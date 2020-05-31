package com.bloxbean.kafka.connectors.web3.source.events.schema;

import com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockConverter;
import com.bloxbean.kafka.connectors.web3.util.FileUtil;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockSchema.*;
import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockSchema.PARENT_HASH;
import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.TransactionSchema.NRG_PRICE;
import static com.bloxbean.kafka.connectors.web3.source.events.schema.EventSchema.*;
import static org.junit.jupiter.api.Assertions.*;

class EventConverterTest {

    @Test
    void convertFromJSONEvent() throws IOException {
        //given
        String jsonStr = FileUtil.readFileFromResource("/aion-event-log-6117319.json");
        JSONArray events = new JSONArray(jsonStr);

        //When
        EventConverter eventConverter = new EventConverter();
        JSONObject eventJson = (JSONObject)events.get(0);
        Struct struct = eventConverter.convertFromJSON(eventJson);

        //then
        assertEquals(eventJson.getString(BLOCK_HASH), struct.getString(BLOCK_HASH));
        assertEquals(0L, struct.getInt64(LOG_INDEX).longValue());
        assertEquals(eventJson.getString(ADDRESS), struct.get(ADDRESS));
        assertEquals(eventJson.getBoolean(REMOVED), struct.getBoolean(REMOVED));
        assertEquals(6117319L, struct.getInt64(BLOCK_NUMBER).longValue());
        assertEquals(1, struct.getInt64(TRANSACTION_INDEX).longValue());
        assertEquals(eventJson.getString(TRANSACTION_HASH), struct.getString(TRANSACTION_HASH));

        List<String> topics = struct.getArray(TOPICS);
        assertEquals(2, topics.size());
        assertEquals("0xe4142e56e449251d27732d585248d507994e400fc19184ce6158f1263cdc9e1b",
                topics.get(0));
        assertEquals("0x53ef615cb081972c2a6bbadf3484e6c6dbee7219807faebb345373cf92c47130",
                topics.get(1));
    }
}