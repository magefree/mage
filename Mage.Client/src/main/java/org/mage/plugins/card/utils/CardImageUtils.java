package org.mage.plugins.card.utils;

import mage.client.MageFrame;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import net.java.truevfs.access.TFile;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.prefs.Preferences;

public final class CardImageUtils {

    private static final HashMap<CardDownloadData, String> pathCache = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(CardImageUtils.class);

    /**
     * @param card
     * @return String if image exists, else null
     */
    public static String generateTokenImagePath(CardDownloadData card) {
        if (card.isToken()) {
            String filePath = getTokenImagePath(card);
            if (pathCache.containsKey(card)) {
                if (filePath.equals(pathCache.get(card))) {
                    return pathCache.get(card);
                }
            }
            TFile file = new TFile(filePath);

            if (!file.exists() && card.getTokenSetCode() != null) {
                filePath = searchForCardImage(card);
                file = new TFile(filePath);
            }

            if (file.exists()) {
                pathCache.put(card, filePath);
                return filePath;
            }

            //log.warn("Token image file not found. Set: " + card.getSet() + " Token Set Code: " + card.getTokenSetCode() + " Name: " + card.getName() + " File path: " + getTokenImagePath(card));
        } else {
            LOGGER.warn("Trying to get token path for non token card. Set: " + card.getSet() + " Set Code: " + card.getTokenSetCode() + " Name: " + card.getName());
        }
        return null;
    }

    /**
     * @param card
     * @return String regardless of whether image exists
     */
    public static String generateFullTokenImagePath(CardDownloadData card) {
        if (card.isToken()) {
            return getTokenImagePath(card);
        }
        return "";
    }

    private static String getTokenImagePath(CardDownloadData card) {
        String filename = buildImagePathToCard(card);

        TFile file = new TFile(filename);
        if (!file.exists()) {
            String tokenDescriptorfilename = generateTokenDescriptorImagePath(card);
            if (!tokenDescriptorfilename.isEmpty()) {
                file = new TFile(filename);
                if (file.exists()) {
                    return tokenDescriptorfilename;
                }
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
        return generateTokenDescriptorImagePath(card);
    }

    public static String prepareCardNameForFile(String cardName) {
        return cardName
                .replace(":", "")
                .replace("\"", "")
                .replace("//", "-");
    }

    public static String getImagesDir() {
        // return real images dir (path without separator)

        String path = null;

        // user path
        if (!PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_USE_DEFAULT, "true").equals("true")) {
            path = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_PATH, null);
        }

        // default path
        if (path == null) {
            path = Constants.IO.DEFAULT_IMAGES_DIR;
        }

        while (path.endsWith(File.separator)) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }

    public static String buildImagePathToDefault(String defaultFileName) {
        // default downloadable images like card back
        return getImagesDir() + Constants.RESOURCE_PATH_DEFAULT_IMAGES + File.separator + defaultFileName;
    }

    public static String fixSetNameForWindows(String set) {
        // windows can't create con folders
        if (set.equals("CON") || set.equals("con")) {
            return "COX";
        } else {
            return set;
        }
    }

    public static String buildImagePathToTokens() {
        String imagesPath = getImagesDir() + File.separator;

        if (PreferencesDialog.isSaveImagesToZip()) {
            return imagesPath + "TOK.zip" + File.separator;
        } else {
            return imagesPath + "TOK" + File.separator;
        }
    }

    private static String buildImagePathToTokenDescriptor(CardDownloadData card) {
        return buildImagePathToTokens() + card.getTokenDescriptor() + ".full.jpg";
    }

    public static String buildImagePathToSet(CardDownloadData card) {

        if (card.getSet() == null) {
            throw new IllegalArgumentException("Card " + card.getName() + " have empty set.");
        }

        String set = card.getSet().toUpperCase(Locale.ENGLISH);

        if (card.isToken()) {
            return buildImagePathToSetAsToken(set);
        } else {
            return buildImagePathToSetAsCard(set);
        }
    }

    private static String buildImagePathToSetAsCard(String set) {
        String imagesPath = getImagesDir() + File.separator;

        if (PreferencesDialog.isSaveImagesToZip()) {
            return imagesPath + fixSetNameForWindows(set) + ".zip" + File.separator + fixSetNameForWindows(set) + File.separator;
        } else {
            return imagesPath + fixSetNameForWindows(set) + File.separator;
        }
    }

    private static String buildImagePathToSetAsToken(String set) {
        return buildImagePathToTokens() + fixSetNameForWindows(set) + File.separator;
    }

    public static String buildImagePathToCard(CardDownloadData card) {

        String setPath = buildImagePathToSet(card);

        String prefixType = "";
        if (card.getType() != 0) {
            prefixType = " " + card.getType();
        }

        String cardName = card.getFileName();
        if (cardName.isEmpty()) {
            cardName = prepareCardNameForFile(card.getName());
        }

        String finalFileName = "";
        if (card.getUsesVariousArt()) {
            // different arts uses name + collector id
            finalFileName = cardName + prefixType + '.' + card.getCollectorIdAsFileName() + ".full.jpg";
        } else {
            // basic arts uses name
            finalFileName = cardName + prefixType + ".full.jpg";
        }

        /* 2019-01-12: no needs in name corrections, all files must be same and auto-downloaded
        // if image file exists, correct name (for case sensitive systems)
        // use TFile for zips
        TFile dirFile = new TFile(setPath);
        TFile imageFile = new TFile(setPath + finalFileName);
        // warning, zip files can be broken
        try {
            if (dirFile.exists() && !imageFile.exists()) {
                // search like names
                for (String fileName : dirFile.list()) {
                    if (fileName.toLowerCase(Locale.ENGLISH).equals(finalFileName.toLowerCase(Locale.ENGLISH))) {
                        finalFileName = fileName;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Can't read card name from file, may be it broken: " + setPath);
        }
        */

        return setPath + finalFileName;
    }

    public static String generateFaceImagePath(String cardname, String set) {
        return getImagesDir() + File.separator + "FACE" + File.separator + fixSetNameForWindows(set) + File.separator + prepareCardNameForFile(cardname) + ".jpg";
    }

    public static String generateTokenDescriptorImagePath(CardDownloadData card) {

        String straightImageFile = buildImagePathToTokenDescriptor(card);
        TFile file = new TFile(straightImageFile);
        if (file.exists()) {
            return straightImageFile;
        }

        straightImageFile = straightImageFile.replaceFirst("\\.[0-9]+\\.[0-9]+", ".X.X");
        file = new TFile(straightImageFile);
        if (file.exists()) {
            return straightImageFile;
        }

        straightImageFile = straightImageFile.replaceFirst("\\.X\\.X", ".S.S");
        file = new TFile(straightImageFile);
        if (file.exists()) {
            return straightImageFile;
        }
        return "";
    }

    public static Proxy getProxyFromPreferences() {
        Preferences prefs = MageFrame.getPreferences();
        Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
        if (proxyType != ProxyType.NONE) {
            String proxyServer = prefs.get("proxyAddress", "");
            int proxyPort = Integer.parseInt(prefs.get("proxyPort", "0"));
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, proxyPort));
        }
        return null;
    }

    public static Document downloadHtmlDocument(String urlString) throws NumberFormatException, IOException {
        Preferences prefs = MageFrame.getPreferences();
        Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
        Document doc;
        if (proxyType == ProxyType.NONE) {
            doc = Jsoup.connect(urlString).timeout(60 * 1000).get();
        } else {
            String proxyServer = prefs.get("proxyAddress", "");
            int proxyPort = Integer.parseInt(prefs.get("proxyPort", "0"));
            URL url = new URL(urlString);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, proxyPort));
            HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
            uc.setConnectTimeout(10000);
            uc.setReadTimeout(60000);
            uc.connect();

            String line;
            StringBuffer tmp = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
            doc = Jsoup.parse(String.valueOf(tmp));
        }
        return doc;
    }

    public static void checkAndFixImageFiles() {
        // search broken files and delete it (zero size files)
        // search temp files and delete it (.tmp files from zip library)
        Path rootPath = new File(CardImageUtils.getImagesDir()).toPath();
        Collection<Path> brokenFilesList = new ArrayList<>();
        Collection<Path> tempFilesList = new ArrayList<>();
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 1. broken files (need zero size files): delete with warning
                    if (attrs.size() == 0) {
                        brokenFilesList.add(file);
                        return FileVisitResult.CONTINUE;
                    }

                    // 2. temp files delete without warning
                    if (file.toString().endsWith(".tmp")) {
                        tempFilesList.add(file);
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            LOGGER.error("Can't load files list from images folder: " + rootPath.toAbsolutePath().toString(), e);
        }

        // temp files must be deleted without errors
        for (Path tempFile : tempFilesList) {
            try {
                Files.delete(tempFile);
            } catch (Throwable e) {
                // ignore any error (e.g. it opened by xmage app)
            }
        }

        // broken files must be informed
        if (!brokenFilesList.isEmpty()) {
            LOGGER.warn("Images: found " + brokenFilesList.size() + " broken files. Trying to fix it...");
            for (Path brokenFile : brokenFilesList) {
                try {
                    Files.delete(brokenFile);
                } catch (Throwable e) {
                    // stop clean on any error
                    LOGGER.error("Images check: ERROR, can't delete broken file: " + brokenFile.toAbsolutePath().toString(), e);
                    break;
                }
            }
        }
    }
}
