package com.eth.web3j.service;

import com.eth.web3j.core.Web3JAdminClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.admin.Admin;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class EthAccountServiceTest {
    @Resource
    private EthAccountService ethAccountService;

    @Test
    void getAccountList() {
        Admin admin = Web3JAdminClient.getAdminClient();
        ethAccountService.getAccountList(admin);
    }

    @Test
    void createNewAccount() {
    }

    @Test
    void unlockAccount() {
    }
}