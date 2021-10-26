package com.eth.web3j.service;


import org.web3j.protocol.Web3j;

/**
 * Event log相关
 * 监听合约event
 */
public interface ContractEventService {

    /**
     * 监听ERC20 token 交易
     * @param address
     */
    void erc20TokenEvent(Web3j web3j, String address);

}
