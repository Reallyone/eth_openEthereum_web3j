package com.eth.web3j.service.impl;

import com.eth.web3j.service.EthFilterService;
import io.reactivex.disposables.Disposable;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;

import java.math.BigInteger;

@Service
public class EthFilterServiceImpl implements EthFilterService {

    @Override
    public void newBlockFilter(Web3j web3j) {
        Disposable subscription =   web3j.
                blockFlowable(false).
                subscribe(block -> {
                    System.out.println("new block come in");
                    System.out.println("block number" + block.getBlock().getNumber());
                });
    }

    @Override
    public void newTransactionFilter(Web3j web3j) {
        Disposable subscription = web3j.
                pendingTransactionFlowable().
                subscribe(transaction -> {
                    System.out.println("transaction come in");
                    System.out.println("transaction txHash " + transaction.getHash());
                });
    }

    @Override
    public void replayFilter(Web3j web3j) {
        BigInteger startBlock = BigInteger.valueOf(2000000);
        BigInteger endBlock = BigInteger.valueOf(2010000);
        /**
         * 遍历旧区块
         */
        Disposable subscription = web3j.
                replayPastBlocksFlowable(
                        DefaultBlockParameter.valueOf(startBlock),
                        DefaultBlockParameter.valueOf(endBlock),
                        false).
                subscribe(ethBlock -> {
                    System.out.println("replay block");
                    System.out.println(ethBlock.getBlock().getNumber());
                });

        /**
         * 遍历旧交易
         */
        Disposable subscription1 = web3j.
                replayPastTransactionsFlowable(
                        DefaultBlockParameter.valueOf(startBlock),
                        DefaultBlockParameter.valueOf(endBlock)).
                subscribe(transaction -> {
                    System.out.println("replay transaction");
                    System.out.println("txHash " + transaction.getHash());
                });
    }

    @Override
    public void catchUpFilter(Web3j web3j) {
        BigInteger startBlock = BigInteger.valueOf(17092529);

        /**
         * 遍历旧区块，监听新区块
         */
        Disposable subscription = web3j.replayPastAndFutureBlocksFlowable(
                DefaultBlockParameter.valueOf(startBlock), false)
                .subscribe(block -> {
                    System.out.println("block");
                    System.out.println(block.getBlock().getNumber());

                });
        /**
         * 遍历旧交易，监听新交易
         */
    /*    Disposable subscription2 = web3j.replayPastAndFutureTransactionsFlowable(
                DefaultBlockParameter.valueOf(startBlock))
                .subscribe(tx -> {
                    System.out.println("transaction");
                    System.out.println(tx.getHash());
                });*/
    }
}
