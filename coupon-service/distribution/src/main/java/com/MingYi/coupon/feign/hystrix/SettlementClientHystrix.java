package com.MingYi.coupon.feign.hystrix;

import com.MingYi.coupon.exception.CouponException;
import com.MingYi.coupon.feign.SettlementClient;
import com.MingYi.coupon.vo.CommonResponse;
import com.MingYi.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SettlementClientHystrix implements SettlementClient{
    @Override
    public CommonResponse<SettlementInfo> computeRule(SettlementInfo settlement) throws CouponException {
        log.error("[eureka-client-coupon-settlement] computeRule" +
                "request error");

        settlement.setEmploy(false);
        settlement.setCost(-1.0);

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-settlement] request error",
                settlement
        );
    }
}
