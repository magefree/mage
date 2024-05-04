package mage.util;

import mage.cards.decks.Deck;
import mage.players.Player;
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
     */
    public static long getDeckHash(List<String> mainCards, List<String> sideboardCards) {
        List<String> all = new ArrayList<>(mainCards);
        all.addAll(sideboardCards);
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

    /**
     * make sure it's the same deck (player do not add or remove something)
     *
     * @param newDeck will be clear on cheating
     */
    public boolean checkDeckForModification(String checkInfo, Player player, Deck oldDeck, Deck newDeck) {
        boolean isGood = (oldDeck.getDeckHash() == newDeck.getDeckHash());
        if (!isGood) {
            String message = String.format("Found cheating player [%s] in [%s] with changed deck, main %d -> %d, side %d -> %d",
                    player.getName(),
                    checkInfo,
                    oldDeck.getCards().size(),
                    newDeck.getCards().size(),
                    oldDeck.getSideboard().size(),
                    newDeck.getSideboard().size()
            );
            logger.error(message);
            newDeck.getCards().clear();
            newDeck.getSideboard().clear();
        }
        return isGood;
    }
}
