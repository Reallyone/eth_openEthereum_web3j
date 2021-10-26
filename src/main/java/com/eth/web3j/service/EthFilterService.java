package com.eth.web3j.service;

import org.web3j.protocol.Web3j;

public interface EthFilterService {

    /**
     * 新区块监听
     */
    void newBlockFilter(Web3j web3j);

    /**
     * 新交易监听
     */
    void newTransactionFilter(Web3j web3j);

    /**
     * 遍历旧区块、交易
     */
    void replayFilter(Web3j web3j);

    /**
     * 从某一区块开始直到最新区块、交易
     */
    void catchUpFilter(Web3j web3j);

}
