package com.MingYi.coupon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public abstract class AbstractZuulFilter extends ZuulFilter {

    //用于在过滤器之间传递消息，保存在ThreadLocal中。
    //扩展了concurrentMap
    RequestContext context;

    //是否执行的标志
    private final static String NEXT="next";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean)ctx.getOrDefault(NEXT,true);
    }

    @Override
    public Object run() throws ZuulException {
        context = RequestContext.getCurrentContext();
        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code,String msg){
        context.set(NEXT,false);
        //不需要执行其他过滤器，直接返回
        context.setSendZuulResponse(false);
        //跳转页面
        context.getResponse().setContentType("text/html;charset=UTF-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(String.format("{\"result\": \"%s!\"}", msg));
        return null;
    }

    Object success() {
        context.set(NEXT, true);
        return null;
    }
}
