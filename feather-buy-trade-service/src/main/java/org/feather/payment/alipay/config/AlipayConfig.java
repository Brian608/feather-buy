package org.feather.payment.alipay.config;


public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091500517431";

    // 商户私钥，您的PKCS8格式RSA私钥
    public static String merchant_private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMfqSNo5GVSKPCExHtXaq4FU1riU7AS/pfIh8YWSgrJUwgSdkiUWYAo/78twVv6hEsSXJtwpqjlfUjvKCZtqHuAf9pMxsELEMIRS2vXu+rcJulAspsrF33W20XowMR3KvnlMP7kCirddnDWcqYnqtNmJ3pimmDObyFUxacuNmrEHAgMBAAECgYEAvfoXJ0GRn7QJtiUev20hBDNiJWBn9Y9GHJZDFYMILn27LnWq9xGPTM47i2GDWlApLKbTJ+90MzcxR14UyRsMoJLEyUF3WtckDOd2WQVoi72zpsP9dS6Zm7SoxccSUTdvGdaqqeQifOqXMc4CEWcMS6KfseoUpBG+oi/AFzpBcsECQQDzSiz58khsJT6yMGCI4herY3VoyhMwo0aL5WaTCZzjZIgkRBDs9r2eg8HXkxqK0nHsalO2xQXI2ZiE5sHffavnAkEA0lwBXmyAcag/v1ecRth29f/9In52O8IBbK44MKgGxLb0PoCpuJq5kHLeurFPYsmk6WQOHIZS3uEGSKQj2bkt4QJAAOIuE6JLnQjtl1wRna2khFzSGlVRiIWvRebJXpNUXIcK74bHwPSMb7zuwUepewOUolUohKtmf/o6UZE89wDP1QJAEbiggOThGTIUVLk7uBgqRWUQXSSML1KTriUrJKYzE8VZ5B4QrjWSpmGW8+FWD7tvAh5ktfB28MHCNsJlJsVb4QJAaNrVpzRVU8CC3GdFWnJw+T3zqwz0wDWtVrWeWoDtlFxKeRnggFKE2Ovs8CSMNYVD57Iyo73ngFj3GJVkm8ytTg==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://116.196.70.116:8666/alipay/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://coder520.com";

    // 签名方式
    public static String sign_type = "RSA";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}
