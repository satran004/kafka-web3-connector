package com.bloxbean.kafka.connectors.web3.exception;

public class Web3Exception extends RuntimeException {

    public Web3Exception(String message) {
        super(message);
    }

    public Web3Exception(String message, Throwable t) {
        super(message, t);
    }
}
