package org.feather.common.exception;

import groovy.util.logging.Slf4j;
import org.feather.common.constants.Constants;
import org.feather.common.resp.ApiResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-10 07:41
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception e){
        return new ApiResult(Constants.RESP_STATUS_INTERNAL_ERROR,"系统异常，请稍后再试");
    }
    @ExceptionHandler(FeatherBuyException.class)
    public ApiResult handleException(FeatherBuyException e){
        return new ApiResult(e.getStatusCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleIllegalParamException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = "参数不合法";
        if (errors.size() > 0) {
            message = errors.get(0).getDefaultMessage();
        }
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,message);
        return result;
    }

}
