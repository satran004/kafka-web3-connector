package com.bloxbean.kafka.connectors.web3.source.blocks.schema;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.kafka.connect.data.Struct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockSchema.*;
import static com.bloxbean.kafka.connectors.web3.util.HexConverter.hexToBigIntegerStr;
import static com.bloxbean.kafka.connectors.web3.util.HexConverter.hexToLongValue;


public class BlockConverter {

    public ParsedBlockStruct convertFromJSON(JSONObject blockJson, boolean publishTransactionsSeparately, Set<String> ignoreBlockFields, Set<String> ignoreTransactionFields) {
        ParsedBlockStruct result = new ParsedBlockStruct();

        Struct blockStruct = new Struct(BlockSchema.SCHEMA);

        if (!ignoreBlockFields.contains(NUMBER))
            blockStruct.put(NUMBER, blockJson.optLong(NUMBER));
        if (!ignoreBlockFields.contains(HASH))
            blockStruct.put(HASH, blockJson.optString(HASH));
        if (!ignoreBlockFields.contains(PARENT_HASH))
            blockStruct.put(PARENT_HASH, blockJson.optString(PARENT_HASH));
        if (!ignoreBlockFields.contains(LOGS_BLOOM))
            blockStruct.put(LOGS_BLOOM, blockJson.optString(LOGS_BLOOM));
        if (!ignoreBlockFields.contains(TRANSACTIONS_ROOT))
            blockStruct.put(TRANSACTIONS_ROOT, blockJson.optString(TRANSACTIONS_ROOT));
        if (!ignoreBlockFields.contains(STATE_ROOT))
            blockStruct.put(STATE_ROOT, blockJson.optString(STATE_ROOT));
        if (!ignoreBlockFields.contains(RECEIPTS_ROOT))
            blockStruct.put(RECEIPTS_ROOT, blockJson.optString(RECEIPTS_ROOT));
        if (!ignoreBlockFields.contains(MINER))
            blockStruct.put(MINER, blockJson.optString(MINER));

        if (!ignoreBlockFields.contains(DIFFICULTY))
            blockStruct.put(DIFFICULTY, hexToBigIntegerStr(blockJson.optString(DIFFICULTY)));
        if (!ignoreBlockFields.contains(TOTAL_DIFFICULTY))
            blockStruct.put(TOTAL_DIFFICULTY, hexToBigIntegerStr(blockJson.optString(TOTAL_DIFFICULTY)));

        if (!ignoreBlockFields.contains(EXTRA_DATA))
            blockStruct.put(EXTRA_DATA, blockJson.optString(EXTRA_DATA));

        if (!ignoreBlockFields.contains(SIZE))
            blockStruct.put(SIZE, hexToBigIntegerStr(blockJson.optString(SIZE)));
        if (!ignoreBlockFields.contains(GAS_LIMIT))
            blockStruct.put(GAS_LIMIT, hexToBigIntegerStr(blockJson.optString(GAS_LIMIT)));
        if (!ignoreBlockFields.contains(GAS_USED))
            blockStruct.put(GAS_USED, hexToBigIntegerStr(blockJson.optString(GAS_USED)));
        if (!ignoreBlockFields.contains(NRG_LIMIT))
            blockStruct.put(NRG_LIMIT, hexToBigIntegerStr(blockJson.optString(NRG_LIMIT)));
        if (!ignoreBlockFields.contains(NRG_USED))
            blockStruct.put(NRG_USED, hexToBigIntegerStr(blockJson.optString(NRG_USED)));

        if (!ignoreBlockFields.contains(TIMESTAMP))
            blockStruct.put(TIMESTAMP, hexToLongValue(blockJson.optString(TIMESTAMP)));

        if (!ignoreBlockFields.contains(SEED))
            blockStruct.put(SEED, blockJson.optString(SEED));
        if (!ignoreBlockFields.contains(SEAL_TYPE))
            blockStruct.put(SEAL_TYPE, blockJson.optString(SEAL_TYPE));
        if (!ignoreBlockFields.contains(SIGNATURE))
            blockStruct.put(SIGNATURE, blockJson.optString(SIGNATURE));
        if (!ignoreBlockFields.contains(PUBLIC_KEY))
            blockStruct.put(PUBLIC_KEY, blockJson.optString(PUBLIC_KEY));
        if (!ignoreBlockFields.contains(MAIN_CHAIN))
            blockStruct.put(MAIN_CHAIN, blockJson.optString(MAIN_CHAIN));

        String blockHash = blockStruct.getString(HASH);
        JSONArray txnArray = blockJson.optJSONArray(TRANSACTIONS);
        if (txnArray != null) {
            List<Struct> txnStructs = new ArrayList<>();
            List<String> txnHashes = new ArrayList<>();
            for (int i = 0; i < txnArray.length(); i++) {
                JSONObject txnJson = txnArray.getJSONObject(i);

                if (!ignoreBlockFields.contains(TRANSACTIONS)) {
                    Struct txnStruct = TransactionConverter.convertFromJSON(blockHash, txnJson, ignoreTransactionFields);
                    txnStructs.add(txnStruct);
                }

                if(!ignoreBlockFields.contains(TRANSACTION_HASHES)){
                    txnHashes.add(txnJson.getString(HASH));
                }
            }

            if (!publishTransactionsSeparately && !ignoreBlockFields.contains(TRANSACTIONS))
                blockStruct.put(BlockSchema.TRANSACTIONS, txnStructs);
            else
                blockStruct.put(TRANSACTIONS, Collections.EMPTY_LIST);

            if(publishTransactionsSeparately && !ignoreBlockFields.contains(TRANSACTION_HASHES))
                blockStruct.put(TRANSACTION_HASHES, txnHashes);
            else
                blockStruct.put(TRANSACTION_HASHES, Collections.EMPTY_LIST);

            result.setTransactions(txnStructs);
        }

        result.setBlock(blockStruct);
        return result;
    }


}
