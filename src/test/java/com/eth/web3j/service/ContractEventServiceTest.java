package com.eth.web3j.service;

import com.eth.web3j.core.Web3JClient;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.Web3j;

import javax.annotation.Resource;

class ContractEventServiceTest {

    @Resource
    private ContractEventService contractEventService;

    @Test
    void erc20TokenEvent() {
        Web3j web3j = Web3JClient.getClient();
        contractEventService.erc20TokenEvent(web3j,"xxxxx");
    }
}