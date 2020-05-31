package com.bloxbean.kafka.connectors.web3.source.events.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class EventSchema {

    public static final String BLOCK_HASH = "blockHash";
    public static final String LOG_INDEX = "logIndex";
    public static final String ADDRESS = "address";
    public static final String REMOVED = "removed";
    public static final String DATA = "data";
    public static final String BLOCK_NUMBER = "blockNumber";
    public static final String TRANSACTION_INDEX = "transactionIndex";
    public static final String TRANSACTION_HASH = "transactionHash";
    public static final String TOPICS = "topics";

    public static Schema SCHEMA = SchemaBuilder.struct().name("com.bloxbean.kafka.connectors.web3.source.schema.Event")
            .field(BLOCK_HASH, Schema.STRING_SCHEMA)
            .field(LOG_INDEX, Schema.INT64_SCHEMA)
            .field(ADDRESS,Schema.STRING_SCHEMA)
            .field(REMOVED, Schema.BOOLEAN_SCHEMA)
            .field(DATA, Schema.STRING_SCHEMA)
            .field(BLOCK_NUMBER, Schema.INT64_SCHEMA)
            .field(TRANSACTION_INDEX, Schema.INT64_SCHEMA)
            .field(TRANSACTION_HASH, Schema.STRING_SCHEMA)
            .field(TOPICS,
                    SchemaBuilder.array(Schema.STRING_SCHEMA).build())
            .build();

    public static Schema KEY_SCHEMA = SchemaBuilder.struct().name("com.bloxbean.kafka.connectors.web3.source.schema.EventKey")
            .field(BLOCK_NUMBER, Schema.OPTIONAL_STRING_SCHEMA)
            .field(LOG_INDEX, Schema.OPTIONAL_STRING_SCHEMA)
            .field(ADDRESS, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TRANSACTION_HASH, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TRANSACTION_INDEX, Schema.OPTIONAL_STRING_SCHEMA)
            .field("topic", Schema.OPTIONAL_STRING_SCHEMA)
            .build();

}
