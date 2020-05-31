package com.bloxbean.kafka.connectors.web3.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    public static String readFileFromResource(String path) throws IOException {
        Class clazz = FileUtil.class;
        InputStream inputStream = clazz.getResourceAsStream(path);
        String data = readFromInputStream(inputStream);
        return data;
    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
