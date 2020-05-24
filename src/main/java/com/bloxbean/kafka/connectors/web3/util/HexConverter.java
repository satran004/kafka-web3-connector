package com.bloxbean.kafka.connectors.web3.util;
import java.math.BigInteger;

public class HexConverter {
    private final static BigInteger THOUSAND = new BigInteger("1000");

    public static BigInteger hexToBigInteger(String hex) {
        if (!StringUtil.isEmpty(hex)) {
            if (hex.startsWith("0x")) {
                hex = hex.substring(2);
            }

            BigInteger bi = new BigInteger(hex, 16);
            return bi;
        } else
            return null;
    }

    public static long hexToTimestampInMillis(String hex) {
        if (!StringUtil.isEmpty(hex)) {
            if (hex.startsWith("0x")) {
                hex = hex.substring(2);
            }

            BigInteger bi = new BigInteger(hex, 16);
            return bi.multiply(THOUSAND).longValue();
        } else
            return 0;
    }
}
