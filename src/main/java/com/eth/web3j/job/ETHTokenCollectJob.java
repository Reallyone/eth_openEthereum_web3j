package com.eth.web3j.job;

import com.eth.web3j.common.ETHEnableCondition;
import com.eth.web3j.common.TaskRedisCheckKey;
import com.eth.web3j.service.EthTokenCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 归集
 */
@Component
@Slf4j
@EnableScheduling
@Conditional(ETHEnableCondition.class)
public class ETHTokenCollectJob {

    @Resource
    private EthTokenCollectService ethTokenCollectService;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void collect() {

        try {
            boolean bool = redisTemplate.opsForValue().setIfAbsent(TaskRedisCheckKey.ETH_TOKEN_COLLECT_JOB_REDIS_CHECK_KEY, "");
            if (bool) {
                log.info(log.getName() + " 归集开始");
                ethTokenCollectService.collect();
                redisTemplate.delete(TaskRedisCheckKey.ETH_TOKEN_COLLECT_JOB_REDIS_CHECK_KEY);
            } else {
                log.error(log.getName() + "归集失败，上一任务正在运行!");
            }
        } catch (Exception e) {
            log.error("归集失败,失败信息【{}】", e);
            e.printStackTrace();
            redisTemplate.delete(TaskRedisCheckKey.ETH_TOKEN_COLLECT_JOB_REDIS_CHECK_KEY);
        }
    }









}
