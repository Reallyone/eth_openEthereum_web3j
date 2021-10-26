package com.eth.web3j.service.impl;

import com.eth.web3j.domain.vo.TokenTransEntranceDto;
import com.eth.web3j.service.EthTransactionClientService;
import com.eth.web3j.util.EthereumUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

//使用 web3j.ethSendRawTransaction() 发送交易 需要用私钥自签名交易 详见ColdWallet.java

@Service
public class EthTransactionClientServiceImpl implements EthTransactionClientService {

    @Value("${eth.gas.limit}")
    private BigInteger GAS_LIMIT;

    private static BigDecimal defaultGasPrice = BigDecimal.valueOf(5);

    /**
     * 获取余额
     * 获取后的余额需要转换
     *
     * @param address 钱包地址
     * @return 余额
     */
    @Override
    public BigDecimal getBalance(Web3j web3j, String address, int round) {
        BigDecimal decimal = null;
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            BigInteger balance = ethGetBalance.getBalance();
            //这里需要转换一下
            decimal = EthereumUtils.bigIntegerToBigDecimal(balance, round);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("address " + address + " balance " + decimal + "wei");
        return decimal;
    }

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
    @Override
    public Transaction makeTransaction(String fromAddress, String toAddress,
                                       BigInteger nonce, BigInteger gasPrice,
                                       BigInteger gasLimit, BigInteger value) {
        Transaction transaction;
        transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);
        return transaction;
    }

    /**
     * 获取普通交易的gas上限
     *
     * @param transaction 交易对象
     * @return gas 上限
     */
    @Override
    public BigInteger getTransactionGasLimit(Web3j web3j, Transaction transaction) {
        BigInteger gasLimit = BigInteger.ZERO;
        try {
            EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
            gasLimit = ethEstimateGas.getAmountUsed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gasLimit;
    }

    /**
     * 获取普通交易的gas上限
     * eth_gasPrice
     * 返回当前的gas价格。这个值由最近几个块的gas价格的中值决定。只读属性。
     *
     * @return gas 上限
     */
    @Override
    public BigInteger getTransactionEthGaPrice(Web3j web3j) {
        BigInteger gasPrice = null;
        try {
            gasPrice = web3j.ethGasPrice().send().getGasPrice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gasPrice != null) {
            gasPrice.multiply(GAS_LIMIT);
        }
        return gasPrice;

    }


    /**
     * 获取账号交易次数 nonce
     *
     * @param address 钱包地址
     * @return nonce
     */
    @Override
    public BigInteger getTransactionNonce(Web3j web3j, String address) {
        BigInteger nonce = BigInteger.ZERO;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            nonce = ethGetTransactionCount.getTransactionCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nonce;
    }

    /**
     * 发送一个普通交易
     *
     * @return 交易 Hash
     */
    @Override
    public String sendTransaction(Web3j web3j, Admin admin, String fromAddress, String toAddress, BigDecimal amount, String password) {
        BigInteger unlockDuration = BigInteger.valueOf(60L);
        String txHash = null;
        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, password, unlockDuration).send();
            if (personalUnlockAccount.accountUnlocked()) {
                BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
                Transaction transaction = makeTransaction(fromAddress, toAddress,
                        null, null, null, value);
                //不是必须的 可以使用默认值
                BigInteger gasLimit = getTransactionGasLimit(web3j, transaction);
                //不是必须的 缺省值就是正确的值
                BigInteger nonce = getTransactionNonce(web3j, fromAddress);
                //该值为大部分矿工可接受的gasPrice
                BigInteger gasPrice = Convert.toWei(defaultGasPrice, Convert.Unit.GWEI).toBigInteger();
                transaction = makeTransaction(fromAddress, toAddress,
                        nonce, gasPrice,
                        gasLimit, value);
                EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
                txHash = ethSendTransaction.getTransactionHash();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("tx hash " + txHash);
        return txHash;
    }

    /**
     * 发送一个普通交易
     * @param web3j
     * @param admin
     * @param entranceDto
     * @return
     */
    @Override
    public String sendTransaction(Web3j web3j, Admin admin, TokenTransEntranceDto entranceDto) {
        BigInteger unlockDuration = BigInteger.valueOf(60L);
        String txHash = null;
        synchronized(this){
            try {
                PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(entranceDto.getFrom(), entranceDto.getPassword(), unlockDuration).send();
                if (personalUnlockAccount.accountUnlocked()) {
                    BigInteger value = Convert.toWei(entranceDto.getAmount(), Convert.Unit.ETHER).toBigInteger();
                    Transaction transaction = makeTransaction(entranceDto.getFrom(), entranceDto.getTo(),
                            null, null, null, value);
                    //不是必须的 可以使用默认值
                    BigInteger gasLimit = getTransactionGasLimit(web3j, transaction);
                    //不是必须的 缺省值就是正确的值
                    BigInteger nonce = getTransactionNonce(web3j, entranceDto.getFrom());
                    //该值为大部分矿工可接受的gasPrice
                    BigInteger gasPrice = Convert.toWei(defaultGasPrice, Convert.Unit.GWEI).toBigInteger();
                    transaction = makeTransaction(entranceDto.getFrom(), entranceDto.getTo(),
                            nonce, gasPrice,
                            gasLimit, value);
                    EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
                    txHash = ethSendTransaction.getTransactionHash();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("tx hash " + txHash);
        }
        return txHash;
    }

    //查询余额
    @Override
    public BigInteger getBalance(Web3j web3j, String address) {
        BigInteger balance = new BigInteger("0");
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            balance = ethGetBalance.getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return balance;
    }

    /**
     * 查看手续费是否足够
     *
     * @return
     */
    @Override
    public boolean enoughCost(Web3j web3j, String address, BigInteger tradeCost) {
        return this.getBalance(web3j, address).compareTo(tradeCost) > 0;
    }


}
