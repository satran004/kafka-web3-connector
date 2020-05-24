package com.bloxbean.kafka.connectors.web3.util;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) ? true : false;
    }
}
