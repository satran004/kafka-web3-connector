package com.bloxbean.kafka.connectors.web3.util;
import java.math.BigInteger;

public class HexConverter {
    private final static BigInteger THOUSAND = new BigInteger("1000");

    public static BigInteger stringToBigInteger(String hex) {
        if (!StringUtil.isEmpty(hex)) {
            if (hex.startsWith("0x")) { //Hex string
                hex = hex.substring(2);

                if (StringUtil.isEmpty(hex))
                    return BigInteger.ZERO;

                BigInteger bi = new BigInteger(hex, 16);
                return bi;
            } else {
                return new BigInteger(hex);
            }

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

    public static long hexToLongValue(String hexOrLong) {
        if (!StringUtil.isEmpty(hexOrLong)) {
            if (hexOrLong.startsWith("0x")) {
                hexOrLong = hexOrLong.substring(2);
            } else {
                try {
                    long value = Long.parseLong(hexOrLong);
                    return value;
                } catch (NumberFormatException ex) {

                }
            }

            BigInteger bi = new BigInteger(hexOrLong, 16);
            if (bi != null)
                return bi.longValue();
            else
                return 0L;
        } else
            return 0L;
    }

    public static String hexToBigIntegerStr(String hex) {
        BigInteger bi = HexConverter.stringToBigInteger(hex);
        if(bi != null) return bi.toString();
        else
            return hex;
    }

    public static void main(String[] args) {
        BigInteger bi = stringToBigInteger("0x54e34e8e");
        System.out.println(bi.toString());
    }
}
