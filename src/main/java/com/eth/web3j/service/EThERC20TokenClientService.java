package com.eth.web3j.service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;

import java.math.BigInteger;

public interface EThERC20TokenClientService {
    /**
     * 查询代币余额
     */
    BigInteger getTokenBalance(Web3j web3j, String fromAddress, String contractAddress);

    /**
     * 查询代币名称
     *
     * @param web3j
     * @param contractAddress
     * @return
     */
    String getTokenName(Web3j web3j, String contractAddress, String fromAddr);


    /**
     * 查询代币符号
     *
     * @param web3j
     * @param contractAddress
     * @return
     */
    String getTokenSymbol(Web3j web3j, String contractAddress, String fromAddr);


    /**
     * 查询代币精度
     *
     * @param web3j
     * @param contractAddress
     * @return
     */
    int getTokenDecimals(Web3j web3j, String contractAddress, String fromAddr);

    /**
     * 查询代币发行总量
     *
     * @param web3j
     * @param contractAddress
     * @return
     */
    BigInteger getTokenTotalSupply(Web3j web3j, String contractAddress, String fromAddr);

    /**
     * 代币转账
     * contractAddress 合约地址
     */
    String sendTokenTransaction(Web3j web3j, Admin admin, String fromAddress, String password, String toAddress, String contractAddress, BigInteger amount);


}
