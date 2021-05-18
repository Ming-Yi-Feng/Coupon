package com.MingYi.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    private Integer code;
    private String mes;
    private T data;

    public CommonResponse(Integer code,String mes){
        this.mes = mes;
        this.code = code;
    }
}
