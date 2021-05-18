package com.MingYi.coupon.feign;

import com.MingYi.coupon.vo.CommonResponse;
import com.MingYi.coupon.vo.CouponTemplateSDK;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 优惠卷模板微服务 Feign接口定义
 */
//指定微服务模块名称。
@FeignClient(value = "eureka-client-coupon-template")
public interface TemplateClient {

    /**
     * 查询可用的优惠劵模板
     */
    @RequestMapping(value = "/coupon-template/template/sdk/all",method = RequestMethod.GET)
    CommonResponse<List<CouponTemplateSDK>>  findAllUsableTemplate();

    /**
     * 获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     * */
    @RequestMapping(value = "/coupon-template/template/sdk/infos",
            method = RequestMethod.GET)
    CommonResponse<Map<Integer, CouponTemplateSDK>> findIds2TemplateSDK(
            @RequestParam("ids") Collection<Integer> ids
    );
}
