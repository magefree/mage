package org.mage.plugins.card.utils;

import mage.cards.repository.TokenRepository;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.view.CardView;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsMountPoint;
import net.java.truevfs.kernel.spec.FsSyncException;
import net.java.truevfs.kernel.spec.FsSyncOptions;
import net.java.truevfs.kernel.spec.FsSyncWarningException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipFile;

public final class CardImageUtils {

    private static final Logger LOGGER = Logger.getLogger(CardImageUtils.class);

    public static String prepareCardNameForFile(String cardName) {
        // remove characters that are invalid in Windows file names: \ / : * ? " < > |
        // note: // is used in split card names like "Fire // Ice" and replaced with -
        return cardName
                .replace("//", "-")
                .replace("\\", "")
                .replace("/", "")
                .replace(":", "")
                .replace("*", "")
                .replace("?", "")
                .replace("\"", "")
                .replace("<", "")
                .replace(">", "")
                .replace("|", "");
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

    public static final String DOWNLOADING_FOLDER = "downloading"; // temp files of the images download

    /**
     * Images folder must be writable for temp and images files (e.g. no access to Program Files on Windows)
     *
     * @return error message or null on fine
     */
    public static String checkImagesFolderWritable() {
        File tempFolder = new File(getImagesDir() + File.separator + DOWNLOADING_FOLDER);
        try {
            if (!tempFolder.exists() && !tempFolder.mkdirs()) {
                throw new IOException("can't create folder " + tempFolder);
            }
            Path probe = Files.createTempFile(tempFolder.toPath(), "write-test", ".tmp");
            Files.write(probe, new byte[]{1});
            Files.delete(probe);
            return null;
        } catch (Throwable e) {
            return "Images folder is not writable, choose another folder in preferences: " + getImagesDir() + " (" + e.getMessage() + ")";
        }
    }

    /**
     * Search and delete broken, temp or outdated files and broken archives
     * <p>
     * WARNING: call it only when no one writes images (TrueVFS keeps an output buffer of a mounted zip
     * as SET.zip.NNN.tmp file next to it, so it will be deleted here as a temp file)
     *
     * @return list of found problems
     */
    public static List<String> checkAndFixImageFiles() {
        List<String> problems = new ArrayList<>();

        // make sure all archives was closed (e.g. on second call of download dialog)
        try {
            TVFS.umount();
        } catch (FsSyncException e) {
            LOGGER.error("Couldn't unmount zip files on searching broken images " + e, e);
            problems.addAll(deleteBrokenArchives(e));
        }

        // real images check is slow, so it used on images download only (not here)
        Path rootPath = new File(CardImageUtils.getImagesDir()).toPath();
        if (!Files.exists(rootPath)) {
            return problems;
        }

        Collection<Path> brokenFilesList = new ArrayList<>();
        Collection<Path> tempFilesList = new ArrayList<>();
        Collection<Path> outdatedFilesList = new ArrayList<>();
        Collection<Path> archivesList = new ArrayList<>();
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

                    // 4. archives must be readable (checks later)
                    if (file.toString().endsWith(".zip") && file.getParent().equals(rootPath)) {
                        archivesList.add(file);
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
                problems.add("deleted temp or outdated file: " + path);
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
                    problems.add("deleted broken file: " + brokenFile);
                } catch (Throwable e) {
                    // stop clean on any error
                    LOGGER.error("Images check: ERROR, can't delete broken file: " + brokenFile.toAbsolutePath(), e);
                    break;
                }
            }
        }

        // broken archives must be deleted (fast check of the zip structure only, not the images data):
        // a download can't save anything to a broken archive, so a user re-downloads the whole set to a new file
        for (Path archive : archivesList) {
            try (ZipFile zip = new ZipFile(archive.toFile())) {
                zip.stream().count(); // read all entries info
            } catch (Throwable e) {
                LOGGER.error("Images check: found broken archive, deleting it (re-run images download to restore): " + archive + " - " + e.getMessage());
                try {
                    Files.delete(archive);
                    problems.add("deleted broken archive: " + archive + " - " + e.getMessage());
                } catch (Throwable e2) {
                    LOGGER.error("Images check: ERROR, can't delete broken archive: " + archive.toAbsolutePath(), e2);
                }
            }
        }

        return problems;
    }

    /**
     * Delete archives that can't be saved anymore (e.g. a broken zip file - any save will fail again and again,
     * so a user must re-download the whole set to a new file)
     *
     * @param e error from TVFS.umount
     * @return list of deleted archives
     */
    public static List<String> deleteBrokenArchives(FsSyncException e) {
        List<String> problems = new ArrayList<>();
        List<Throwable> errors = new ArrayList<>();
        errors.add(e);
        errors.addAll(Arrays.asList(e.getSuppressed()));
        for (Throwable error : errors) {
            if (!(error instanceof FsSyncException) || error instanceof FsSyncWarningException) {
                // warning is a success with some notes (e.g. still opened streams)
                continue;
            }

            // exception knows a mount point of the failed archive, but keep it in message only
            File archive;
            try {
                FsMountPoint mountPoint = FsMountPoint.create(URI.create(error.getMessage()));
                archive = new File(mountPoint.getPath().getUri());
            } catch (Exception e2) {
                LOGGER.error("Images: can't find broken archive by error message: " + error.getMessage(), e2);
                continue;
            }

            LOGGER.error("Images: can't save archive, deleting it (re-run images download to restore): " + archive + " - " + error.getCause());
            try {
                // reset archive's state (unsaved data will be lost anyway)
                TVFS.sync(new TFile(archive), FsSyncOptions.RESET);
            } catch (Throwable e2) {
                LOGGER.error("Images: can't reset broken archive: " + archive, e2);
            }
            if (archive.delete()) {
                problems.add("deleted broken archive: " + archive + " - " + error.getCause());
            } else {
                LOGGER.error("Images: ERROR, can't delete broken archive: " + archive);
            }
        }
        return problems;
    }
}
