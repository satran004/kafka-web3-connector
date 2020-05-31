package com.bloxbean.kafka.connectors.web3.source.blocks.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class BlockSchema {

    public static final String NUMBER = "number";
    public static final String HASH = "hash";
    public static final String PARENT_HASH = "parentHash";
    public static final String LOGS_BLOOM = "logsBloom";
    public static final String TRANSACTIONS_ROOT = "transactionsRoot";
    public static final String STATE_ROOT = "stateRoot";
    public static final String RECEIPTS_ROOT = "receiptsRoot";
    public static final String MINER = "miner";
    public static final String DIFFICULTY = "difficulty";
    public static final String TOTAL_DIFFICULTY = "totalDifficulty";
    public static final String EXTRA_DATA = "extraData";
    public static final String SIZE = "size";
    public static final String GAS_USED = "gasUsed";
    public static final String GAS_LIMIT = "gasLimit";
    public static final String NRG_LIMIT = "nrgLimit";
    public static final String NRG_USED = "nrgUsed";
    public static final String TIMESTAMP = "timestamp";
    public static final String SEED = "seed";
    public static final String SEAL_TYPE = "sealType";
    public static final String SIGNATURE = "signature";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String MAIN_CHAIN = "mainChain";
    public static final String TRANSACTIONS = "transactions";
    public  static final String TRANSACTION_HASHES = "transaction_hashes" ;

    public static Schema SCHEMA = SchemaBuilder.struct().name("com.bloxbean.kafka.connectors.web3.source.schema.Block")
            .field(NUMBER, Schema.OPTIONAL_INT64_SCHEMA)
            .field(HASH, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PARENT_HASH, Schema.OPTIONAL_STRING_SCHEMA)
            .field(LOGS_BLOOM, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TRANSACTIONS_ROOT, Schema.OPTIONAL_STRING_SCHEMA)
            .field(STATE_ROOT, Schema.OPTIONAL_STRING_SCHEMA)
            .field(RECEIPTS_ROOT, Schema.OPTIONAL_STRING_SCHEMA)
            .field(MINER, Schema.OPTIONAL_STRING_SCHEMA)

            .field(DIFFICULTY, Schema.OPTIONAL_STRING_SCHEMA) //Quantity
            .field(TOTAL_DIFFICULTY, Schema.OPTIONAL_STRING_SCHEMA) //Quantity

            .field(EXTRA_DATA, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SIZE, Schema.OPTIONAL_STRING_SCHEMA) //Quantity

            .field(GAS_LIMIT, Schema.OPTIONAL_STRING_SCHEMA) //Quantity
            .field(GAS_USED, Schema.OPTIONAL_STRING_SCHEMA)  //Quantity
            .field(NRG_LIMIT, Schema.OPTIONAL_STRING_SCHEMA) //Quantity
            .field(NRG_USED, Schema.OPTIONAL_STRING_SCHEMA)  //Quantity
            .field(TIMESTAMP, Schema.OPTIONAL_INT64_SCHEMA)

            .field(SEED, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SEAL_TYPE, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SIGNATURE, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PUBLIC_KEY, Schema.OPTIONAL_STRING_SCHEMA)
            .field(MAIN_CHAIN, Schema.OPTIONAL_STRING_SCHEMA)

            .field(TRANSACTIONS,
                    SchemaBuilder.array(TransactionSchema.SCHEMA).name("transactions").build())
            .field(TRANSACTION_HASHES, SchemaBuilder.array(Schema.STRING_SCHEMA).build())
            .build();
}
