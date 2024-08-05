package mage.util;

import mage.cards.decks.DeckValidator;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LevelX2, JayDi85
 */
public final class DeckUtil {

    private static final Logger logger = Logger.getLogger(DeckUtil.class);

    /**
     * Find deck hash (main + sideboard)
     * It must be same after sideboard (do not depend on main/sb structure)
     *
     * @param ignoreMainBasicLands - drafts allow to use any basic lands, so ignore it for hash calculation
     */
    public static long getDeckHash(List<String> mainCards, List<String> sideboardCards, boolean ignoreMainBasicLands) {
        List<String> all = new ArrayList<>(mainCards);
        all.addAll(sideboardCards);

        // related rules:
        // 7.2 Card Use in Limited Tournaments
        // Players may add an unlimited number of cards named Plains, Island, Swamp, Mountain, or Forest to their
        // deck and sideboard. They may not add additional snow basic land cards (e.g., Snow-Covered Forest, etc)
        // or Wastes basic land cards, even in formats in which they are legal.
        if (ignoreMainBasicLands) {
            all.removeIf(DeckValidator.MAIN_BASIC_LAND_NAMES::contains);
        }

        Collections.sort(all);
        return getStringHash(all.toString());
    }

    private static long getStringHash(String string) {
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
        BufferedWriter bw = null;
        try {
            File temp = File.createTempFile(filePrefix, fileSuffix);
            bw = new BufferedWriter(new FileWriter(temp));
            bw.write(text);
            return temp.getPath();
        } catch (IOException e) {
            logger.error("Can't write deck file to temp file", e);
        } finally {
            StreamUtils.closeQuietly(bw);
        }
        return null;
    }
}
