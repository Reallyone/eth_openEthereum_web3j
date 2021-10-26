package com.eth.web3j.job;

import com.eth.web3j.common.ETHEnableCondition;
import com.eth.web3j.common.TaskRedisCheckKey;
import com.eth.web3j.service.ETHTokenConfirmJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 确认订单
 * <p>
 * 通过区块确认
 */
@Component
@Slf4j
@EnableScheduling
@Conditional(ETHEnableCondition.class)
public class ETHTokenConfirmJob {

    @Autowired
    private ETHTokenConfirmJobService confirmJobService;


    @Scheduled(cron = "0/10 * * * * ?")
    public void queryBlockJob() {
        confirmJobService.queryBlockJob();
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void confirmJob() {
        confirmJobService.confirmJob();
    }


}
