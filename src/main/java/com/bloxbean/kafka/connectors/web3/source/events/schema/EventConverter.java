package com.bloxbean.kafka.connectors.web3.source.events.schema;

import com.bloxbean.kafka.connectors.web3.util.HexConverter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.kafka.connect.data.Struct;

import java.util.ArrayList;
import java.util.List;

import static com.bloxbean.kafka.connectors.web3.source.events.schema.EventSchema.*;

public class EventConverter {

    public Struct convertFromJSON(JSONObject eventJson) {
        Struct eventStruct = new Struct(EventSchema.SCHEMA);
        eventStruct.put(BLOCK_HASH, eventJson.getString(BLOCK_HASH));
        eventStruct.put(LOG_INDEX, HexConverter.hexToLongValue(eventJson.getString(LOG_INDEX)));
        eventStruct.put(ADDRESS, eventJson.getString(ADDRESS));
        eventStruct.put(REMOVED, eventJson.getBoolean(REMOVED));
        eventStruct.put(DATA, eventJson.getString(DATA));
        eventStruct.put(BLOCK_NUMBER, HexConverter.hexToLongValue(eventJson.getString(BLOCK_NUMBER)));
        eventStruct.put(TRANSACTION_INDEX, HexConverter.hexToLongValue(eventJson.getString(TRANSACTION_INDEX)));
        eventStruct.put(TRANSACTION_HASH, eventJson.getString(TRANSACTION_HASH));

        JSONArray topicsArray = eventJson.getJSONArray("topics");
        if(topicsArray != null) {
            List<String> topicsStruct = new ArrayList<>();
            for(int i=0; i < topicsArray.length(); i++) {
                String topic = topicsArray.getString(i);
                topicsStruct.add(topic);
            }

            eventStruct.put(TOPICS, topicsStruct);
        }

        return eventStruct;
    }
}
