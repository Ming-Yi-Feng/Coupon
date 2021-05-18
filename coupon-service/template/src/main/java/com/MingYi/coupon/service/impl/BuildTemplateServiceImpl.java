package com.MingYi.coupon.service.impl;

import com.MingYi.coupon.Dao.CouponTemplateDao;
import com.MingYi.coupon.entity.CouponTemplate;
import com.MingYi.coupon.exception.CouponException;
import com.MingYi.coupon.service.IAsyncService;
import com.MingYi.coupon.service.IBuildTemplateService;
import com.MingYi.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 构建优惠劵模板
 */
@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {

    private final IAsyncService asyncService;
    private final CouponTemplateDao couponTemplateDao;

    public BuildTemplateServiceImpl(IAsyncService asyncService, CouponTemplateDao couponTemplateDao) {
        this.asyncService = asyncService;
        this.couponTemplateDao = couponTemplateDao;
    }

    //request的校验和转化
    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {
        //TODO:参数合法性校验
        if(!request.validate()) throw new CouponException("BuildTemplate Param Is Not Valid!");
        // 判断同名的优惠券模板是否存在
        if (null != couponTemplateDao.findByName(request.getName())) {
            throw new CouponException("Exist Same Name Template!");
        }
        // 构造 CouponTemplate 并保存到数据库中
        CouponTemplate template = requestToCouponTemplate(request);

        //返回的实体带了ID
        template = couponTemplateDao.save(template);

        // 根据优惠券模板异步生成优惠券码
        //因为比较慢，所以写成异步的
        asyncService.asyncConstructCouponByTemplate(template);

        return template;
    }

    /**
     * 将前端传来的TemplateRequest转为CouponTemplate
     * @param request
     * @return
     */
    private CouponTemplate requestToCouponTemplate(TemplateRequest request){
        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getRule()
        );
    }

}
