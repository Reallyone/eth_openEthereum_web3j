package com.eth.web3j.service;

import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.List;

public interface EthBlockService {

    /**
     * 查询hash查询属于哪个区块
     */
    BigInteger queryBlockByTransactionId(String transactionId);

    /**
     * 查询最新区块高度
     *
     * @return
     */
    BigInteger queryBlockLast();



    /**
     * 获取区块的交易信息
     *
     * @param blockNo
     * @return
     */
    List<EthBlock.TransactionResult> getTransactionsByBlockNo(BigInteger blockNo);

}
