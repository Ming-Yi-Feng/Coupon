package com.MingYi.coupon.advice;

import com.MingYi.coupon.exception.CouponException;
import com.MingYi.coupon.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>全局异常处理</h1>
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    //可以捕获异常类型，进行处理
    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handlerCouponException(HttpServletRequest req,
                                                         CouponException ex){
        CommonResponse<String> response = new CommonResponse<>(-1,"business error");

        //获取自定义类型的异常信息
        response.setData(ex.getMessage());
        return response;
    }
}
