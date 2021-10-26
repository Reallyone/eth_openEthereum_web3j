package com.eth.web3j.job;

import com.eth.web3j.common.ETHEnableCondition;
import com.eth.web3j.common.TaskRedisCheckKey;
import com.eth.web3j.service.EthTokenAddressPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 批量生成以太坊地址放入地址池
 */
@Component
@Slf4j
@EnableScheduling
@Conditional(ETHEnableCondition.class)
public class EthCreateAddressJob {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EthTokenAddressPoolService ethTokenAddressPoolService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void createAddress() {
        try {
            boolean bool = redisTemplate.opsForValue().setIfAbsent(TaskRedisCheckKey.ETH_CREATE_ADDRESS_JOB_REDIS_CHECK_KEY, "", Duration.ofSeconds(300));
            if (bool) {
                ethTokenAddressPoolService.batchCreateEthAddress();
                redisTemplate.delete(TaskRedisCheckKey.ETH_CREATE_ADDRESS_JOB_REDIS_CHECK_KEY);
            } else {
                log.error(log.getName() + "创建地址池失败，上一任务正在运行!");
            }
        } catch (Exception e) {
            log.error("创建地址池失败,失败信息【{}】", e);
            redisTemplate.delete(TaskRedisCheckKey.ETH_CREATE_ADDRESS_JOB_REDIS_CHECK_KEY);
            e.printStackTrace();
        }
    }


}
