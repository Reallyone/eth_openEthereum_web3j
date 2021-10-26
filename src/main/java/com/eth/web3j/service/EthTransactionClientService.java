package com.eth.web3j.service;

import com.eth.web3j.domain.vo.TokenTransEntranceDto;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.request.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 普通交易
 */
public interface EthTransactionClientService {

    /**
     * 获取余额
     *
     * @param address 钱包地址
     * @return 余额
     */
    BigDecimal getBalance(Web3j web3j, String address,int round);

    BigInteger getBalance(Web3j web3j, String address);

    /**
     * 生成一个普通交易对象
     *
     * @param fromAddress 放款方
     * @param toAddress   收款方
     * @param nonce       交易序号
     * @param gasPrice    gas 价格
     * @param gasLimit    gas 数量
     * @param value       金额
     * @return 交易对象
     */
    Transaction makeTransaction(String fromAddress, String toAddress,
                                BigInteger nonce, BigInteger gasPrice,
                                BigInteger gasLimit, BigInteger value);

    /**
     * 获取普通交易的gas上限
     *
     * @param transaction 交易对象
     * @return gas 上限
     */
    BigInteger getTransactionGasLimit(Web3j web3j,Transaction transaction);

    /**
     * 获取普通交易的gas上限
     *     eth_gasPrice
     *    返回当前的gas价格。这个值由最近几个块的gas价格的中值决定。只读属性。
     * @return gas 上限
     */
    BigInteger getTransactionEthGaPrice(Web3j web3j);


    BigInteger getTransactionNonce(Web3j web3j,String address);


    String sendTransaction(Web3j web3j, Admin admin, String fromAddress, String toAddress, BigDecimal amount, String password);

    String sendTransaction(Web3j web3j, Admin admin, TokenTransEntranceDto entranceDto);

    boolean enoughCost(Web3j web3j, String address, BigInteger tradeCost);



}
