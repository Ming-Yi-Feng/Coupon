package com.MingYi.coupon.service.impl;

import com.MingYi.coupon.Dao.CouponTemplateDao;
import com.MingYi.coupon.entity.CouponTemplate;
import com.MingYi.coupon.exception.CouponException;
import com.MingYi.coupon.service.ITemplateBaseService;
import com.MingYi.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优惠劵模板的基础服务，查看
 */
@Slf4j
@Service
public class TemplateBaseServiceImpl implements ITemplateBaseService {

    final CouponTemplateDao templateDao;

    public TemplateBaseServiceImpl(CouponTemplateDao templateDao) {
        this.templateDao = templateDao;
    }


    @Override
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {
        //从 Java 8 引入的一个很有趣的特性是 Optional  类。
        // Optional 类主要解决的问题是臭名昭著的空指针异常（NullPointerException）
        Optional<CouponTemplate> template = templateDao.findById(id);
        if (!template.isPresent()) {
            throw new CouponException("Template Is Not Exist: " + id);
        }

        return template.get();
    }

    @Override
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        List<CouponTemplate> templates = templateDao.findAllByAvailableAndExpired(
                        true, false);
        return templates.stream()
                .map(this::template2TemplateSDK).collect(Collectors.toList());
    }

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     */
    @Override
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids) {
        List<CouponTemplate> templates = templateDao.findAllById(ids);
        return templates.stream().map(this::template2TemplateSDK)
                .collect(Collectors.toMap(
                        CouponTemplateSDK::getId, Function.identity()
                ));
    }

    /**
     * 把CouponTemplate转化为CouponTemplateSDK，需要的数据没有entity那么多
     */
    private CouponTemplateSDK template2TemplateSDK(CouponTemplate template) {

        return new CouponTemplateSDK(
                template.getId(),
                template.getName(),
                template.getLogo(),
                template.getDesc(),
                template.getCategory().getCode(),
                template.getProductLine().getCode(),
                template.getKey(),  // 并不是拼装好的 Template Key
                template.getTarget().getCode(),
                template.getRule()
        );
    }
}
