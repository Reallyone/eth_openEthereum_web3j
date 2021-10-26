package com.eth.web3j.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class EthCodeWalletServiceTest {

    @Resource
    private  EthCodeWalletService ethCodeWalletService;


    @Test
    void createWallet() {
        ethCodeWalletService.createWallet("a1111");
    }

    @Test
    void testCreateWallet() {

    }

    @Test
    void decryptWallet() {
    }

    @Test
    void signTransaction() {
    }
}