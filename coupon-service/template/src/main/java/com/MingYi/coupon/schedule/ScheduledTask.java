package com.MingYi.coupon.schedule;


import com.MingYi.coupon.Dao.CouponTemplateDao;
import com.MingYi.coupon.entity.CouponTemplate;
import com.MingYi.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Slf4j
@Component
public class ScheduledTask {
    final CouponTemplateDao templateDao;

    public ScheduledTask(CouponTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    /**
     * 每小时清算一次
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate(){
        log.info("Start To Expire CouponTemplate");
        List<CouponTemplate> templates = templateDao.findAllByExpired(false);

        //没有有效，在使用的模板
        if (CollectionUtils.isEmpty(templates)) {
            log.info("Done To Expire CouponTemplate.");
            return;
        }

        //有使用的，定时看看过期木有
        Date cur = new Date();
        List<CouponTemplate> expiredTemplates =
                new ArrayList<>(templates.size());
        for(CouponTemplate t : templates){
            // 根据优惠券模板规则中的 "过期规则" 校验模板是否过期
            TemplateRule rule = t.getRule();
            if (rule.getExpiration().getDeadline() < cur.getTime()) {
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        }
        if (CollectionUtils.isNotEmpty(expiredTemplates)) {
            log.info("Expired CouponTemplate Num: {}",
                    templateDao.saveAll(expiredTemplates));
        }

        log.info("Done To Expire CouponTemplate.");
    }
}
