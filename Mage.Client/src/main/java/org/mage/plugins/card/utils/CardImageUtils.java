package org.mage.plugins.card.utils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.prefs.Preferences;
import mage.client.MageFrame;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import net.java.truevfs.access.TFile;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.properties.SettingsManager;

public class CardImageUtils {

    private static final HashMap<CardDownloadData, String> pathCache = new HashMap<>();
    private static final Logger log = Logger.getLogger(CardImageUtils.class);

    /**
     *
     * @param card
     * @return String if image exists, else null
     */
    public static String generateTokenImagePath(CardDownloadData card) {
        if (card.isToken()) {
            if (pathCache.containsKey(card)) {
                return pathCache.get(card);
            }
            String filePath = getTokenImagePath(card);
            TFile file = new TFile(filePath);

            if (!file.exists() && card.getTokenSetCode() != null) {
                filePath = searchForCardImage(card);
                file = new TFile(filePath);
            }

            if (file.exists()) {
                pathCache.put(card, filePath);
                return filePath;
            }
        }
        log.warn("Token image file not found: " + card.getTokenSetCode() + " - " + card.getName());            
        return null;
    }

    private static String getTokenImagePath(CardDownloadData card) {
        String filename = generateImagePath(card);

        TFile file = new TFile(filename);
        if (!file.exists()) {
            CardDownloadData updated = new CardDownloadData(card);
            updated.setName(card.getName() + " 1");
            filename = generateImagePath(updated);
            file = new TFile(filename);
            if (!file.exists()) {
                updated = new CardDownloadData(card);
                updated.setName(card.getName() + " 2");
                filename = generateImagePath(updated);
            }
        }

        return filename;
    }

    private static String searchForCardImage(CardDownloadData card) {
        TFile file;
        String path;
        CardDownloadData c = new CardDownloadData(card);
        c.setSet(card.getTokenSetCode());
        path = getTokenImagePath(c);
        file = new TFile(path);
        if (file.exists()) {
            pathCache.put(card, path);
            return path;
        }

//        for (String set : SettingsManager.getIntance().getTokenLookupOrder()) {
//            c.setSet(set);
//            path = getTokenImagePath(c);
//            file = new TFile(path);
//            if (file.exists()) {
//                pathCache.put(card, path);
//                return path;
//            }
//        }
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

    private static String getImageDir(CardDownloadData card, String imagesPath) {
        if (card.getSet() == null) {
                return "";
            }
        String set = updateSet(card.getSet(), false).toUpperCase();
        String imagesDir = (imagesPath != null ? imagesPath :  Constants.IO.imageBaseDir);
        if (card.isToken()) {
            return buildTokenPath(imagesDir, set);
        } else {
            return buildPath(imagesDir, set);
        }
    }
    
    private static String buildTokenPath(String imagesDir, String set) {
        if (PreferencesDialog.isSaveImagesToZip()) {
            return imagesDir + TFile.separator + "TOK" + ".zip" + TFile.separator + set;
        } else {
            return imagesDir + TFile.separator + "TOK" + TFile.separator + set;
        }
    }

    private static String buildPath(String imagesDir, String set) {
        if (PreferencesDialog.isSaveImagesToZip()) {
            return imagesDir + TFile.separator + set + ".zip" + TFile.separator + set;
        } else {
            return imagesDir + TFile.separator + set;
        }
    }

    public static String generateImagePath(CardDownloadData card) {
        String useDefault = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_USE_DEFAULT, "true");
        String imagesPath = useDefault.equals("true") ? null : PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PATH, null);

        String imageDir = getImageDir(card, imagesPath);
        String imageName;

        String type = card.getType() != 0 ? " " + Integer.toString(card.getType()) : "";
        String name = card.getName().replace(":", "").replace("//", "-");

        if (card.getUsesVariousArt()) {
            imageName = name + "." + card.getCollectorId() + ".full.jpg";
        } else {
            imageName = name + type + ".full.jpg";
        }

        if (new TFile(imageDir).exists() && !new TFile(imageDir + TFile.separator + imageName).exists()) {
            for (String fileName : new TFile(imageDir).list()) {
                if (fileName.toLowerCase().equals(imageName.toLowerCase())) {
                    imageName = fileName;
                    break;
                }
            }
        }

        return imageDir + TFile.separator + imageName;
    }
    
    public static Proxy getProxyFromPreferences() {
        Preferences prefs = MageFrame.getPreferences();
        Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
        if (!proxyType.equals(ProxyType.NONE)) {
            String proxyServer = prefs.get("proxyAddress", "");
            int proxyPort = Integer.parseInt(prefs.get("proxyPort", "0"));
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, proxyPort));
        }
        return null;
    }
}
