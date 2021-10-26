package com.eth.web3j.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EthereumUtils {
    public static final int SCALE = 9;//保留小数点

    public static final int PASS_RADIX = 16;//pay 数字都是16进制

    /**
     * 16进制转BigDecimal
     *
     * @param hex
     * @return
     */
    public static BigDecimal hexToBigDecimal(String hex, int round) {
        BigDecimal PASS_VALUE_BIGDECIMAL = revertBigDecimalByRound(round);
        return hexToBigDecimal(hex, PASS_VALUE_BIGDECIMAL);
    }



    /**
     * 16进制转BigDecimal
     *
     * @param hex
     * @param passValueBigdecimal
     * @return
     */
    public static BigDecimal hexToBigDecimal(String hex, BigDecimal passValueBigdecimal) {
        if (hex == null || hex.length() <= 2) {
            return BigDecimal.ZERO;
        }
        hex = hex.toLowerCase();
        if (!hex.startsWith("0x")) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(new BigInteger(hex.substring(2), PASS_RADIX).toString())
                .divide(passValueBigdecimal);//.setScale(SCALE, BigDecimal.ROUND_DOWN)
    }



    /**
     * BigDecimal 转 BigInteger(平常单位换算成wei)
     *
     * @param value
     * @return
     */
    public static BigInteger generally2wei(BigDecimal value, int round) {
        BigDecimal PASS_VALUE_BIGDECIMAL = revertBigDecimalByRound(round);
        return new BigDecimal(value.toString()).multiply(PASS_VALUE_BIGDECIMAL).toBigInteger();
    }


    /**
     * BigInteger 转 BigDecimal(wei 换算成平常单位)
     *
     * @param value
     * @return
     */
    public static BigDecimal bigIntegerToBigDecimal(BigInteger value, int round) {
        BigDecimal PASS_VALUE_BIGDECIMAL = revertBigDecimalByRound(round);
        return new BigDecimal(value.toString())
                .divide(PASS_VALUE_BIGDECIMAL).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal revertBigDecimalByRound(int round){
        double pow = Math.pow(Double.parseDouble("10"),Double.parseDouble((round)+""));
        return new BigDecimal(pow);
    }


}
