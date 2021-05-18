package com.MingYi.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 结算信息对象定义
 * 1. userId
 * 2. 商品信息
 * 3. 优惠劵列表
 * 4. 结算结果金额
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementInfo {
    private Long userId;

    private List<GoodsInfo> goodsInfos;

    private List<CouponAndTemplateInfo> couponAndTemplateInfos;

    /** 是否使结算生效*/
    private Boolean employ;
    private Double cost;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class  CouponAndTemplateInfo{
        //Coupon的主键
        private Integer id;

        private CouponTemplateSDK template;
    }


}
