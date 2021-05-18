package com.MingYi.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {
    private Integer type;

    private Double price;

    private Integer count;

}
