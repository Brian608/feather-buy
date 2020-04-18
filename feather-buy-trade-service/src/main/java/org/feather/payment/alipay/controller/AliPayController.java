package org.feather.payment.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.feather.payment.alipay.config.AlipayConfig;
import org.feather.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-16 23:25
 **/
@Controller
@RequestMapping("alipay")
public class AliPayController {
    @Autowired
    @Qualifier("/tradeServiceImpl")
    private TradeService tradeService;

    /**
     *@Description 提交支付订单
     */
    @RequestMapping("/pay")
    public void pay(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //付款金额，必填
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        String subject =new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
//        String subject =request.getParameter("subject");
        //商品描述，可空
        String body =new String(request.getParameter("body").getBytes("ISO-8859-1"),"UTF-8");
//        String body =request.getParameter("body");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //输出
//        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
        response.getWriter().flush();
    }



    /**
     *@Description 支付宝异步通知
     */
    @RequestMapping("/notifyUrl")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if(signVerified){
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            System.out.println("订单号："+out_trade_no+"--支付宝交易号码："+trade_no+"--交易状态："+trade_status);

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
                tradeService.payOrder(out_trade_no);
            }
            response.getOutputStream().println("success");
        }else {//验证失败
            response.getOutputStream().println("fail");
        }

    }

}
