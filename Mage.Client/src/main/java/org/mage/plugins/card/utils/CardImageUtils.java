package org.mage.plugins.card.utils;

import mage.cards.repository.TokenRepository;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.view.CardView;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

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
        if (card.getImageNumber() != 0) {
            prefixType = " " + card.getImageNumber();
        }

        String cardName = prepareCardNameForFile(card.getName());

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

    /**
     * Special version for CardView and direct images info
     * (real card images uses image cache and key logic, see ImageCache.getKey)
     *
     * @return relative image path or "ERROR + reason"
     */
    public static String buildImagePathToCardView(CardView card) {
        String imageFile;
        String imageFileName = card.getImageFileName();
        if (imageFileName.isEmpty()) {
            imageFileName = card.getName();
        }

        if (imageFileName.isEmpty()) {
            return "ERROR: empty image file name, object type - " + card.getMageObjectType();
        }

        boolean isTokenRepository = card.getMageObjectType().isUseTokensRepository()
                || card.getExpansionSetCode().equals(TokenRepository.XMAGE_TOKENS_SET_CODE);
        // if token from a card then must use card repository instead
        if (isTokenRepository && !card.getCardNumber().isEmpty()) {
            isTokenRepository = false;
        }

        if (isTokenRepository) {
            // images from tokens repository (token + xmage token)
            CardDownloadData cardData = new CardDownloadData(
                    imageFileName.replace(" Token", ""),
                    card.getExpansionSetCode(),
                    card.getCardNumber(),
                    card.getUsesVariousArt(),
                    card.getImageNumber());
            cardData.setToken(true);
            imageFile = CardImageUtils.buildImagePathToCardOrToken(cardData);
        } else {
            // images from cards repository (card + token from cards)
            CardDownloadData cardData = new CardDownloadData(
                    imageFileName,
                    card.getExpansionSetCode(),
                    card.getCardNumber(),
                    card.getUsesVariousArt(), // TODO: need to use usesVariousArt instead card?
                    card.getImageNumber()
            );
            imageFile = CardImageUtils.buildImagePathToCardOrToken(cardData);
        }
        return imageFile;
    }

    public static String generateFaceImagePath(String cardName, String setCode) {
        return getImagesDir() + File.separator + "FACE" + File.separator + fixSetNameForWindows(setCode) + File.separator + prepareCardNameForFile(cardName) + ".jpg";
    }

    public static void checkAndFixImageFiles() {
        // search broken, temp or outdated files and delete it

        // make sure all archives was closed (e.g. on second call of download dialog)
        try {
            TVFS.umount();
        } catch (FsSyncException e) {
            LOGGER.error("Couldn't unmount zip files on searching broken images " + e, e);
        }

        // real images check is slow, so it used on images download only (not here)
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
