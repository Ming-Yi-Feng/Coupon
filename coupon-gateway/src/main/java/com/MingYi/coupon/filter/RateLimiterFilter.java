package com.MingYi.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>限流过滤器</h1>
 */
@Slf4j
@Component
public class RateLimiterFilter extends AbstractPreZuulFilter {

    //TODO:在一秒中只能取两个
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    protected Object cRun() {

        HttpServletRequest request = context.getRequest();

        if (rateLimiter.tryAcquire()) {
            log.info("get rate token success");
            return success();
        } else {
            log.error("rate limit: {}", request.getRequestURI());
            return fail(402, "error: rate limit");
        }
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        return 2;
    }
}