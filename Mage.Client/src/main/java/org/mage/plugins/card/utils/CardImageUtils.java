package org.mage.plugins.card.utils;

import mage.client.MageFrame;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
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
import java.util.Locale;
import java.util.prefs.Preferences;

public final class CardImageUtils {

    private static final Logger LOGGER = Logger.getLogger(CardImageUtils.class);

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

    public static String fixSetNameForWindows(String setCode) {
        // windows can't create con folders
        if (setCode.equals("CON") || setCode.equals("con")) {
            return "COX";
        } else {
            return setCode;
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

    public static String buildImagePathToSet(CardDownloadData card) {
        if (card.getSet() == null) {
            throw new IllegalArgumentException("Card " + card.getName() + " have empty set.");
        }
        String setCode = card.getSet().toUpperCase(Locale.ENGLISH);

        if (card.isToken()) {
            return buildImagePathToSetAsToken(setCode);
        } else {
            return buildImagePathToSetAsCard(setCode);
        }
    }

    private static String buildImagePathToSetAsCard(String setCode) {
        String imagesPath = getImagesDir() + File.separator;

        if (PreferencesDialog.isSaveImagesToZip()) {
            return imagesPath + fixSetNameForWindows(setCode) + ".zip" + File.separator + fixSetNameForWindows(setCode) + File.separator;
        } else {
            return imagesPath + fixSetNameForWindows(setCode) + File.separator;
        }
    }

    private static String buildImagePathToSetAsToken(String setCode) {
        return buildImagePathToTokens() + fixSetNameForWindows(setCode) + File.separator;
    }

    public static String buildImagePathToCardOrToken(CardDownloadData card) {

        String setPath = buildImagePathToSet(card);

        String prefixType = "";
        if (card.getType() != 0) {
            prefixType = " " + card.getType();
        }

        String cardName = card.getFileName();
        if (cardName.isEmpty()) {
            cardName = prepareCardNameForFile(card.getName());
        }

        String finalFileName;
        if (card.getUsesVariousArt()) {
            // different arts uses name + collector id
            finalFileName = cardName + prefixType + '.' + card.getCollectorIdAsFileName() + ".full.jpg";
        } else {
            // basic arts uses name
            finalFileName = cardName + prefixType + ".full.jpg";
        }

        return setPath + finalFileName;
    }

    public static String generateFaceImagePath(String cardName, String setCode) {
        return getImagesDir() + File.separator + "FACE" + File.separator + fixSetNameForWindows(setCode) + File.separator + prepareCardNameForFile(cardName) + ".jpg";
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
        // search broken, temp or outdated files and delete it
        Path rootPath = new File(CardImageUtils.getImagesDir()).toPath();
        if (!Files.exists(rootPath)) {
            return;
        }

        Collection<Path> brokenFilesList = new ArrayList<>();
        Collection<Path> tempFilesList = new ArrayList<>();
        Collection<Path> outdatedFilesList = new ArrayList<>();
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

                    // 3. outdated files delete without warning
                    if (file.toString().endsWith(".thumb.zip")) {
                        outdatedFilesList.add(file);
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            LOGGER.error("Can't load files list from images folder: " + rootPath.toAbsolutePath().toString(), e);
        }

        // temp and outdated files must be deleted without errors
        Collection<Path> list = new ArrayList<>();
        list.addAll(tempFilesList);
        list.addAll(outdatedFilesList);
        for (Path path : list) {
            try {
                Files.delete(path);
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
                    LOGGER.error("Images check: ERROR, can't delete broken file: " + brokenFile.toAbsolutePath(), e);
                    break;
                }
            }
        }
    }
}
