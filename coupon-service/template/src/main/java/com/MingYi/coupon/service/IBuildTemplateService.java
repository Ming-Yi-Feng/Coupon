package com.MingYi.coupon.service;

import com.MingYi.coupon.entity.CouponTemplate;
import com.MingYi.coupon.exception.CouponException;
import com.MingYi.coupon.vo.TemplateRequest;

/**
 * <h1>构建优惠券模板接口定义</h1>
 */
public interface IBuildTemplateService {

    /**
     * <h2>创建优惠券模板</h2>
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     * */
    CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException;
}
