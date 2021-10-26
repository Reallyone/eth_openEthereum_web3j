package com.eth.web3j.service;

import com.eth.web3j.core.Web3JClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.Web3j;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class EthFilterServiceTest {
    @Resource
    private EthFilterService filterService;

    @Test
    void newBlockFilter() {
    }

    @Test
    void newTransactionFilter() {
    }

    @Test
    void replayFilter() {
        Web3j web3j = Web3JClient.getClient();
        filterService.replayFilter(web3j);
    }

    @Test
    void catchUpFilter() {
        Web3j web3j = Web3JClient.getClient();
        filterService.catchUpFilter(web3j);
    }
}