package org.mage.plugins.card.utils;

import de.schlichtherle.truezip.file.TFile;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.images.CardInfo;
import org.mage.plugins.card.properties.SettingsManager;

public class CardImageUtils {

    private static final Pattern basicLandPattern = Pattern.compile("^(Forest|Mountain|Swamp|Island|Plains)$");
    private static HashMap<CardInfo, String> pathCache = new HashMap<CardInfo, String>();

    /**
     * Get path to image for specific card.
     * 
     * @param card
     *            card to get path for
     * @return String if image exists, else null
     */
    public static String getImagePath(CardInfo card) {
        String filePath;

        TFile file;
        if (card.isToken()) {
            if (pathCache.containsKey(card)) {
                return pathCache.get(card);
            }
            filePath = getTokenImagePath(card);
            file = new TFile(filePath);

            if (!file.exists()) {
                filePath = searchForCardImage(card);
                file = new TFile(filePath);
            }

            if (file.exists()) {
                pathCache.put(card, filePath);
            }
        } else {
            filePath = getImagePath(card, null);
            file = new TFile(filePath);
        }

        if (file.exists()) {
            return filePath;
        } else {
            return null;
        }
    }

    private static String getTokenImagePath(CardInfo card) {
        String filename = getImagePath(card, null);

        TFile file = new TFile(filename);
        if (!file.exists()) {
            CardInfo updated = new CardInfo(card);
            updated.setName(card.getName() + " 1");
            filename = getImagePath(updated, null);
            file = new TFile(filename);
            if (!file.exists()) {
                updated = new CardInfo(card);
                updated.setName(card.getName() + " 2");
                filename = getImagePath(updated, null);
            }
        }

        return filename;
    }

    private static String searchForCardImage(CardInfo card) {
        TFile file;
        String path;
        CardInfo c = new CardInfo(card);

        for (String set : SettingsManager.getIntance().getTokenLookupOrder()) {
            c.setSet(set);
            path = getTokenImagePath(c);
            file = new TFile(path);
            if (file.exists()) {
                pathCache.put(card, path);
                return path;
            }
        }
        return "";
    }

    public static String updateSet(String cardSet, boolean forUrl) {
        String set = cardSet.toLowerCase();
        if (set.equals("con")) {
            set = "cfx";
        }
        if (forUrl) {
            set = SettingsManager.getIntance().getSetNameReplacement(set);
        }
        return set;
    }

    private static String getImageDir(CardInfo card, String imagesPath) {
        if (card.getSet() == null) {
            return "";
        }
        String set = updateSet(card.getSet(), false).toUpperCase();
        String imagesDir = (imagesPath != null ? imagesPath :  Constants.IO.imageBaseDir);
        if (card.isToken()) {
            return imagesDir + TFile.separator + "TOK" + ".zip" + TFile.separator + set;
        } else {
            return imagesDir + TFile.separator + set + ".zip" + TFile.separator + set;
        }
    }

    public static String getImagePath(CardInfo card, String imagesPath) {
        String imageDir = getImageDir(card, imagesPath);
        String type = card.getType() != 0 ? " " + Integer.toString(card.getType()) : "";
        String name = card.getName();

        String path, capitalizedPath;
        if (basicLandPattern.matcher(name).matches()) {
            path = imageDir + TFile.separator + name + "." + card.getCollectorId() + ".full.jpg";
            capitalizedPath = imageDir + TFile.separator + capitalize(name) + "." + card.getCollectorId() + ".full.jpg";

        } else {
            path = imageDir + TFile.separator + name + type + ".full.jpg";
            capitalizedPath = imageDir + TFile.separator + capitalize(name) + type + ".full.jpg";
        }

        return new TFile(capitalizedPath).exists() ? capitalizedPath : path;
    }

    private static String capitalize(String str) {
        int delimLen = -1;
        if (str.isEmpty() || delimLen == 0) {
            return str;
        }
        char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            char ch = buffer[i];
            if (Character.isWhitespace(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }
}
