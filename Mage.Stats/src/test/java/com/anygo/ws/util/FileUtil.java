package com.anygo.ws.util;

import java.io.*;

/**
 *
 * @author noxx
 */
public final class FileUtil {

    private FileUtil() {}

    public static String readFile(String file) throws IOException {
        InputStream in = FileUtil.class.getResourceAsStream(file);
        if (in == null) {
            throw new FileNotFoundException("Couldn't find file " + file);
        }
        Reader fr = new InputStreamReader(in, "utf-8");

        BufferedReader reader = new BufferedReader(fr);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    }
}
