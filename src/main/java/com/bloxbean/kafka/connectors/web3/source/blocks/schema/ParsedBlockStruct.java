package com.bloxbean.kafka.connectors.web3.source.blocks.schema;

import org.apache.kafka.connect.data.Struct;

import java.util.List;

public class ParsedBlockStruct {
    private Struct block;
    private List<Struct> transactions;

    public Struct getBlock() {
        return block;
    }

    public void setBlock(Struct block) {
        this.block = block;
    }

    public List<Struct> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Struct> transactions) {
        this.transactions = transactions;
    }
}
