package com.eth.web3j.service.impl;

import com.eth.web3j.service.EthAccountService;
import org.springframework.stereotype.Service;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class EthAccountAccountServiceImpl implements EthAccountService {

    /**
     * 获取账号列表
     */
    @Override
    public List<String> getAccountList(Admin admin) {
        List<String> addressList = new ArrayList<>();
        try {
            PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
            if (personalListAccounts==null) return null;

            addressList = personalListAccounts.getAccountIds();
            System.out.println("account size " + addressList.size());
            for (String address : addressList) {
                System.out.println(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressList;
    }

    /**
     * 创建账号
     */
    @Override
    public String createNewAccount(Admin admin, String password) {
        String address = null;
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            address = newAccountIdentifier.getAccountId();
            System.out.println("new account address " + address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }


    /**
     * 账号解锁
     */
    public void unlockAccount(Admin admin, String address, String password) {
        //账号解锁持续时间 单位秒 缺省值300秒
        BigInteger unlockDuration = BigInteger.valueOf(60L);
        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, password, unlockDuration).send();
            Boolean isUnlocked = personalUnlockAccount.accountUnlocked();
            System.out.println("account unlock " + isUnlocked);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
