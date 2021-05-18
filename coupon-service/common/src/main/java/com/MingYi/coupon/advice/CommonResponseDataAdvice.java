package com.MingYi.coupon.advice;

import com.MingYi.coupon.annotation.IgnoreResponseAdvice;
import com.MingYi.coupon.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否对响应进行处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class aClass) {
        //TODO:如果类有注解，则不处理
        if(methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class))
            return false;
        //TODO:如果方法有注解，则不处理
        if(methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class))
            return false;
        return true;
    }

    /**
     * 如何处理？？
     */
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        CommonResponse<Object> response = new CommonResponse<Object>(0,"");
        //为空，则直接返回
        if(null == o) return response;
        //格式已经，则转化类型
        else if(o instanceof CommonResponse){
            response = (CommonResponse<Object>) o;
        }
        else {
            response.setData(o);
        }
        return response;
    }
}
