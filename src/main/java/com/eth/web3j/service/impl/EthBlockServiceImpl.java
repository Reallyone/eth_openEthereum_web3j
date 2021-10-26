package com.eth.web3j.service.impl;

import com.eth.web3j.core.Web3JClient;
import com.eth.web3j.service.EthBlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class EthBlockServiceImpl implements EthBlockService {

    private Web3j web3j = Web3JClient.getClient();

    /**
     * 查询区块高度
     *
     * @param transactionId
     */
    @Override
    public BigInteger queryBlockByTransactionId(String transactionId) {
        try {
            EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(transactionId).send();
            TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getResult();
            if (transactionReceipt == null) {
                return BigInteger.ZERO;
            }
            return new BigInteger(transactionReceipt.getBlockNumber().toString());
        } catch (Exception e) {
            log.error(log.getName()+".queryBlockByTransactionId.error.transactionId=", transactionId, e);
            return BigInteger.ZERO;
        }
    }

    /**
     * 获取高度
     *
     * @return
     */
    @Override
    public BigInteger queryBlockLast() {
        try {
            EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
            return ethBlockNumber.getBlockNumber();
        } catch (IOException e) {
            log.error(log.getName() + ".queryBlockLast.error", e);
            return BigInteger.ZERO;
        }
    }

    /**
     * 获取区块的信息
     *
     * @param blockNo
     * @return
     */
    @Override
    public List<EthBlock.TransactionResult> getTransactionsByBlockNo(BigInteger blockNo) {
        Request request = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNo), true);
        Response response = null;
        try {
            response = request.send();
        } catch (IOException e) {
            log.error(log.getName()+".getTransactionsByBlockNo.error.blockNo=", blockNo, e);
            throw new RuntimeException(e.getMessage());
        }
        EthBlock.Block block = (EthBlock.Block) response.getResult();
        return block.getTransactions();
    }
}
