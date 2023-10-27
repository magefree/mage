package mage.util;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author LevelX2
 */
public final class DeckUtil {

    private static final Logger logger = Logger.getLogger(DeckUtil.class);

    public static long fixedHash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            h = 31 * h + string.charAt(i);
        }
        return h;
    }

    public static String writeTextToTempFile(String text) {
        return writeTextToTempFile("cbimportdeck", ".txt", text);
    }

    public static String writeTextToTempFile(String filePrefix, String fileSuffix, String text) {
        String filePath;
        try {
            filePath = File.createTempFile(filePrefix, fileSuffix).getPath();
        } catch (IOException e) {
            logger.error("Couldn't create temp file for deck", e);
            return null;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(text);
            return filePath;
        } catch (IOException e) {
            logger.error("Can't write deck file to temp file", e);
        }
        return null;
    }
}
