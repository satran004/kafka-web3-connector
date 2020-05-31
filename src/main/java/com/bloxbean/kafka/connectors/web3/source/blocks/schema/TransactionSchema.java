package com.bloxbean.kafka.connectors.web3.source.blocks.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class TransactionSchema {

    public static final String BLOCK_HASH = "blockHash";
    public static final String BLOCK_NUMBER = "blockNumber";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String NRG = "nrg";
    public static final String NRG_PRICE = "nrgPrice";
    public static final String GAS = "gas";
    public static final String GAS_PRICE = "gasPrice";
    public static final String HASH = "hash";
    public static final String INPUT = "input";
    public static final String NONCE = "nonce";
    public static final String TRANSACTION_INDEX = "transactionIndex";
    public static final String VALUE = "value";
    public static final String TIMESTAMP = "timestamp";
    public static final String V = "v";
    public static final String R = "r";
    public static final String S = "s";

    public final static Schema SCHEMA = SchemaBuilder.struct().name("com.bloxbean.kafka.connectors.web3.source.schema.Transaction")
            .field(BLOCK_HASH, Schema.OPTIONAL_STRING_SCHEMA)
            .field(BLOCK_NUMBER, Schema.OPTIONAL_INT64_SCHEMA)
            .field(FROM, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TO, Schema.OPTIONAL_STRING_SCHEMA)
            .field(NRG, Schema.OPTIONAL_STRING_SCHEMA) //Quntity
            .field(NRG_PRICE, Schema.OPTIONAL_STRING_SCHEMA) //Quntity
            .field(GAS, Schema.OPTIONAL_STRING_SCHEMA) //Quntity
            .field(GAS_PRICE, Schema.OPTIONAL_STRING_SCHEMA) //Quntity
            .field(HASH, Schema.OPTIONAL_STRING_SCHEMA)
            .field(INPUT, Schema.OPTIONAL_STRING_SCHEMA)
            .field(NONCE, Schema.OPTIONAL_INT64_SCHEMA)
            .field(TRANSACTION_INDEX, Schema.OPTIONAL_INT64_SCHEMA)
            .field(VALUE, Schema.OPTIONAL_STRING_SCHEMA) //Quntity
            .field(TIMESTAMP, Schema.OPTIONAL_INT64_SCHEMA)
            .field(V, Schema.OPTIONAL_STRING_SCHEMA)
            .field(R, Schema.OPTIONAL_STRING_SCHEMA)
            .field(S, Schema.OPTIONAL_STRING_SCHEMA)
            .build();


}
