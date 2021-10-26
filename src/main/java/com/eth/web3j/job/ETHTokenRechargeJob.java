package com.eth.web3j.job;

import com.eth.web3j.common.ETHEnableCondition;
import com.eth.web3j.common.TaskRedisCheckKey;
import com.eth.web3j.service.EthTokenRechargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
@EnableScheduling
@Conditional(ETHEnableCondition.class)
public class ETHTokenRechargeJob {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EthTokenRechargeService ethTokenRechargeService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void rechargeJob() {
        try {
            boolean bool = redisTemplate.opsForValue().setIfAbsent(TaskRedisCheckKey.ETH_TOKEN_RECHARGE_JOB_REDIS_CHECK_KEY, "", Duration.ofSeconds(60 * 5));
            if (bool) {
                ethTokenRechargeService.rechargeJob();
                redisTemplate.delete(TaskRedisCheckKey.ETH_TOKEN_RECHARGE_JOB_REDIS_CHECK_KEY);
            } else {
                log.error(log.getName() + "扫快任务任务失败，上一任务正在运行!");
            }
        } catch (Exception e) {
            log.error("扫快任务任务失败,失败信息【{}】", e);
            redisTemplate.delete(TaskRedisCheckKey.ETH_TOKEN_RECHARGE_JOB_REDIS_CHECK_KEY);
            e.printStackTrace();
        }
    }

}
