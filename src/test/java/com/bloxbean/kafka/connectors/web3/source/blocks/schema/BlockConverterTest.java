package com.bloxbean.kafka.connectors.web3.source.blocks.schema;

import com.bloxbean.kafka.connectors.web3.util.FileUtil;
import kong.unirest.json.JSONObject;
import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockSchema.HASH;
import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockSchema.TIMESTAMP;
import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.BlockSchema.*;
import static com.bloxbean.kafka.connectors.web3.source.blocks.schema.TransactionSchema.*;
import static org.junit.jupiter.api.Assertions.*;

class BlockConverterTest {

    @Test
    void convertFromJSONForPOWBlock() throws IOException {
        //given
        String jsonStr = FileUtil.readFileFromResource("/aion-block-6139184.json");
        JSONObject blockJson = new JSONObject(jsonStr);

        //When
        BlockConverter blockConverter = new BlockConverter();
        Struct struct = blockConverter.convertFromJSON(blockJson, false, new HashSet<>(), new HashSet<>()).getBlock();

        //then
        assertEquals(blockJson.getLong(NUMBER), struct.getInt64(NUMBER).longValue());
        assertEquals(blockJson.getString(HASH), struct.getString(HASH));
        assertEquals(blockJson.getString(PARENT_HASH), struct.get(PARENT_HASH));
        assertEquals(blockJson.getString(LOGS_BLOOM), struct.getString(LOGS_BLOOM));
        assertEquals(blockJson.getString(TRANSACTIONS_ROOT), struct.getString(TRANSACTIONS_ROOT));
        assertEquals(blockJson.getString(STATE_ROOT), struct.getString(STATE_ROOT));
        assertEquals(blockJson.getString(RECEIPTS_ROOT), struct.getString(RECEIPTS_ROOT));
        assertEquals(blockJson.getString(MINER), struct.getString(MINER));

        assertEquals("14460887", struct.getString(DIFFICULTY));
        assertEquals("649149422609575888269436786897364", struct.getString(TOTAL_DIFFICULTY));

        assertEquals(blockJson.getString(EXTRA_DATA), struct.getString(EXTRA_DATA));

        assertEquals("3985", struct.getString(SIZE));
        assertEquals("14994332", struct.getString(GAS_LIMIT));
        assertEquals("252000", struct.getString(GAS_USED));
        assertEquals("14994332", struct.getString(NRG_LIMIT));
        assertEquals("252000", struct.getString(NRG_USED));

        assertEquals(1590815350L, struct.getInt64(TIMESTAMP).longValue());
        assertEquals("", struct.getString(SEED));
        assertEquals("0x1", struct.getString(SEAL_TYPE));
        assertEquals("", struct.getString(SIGNATURE));
        assertEquals("", struct.getString(PUBLIC_KEY));
        assertEquals("true", struct.getString(MAIN_CHAIN));

        //Check transactions
        List<Struct> transactions = struct.getArray("transactions");
        assertEquals(12, transactions.size());

        //1st txn
        assertEquals("10000000000", transactions.get(0).getString(NRG_PRICE));
        assertEquals("90000", transactions.get(0).getString(NRG));
        assertEquals(0, transactions.get(0).getInt64(TRANSACTION_INDEX).longValue());
        assertEquals(3358665, transactions.get(0).getInt64(NONCE).longValue());
        assertEquals("0x", transactions.get(0).getString(INPUT));
        assertEquals(6139184, transactions.get(0).getInt64(BLOCK_NUMBER).longValue());
        assertEquals("90000", transactions.get(0).getString(GAS));
        assertEquals("0xa00983f07c11ee9160a64dd3ba3dc3d1f88332a2869f25725f56cbd0be32ef7a",
                transactions.get(0).getString(FROM));
        assertEquals("0xa0604716902de61349ff4aeac01de911fcb91b513ec39007ff761e56fa117f62",
                transactions.get(0).getString(TO));

        assertEquals("1086404641135000000", transactions.get(0).getString(VALUE));
        assertEquals("0x5505d9f98cd0ec27fc6cf688cd69050205024ea5116f919e8aff07ee2f465893",
                transactions.get(0).getString(HASH));
        assertEquals("10000000000", transactions.get(0).getString(GAS_PRICE));
        assertEquals(1590815321816774L, transactions.get(0).getInt64(TIMESTAMP).longValue());

        //5th txn
        assertEquals("10000000000", transactions.get(4).getString(NRG_PRICE));
        assertEquals("90000", transactions.get(4).getString(NRG));
        assertEquals(4, transactions.get(4).getInt64(TRANSACTION_INDEX).longValue());
        assertEquals(3358669, transactions.get(4).getInt64(NONCE).longValue());
        assertEquals("0x", transactions.get(4).getString(INPUT));
        assertEquals(6139184, transactions.get(4).getInt64(BLOCK_NUMBER).longValue());
        assertEquals("90000", transactions.get(4).getString(GAS));
        assertEquals("0xa00983f07c11ee9160a64dd3ba3dc3d1f88332a2869f25725f56cbd0be32ef7a",
                transactions.get(4).getString(FROM));
        assertEquals("0xa0d2cd966e2a827754d5379b7e318ce2110a90a270d33357242bb3b6c945d751",
                transactions.get(4).getString(TO));

        assertEquals("1029016062353000000", transactions.get(4).getString(VALUE));
        assertEquals("0x7c19cea4c2572cbfd5bf0571bb36368acbc409d5f403dd7994da7370e4f43f17",
                transactions.get(4).getString(HASH));
        assertEquals("10000000000", transactions.get(4).getString(GAS_PRICE));
        assertEquals(1590815324652434L, transactions.get(4).getInt64(TIMESTAMP).longValue());
    }

    @Test
    void convertFromJSONForPOSBlock() throws IOException {
        //given
        String jsonStr = FileUtil.readFileFromResource("/aion-block-6139191.json");
        JSONObject blockJson = new JSONObject(jsonStr);

        //When
        BlockConverter blockConverter = new BlockConverter();
        Struct struct = blockConverter.convertFromJSON(blockJson, false, new HashSet<>(), new HashSet<>()).getBlock();

        //then
        assertEquals(blockJson.getLong(NUMBER), struct.getInt64(NUMBER).longValue());
        assertEquals(blockJson.getString(HASH), struct.getString(HASH));
        assertEquals(blockJson.getString(PARENT_HASH), struct.get(PARENT_HASH));
        assertEquals(blockJson.getString(LOGS_BLOOM), struct.getString(LOGS_BLOOM));
        assertEquals(blockJson.getString(TRANSACTIONS_ROOT), struct.getString(TRANSACTIONS_ROOT));
        assertEquals(blockJson.getString(STATE_ROOT), struct.getString(STATE_ROOT));
        assertEquals(blockJson.getString(RECEIPTS_ROOT), struct.getString(RECEIPTS_ROOT));
        assertEquals(blockJson.getString(MINER), struct.getString(MINER));

        assertEquals("667211573736065753823069309", struct.getString(DIFFICULTY));
        assertEquals("649152093044369656457321358272015", struct.getString(TOTAL_DIFFICULTY));

        assertEquals(blockJson.getString(EXTRA_DATA), struct.getString(EXTRA_DATA));

        assertEquals("1239", struct.getString(SIZE));
        assertEquals("15008964", struct.getString(GAS_LIMIT));
        assertEquals("163506", struct.getString(GAS_USED));

        assertEquals("15008964", struct.getString(NRG_LIMIT));
        assertEquals("163506", struct.getString(NRG_USED));

        assertEquals(1590815426L, struct.getInt64(TIMESTAMP).longValue());
        assertEquals(blockJson.getString(SEED), struct.getString(SEED));
        assertEquals("0x2", struct.getString(SEAL_TYPE));
        assertEquals(blockJson.getString(SIGNATURE), struct.getString(SIGNATURE));
        assertEquals(blockJson.getString(PUBLIC_KEY), struct.getString(PUBLIC_KEY));
        assertEquals("true", struct.getString(MAIN_CHAIN));

        //Check transactions
        List<Struct> transactions = struct.getArray("transactions");
        assertEquals(3, transactions.size());

        //2ns txn
        assertEquals("10000000000", transactions.get(2).getString(NRG_PRICE));
        assertEquals("2000000", transactions.get(2).getString(NRG));
        assertEquals(2, transactions.get(2).getInt64(TRANSACTION_INDEX).longValue());
        assertEquals(44711, transactions.get(2).getInt64(NONCE).longValue());
        assertEquals("0x21001266696e616c697a65556e64656c6567617465060000000000000b52", transactions.get(2).getString(INPUT));
        assertEquals(6139191, transactions.get(2).getInt64(BLOCK_NUMBER).longValue());
        assertEquals("2000000", transactions.get(2).getString(GAS));
        assertEquals("0xa0e3976a5cd369e679743cf0255fff991490b5a1afc541e43f4b2a33feaafe38",
                transactions.get(2).getString(FROM));
        assertEquals("0xa008e42a76e2e779175c589efdb2a0e742b40d8d421df2b93a8a0b13090c7cc8",
                transactions.get(2).getString(TO));

        assertEquals("0", transactions.get(2).getString(VALUE));
        assertEquals("0x55932c80ed8b27cfcccdcd1b3dc079bf7a0500e737c705e4b21bc40f51ef5e6b",
                transactions.get(2).getString(HASH));
        assertEquals("10000000000", transactions.get(2).getString(GAS_PRICE));
        assertEquals(1590815424367443L, transactions.get(2).getInt64(TIMESTAMP).longValue());
    }

    @Test
    void convertFromJSONOnlyIncludeTxnHashses() throws IOException {
        //given
        String jsonStr = FileUtil.readFileFromResource("/aion-block-6139184.json");
        JSONObject blockJson = new JSONObject(jsonStr);

        //When
        BlockConverter blockConverter = new BlockConverter();
        Struct struct = blockConverter.convertFromJSON(blockJson, true, new HashSet<>(), new HashSet<>()).getBlock();

        //then
        assertEquals(blockJson.getLong(NUMBER), struct.getInt64(NUMBER).longValue());
        assertEquals(blockJson.getString(HASH), struct.getString(HASH));

        //Check transactions
        List<Struct> transactions = struct.getArray(TRANSACTIONS);
        assertEquals(0, transactions.size());

        List<String> transactionHases = struct.getArray(TRANSACTION_HASHES);
        assertEquals(12, transactionHases.size());

        assertEquals("0x5505d9f98cd0ec27fc6cf688cd69050205024ea5116f919e8aff07ee2f465893",
                transactionHases.get(0));
        assertEquals("0x7c19cea4c2572cbfd5bf0571bb36368acbc409d5f403dd7994da7370e4f43f17",
                transactionHases.get(4));

    }

    @Test
    void convertFromJSONIgnoreBlockFields() throws IOException {
        //given
        String jsonStr = FileUtil.readFileFromResource("/aion-block-6139184.json");
        JSONObject blockJson = new JSONObject(jsonStr);

        Set ignoreBlockFields = new HashSet<>();
        ignoreBlockFields.add(DIFFICULTY);
        ignoreBlockFields.add(TOTAL_DIFFICULTY);
        ignoreBlockFields.add(STATE_ROOT);
        ignoreBlockFields.add(RECEIPTS_ROOT);
        ignoreBlockFields.add(TRANSACTION_HASHES);

        //When
        BlockConverter blockConverter = new BlockConverter();
        Struct struct = blockConverter.convertFromJSON(blockJson, true, ignoreBlockFields, new HashSet<>()).getBlock();

        //then
        assertEquals(blockJson.getLong(NUMBER), struct.getInt64(NUMBER).longValue());
        assertEquals(blockJson.getString(HASH), struct.getString(HASH));

        assertNull(struct.getString(DIFFICULTY));
        assertNull(struct.getString(TOTAL_DIFFICULTY));
        assertNull(struct.getString(STATE_ROOT));
        assertNull(struct.getString(RECEIPTS_ROOT));
        assertNotNull(struct.getString(TRANSACTIONS_ROOT));

    }

    @Test
    void convertFromJSONIgnoreTransactionFields() throws IOException {
        //given
        String jsonStr = FileUtil.readFileFromResource("/aion-block-6139184.json");
        JSONObject blockJson = new JSONObject(jsonStr);

        Set ignoreTransactionFields = new HashSet<>();
        ignoreTransactionFields.add(V);
        ignoreTransactionFields.add(R);
        ignoreTransactionFields.add(INPUT);

        //When
        BlockConverter blockConverter = new BlockConverter();
        Struct struct = blockConverter.convertFromJSON(blockJson, false, new HashSet<>(), ignoreTransactionFields).getBlock();

        //then
        assertEquals(blockJson.getLong(NUMBER), struct.getInt64(NUMBER).longValue());
        assertEquals(blockJson.getString(HASH), struct.getString(HASH));

        //Check transactions
        List<Struct> transactions = struct.getArray("transactions");
        assertEquals(12, transactions.size());

        //1st txn
        assertEquals("10000000000", transactions.get(0).getString(NRG_PRICE));
        assertEquals("90000", transactions.get(0).getString(NRG));
        assertEquals(0, transactions.get(0).getInt64(TRANSACTION_INDEX).longValue());

        assertNull(transactions.get(0).getString(V));
        assertNull(transactions.get(0).getString(R));
        assertNull(transactions.get(0).getString(INPUT));
        assertNotNull(transactions.get(0).getString(S));
    }
}