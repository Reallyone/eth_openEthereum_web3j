package com.eth.web3j.service;

import org.web3j.protocol.admin.Admin;

import java.util.List;

/**
 * 账户操作
 */
public interface EthAccountService {

    List<String> getAccountList(Admin admin);


    String createNewAccount(Admin admin, String password);

    /**
     * 账号解锁
     */
    void unlockAccount(Admin admin, String address, String password);


}
