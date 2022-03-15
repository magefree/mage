package org.mage.plugins.card.images;

import com.google.common.base.Function;
import com.google.common.collect.ComputationException;
import mage.abilities.icon.CardIconColor;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.SoftValuesLoadingCache;
import mage.client.util.TransformedImageCache;
import mage.view.CardView;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TFileOutputStream;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.DirectLinksForDownload;
import org.mage.plugins.card.utils.CardImageUtils;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class stores ALL card images in a cache with soft values. this means
 * that the images may be garbage collected when they are not needed any more,
 * but will be kept as long as possible.
 * <p>
 * Key format: "[cardname]#[setname]#[type]#[collectorID]#[param]"
 * <p>
 * where param is:
 * <ul>
 * <li>size of image</li>
 *
 * <li>#Normal: request for unrotated image</li>
 * <li>#Tapped: request for rotated image</li>
 * <li>#Cropped: request for cropped image that is used for Shandalar like card
 * look</li>
 * </ul>
 */
public final class ImageCache {

    private static final Logger LOGGER = Logger.getLogger(ImageCache.class);

    private static final SoftValuesLoadingCache<String, BufferedImage> IMAGE_CACHE;
    private static final SoftValuesLoadingCache<String, BufferedImage> FACE_IMAGE_CACHE;
    private static final SoftValuesLoadingCache<String, BufferedImage> CARD_ICONS_CACHE;

    /**
     * Common pattern for keys. See ImageCache.getKey for structure info
     */
    private static final Pattern KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)#(.*)#(.*)#(.*)");
    private static final Pattern CARD_ICON_KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)");

    static {
        // softValues() = Specifies that each value (not key) stored in the map should be wrapped in a SoftReference
        // (by default, strong references are used). Softly-referenced objects will be garbage-collected in a
        // globally least-recently-used manner, in response to memory demand.
        IMAGE_CACHE = SoftValuesLoadingCache.from(new Function<String, BufferedImage>() {
            @Override
            public BufferedImage apply(String key) {
                try {
                    boolean usesVariousArt = false;
                    if (key.matches(".*#usesVariousArt.*")) {
                        usesVariousArt = true;
                        key = key.replace("#usesVariousArt", "");
                    }
                    boolean thumbnail = false;
                    if (key.matches(".*#thumb.*")) {
                        thumbnail = true;
                        key = key.replace("#thumb", "");
                    }
                    Matcher m = KEY_PATTERN.matcher(key);

                    if (m.matches()) {
                        String name = m.group(1);
                        String set = m.group(2);
                        Integer type = Integer.parseInt(m.group(3));
                        String collectorId = m.group(4);
                        if (collectorId.equals("null")) {
                            collectorId = "0";
                        }
                        String tokenSetCode = m.group(5);
                        String tokenDescriptor = m.group(6);

                        CardDownloadData info = new CardDownloadData(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor);

                        boolean cardback = false;
                        String path;
                        if (collectorId.isEmpty() || "0".equals(collectorId) || !tokenDescriptor.isEmpty()) { // tokenDescriptor for embalm ability
                            info.setToken(true);
                            path = CardImageUtils.generateTokenImagePath(info);
                            if (path == null) {
                                cardback = true;
                                // try token image from card
                                CardDownloadData newInfo = new CardDownloadData(info);
                                newInfo.setToken(false);
                                path = CardImageUtils.buildImagePathToCard(newInfo);
                                TFile tokenFile = getTFile(path);
                                if (tokenFile == null || !tokenFile.exists()) {
                                    // token empty token image
                                    // TODO: replace empty token by other default card, not cardback
                                    path = CardImageUtils.buildImagePathToDefault(DirectLinksForDownload.cardbackFilename);
                                }
                            }
                        } else {
                            path = CardImageUtils.buildImagePathToCard(info);
                        }

                        if (path == null) {
                            return null;
                        }
                        TFile file = getTFile(path);
                        if (file == null) {
                            return null;
                        }

                        if (thumbnail && path.endsWith(".jpg")) {
                            // need thumbnail image
                            String thumbnailPath = buildThumbnailPath(path);
                            TFile thumbnailFile = null;
                            try {
                                thumbnailFile = new TFile(thumbnailPath);
                            } catch (Exception ex) {
                            }
                            boolean exists = false;
                            if (thumbnailFile != null) {
                                try {
                                    exists = thumbnailFile.exists();
                                } catch (Exception ex) {
                                    exists = false;
                                }
                            }
                            if (exists) {
                                LOGGER.debug("loading thumbnail for " + key + ", path=" + thumbnailPath);
                                BufferedImage thumbnailImage = loadImage(thumbnailFile);
                                if (thumbnailImage == null) { // thumbnail exists but broken for some reason
                                    LOGGER.warn("failed loading thumbnail for " + key + ", path=" + thumbnailPath
                                            + ", thumbnail file is probably broken, attempting to recreate it...");
                                    thumbnailImage = makeThumbnailByFile(key, file, thumbnailPath);
                                }

                                if (cardback) {
                                    // unknown tokens on opponent desk
                                    thumbnailImage = getRoundCorner(thumbnailImage);
                                }

                                return thumbnailImage;
                            } else {
                                return makeThumbnailByFile(key, file, thumbnailPath);
                            }
                        } else {
                            if (cardback) {
                                // need cardback image
                                BufferedImage image = loadImage(file);
                                image = getRoundCorner(image);
                                return image;
                            } else {
                                // need normal card image
                                BufferedImage image = loadImage(file);
                                image = getWizardsCard(image);
                                image = getRoundCorner(image);
                                return image;
                            }
                        }
                    } else {
                        throw new RuntimeException(
                                "Requested image doesn't fit the requirement for key (<cardname>#<setname>#<collectorID>): " + key);
                    }
                } catch (Exception ex) {
                    if (ex instanceof ComputationException) {
                        throw (ComputationException) ex;
                    } else {
                        throw new ComputationException(ex);
                    }
                }
            }

            public BufferedImage makeThumbnailByFile(String key, TFile file, String thumbnailPath) {
                BufferedImage image = loadImage(file);
                image = getWizardsCard(image);
                image = getRoundCorner(image);
                if (image == null) {
                    return null;
                }
                LOGGER.debug("creating thumbnail for " + key);
                return makeThumbnail(image, thumbnailPath);
            }
        });

        FACE_IMAGE_CACHE = SoftValuesLoadingCache.from(key -> {
            try {
                Matcher m = KEY_PATTERN.matcher(key);

                if (m.matches()) {
                    String name = m.group(1);
                    String set = m.group(2);
                    //Integer artid = Integer.parseInt(m.group(2));

                    String path;
                    path = CardImageUtils.generateFaceImagePath(name, set);

                    if (path == null) {
                        return null;
                    }
                    TFile file = getTFile(path);
                    if (file == null) {
                        return null;
                    }

                    BufferedImage image = loadImage(file);
                    return image;
                } else {
                    throw new RuntimeException(
                            "Requested face image doesn't fit the requirement for key (<cardname>#<artid>#: " + key);
                }
            } catch (Exception ex) {
                if (ex instanceof ComputationException) {
                    throw (ComputationException) ex;
                } else {
                    throw new ComputationException(ex);
                }
            }
        });

        CARD_ICONS_CACHE = SoftValuesLoadingCache.from(key -> {
            try {
                Matcher m = CARD_ICON_KEY_PATTERN.matcher(key);

                if (m.matches()) {
                    int cardSize = Integer.parseInt(m.group(1));
                    String resourceName = m.group(2);
                    CardIconColor cardIconColor = CardIconColor.valueOf(m.group(3));
                    BufferedImage image = ImageManagerImpl.instance.getCardIcon(resourceName, cardSize, cardIconColor);
                    return image;
                } else {
                    throw new RuntimeException("Wrong card icons image key format: " + key);
                }
            } catch (Exception ex) {
                if (ex instanceof ComputationException) {
                    throw (ComputationException) ex;
                } else {
                    throw new ComputationException(ex);
                }
            }
        });
    }

    public static void clearCache() {
        IMAGE_CACHE.invalidateAll();
        FACE_IMAGE_CACHE.invalidateAll();
        CARD_ICONS_CACHE.invalidateAll();
    }

    public static String getFilePath(CardView card, int width) {
        String key = getKey(card, card.getName(), Integer.toString(width));
        boolean usesVariousArt = false;
        if (key.matches(".*#usesVariousArt.*")) {
            usesVariousArt = true;
            key = key.replace("#usesVariousArt", "");
        }
        boolean thumbnail = false;
        if (key.matches(".*#thumb.*")) {
            thumbnail = true;
            key = key.replace("#thumb", "");
        }
        Matcher m = KEY_PATTERN.matcher(key);

        if (m.matches()) {
            String name = m.group(1);
            String set = m.group(2);
            Integer type = Integer.parseInt(m.group(3));
            String collectorId = m.group(4);
            if (collectorId.equals("null")) {
                collectorId = "0";
            }
            String tokenSetCode = m.group(5);
            String tokenDescriptor = m.group(6);

            CardDownloadData info = new CardDownloadData(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor);

            String path;
            if (collectorId.isEmpty() || "0".equals(collectorId) || !tokenDescriptor.isEmpty()) { // tokenDescriptor for embalm ability
                info.setToken(true);
                path = CardImageUtils.generateTokenImagePath(info);
                if (path == null) {
                    // try token image from card
                    CardDownloadData newInfo = new CardDownloadData(info);
                    newInfo.setToken(false);
                    path = CardImageUtils.buildImagePathToCard(newInfo);
                    TFile tokenFile = getTFile(path);
                    if (tokenFile == null || !tokenFile.exists()) {
                        // token empty token image
                        // TODO: replace empty token by other default card, not cardback
                        path = CardImageUtils.buildImagePathToDefault(DirectLinksForDownload.cardbackFilename);
                    }
                }
            } else {
                path = CardImageUtils.buildImagePathToCard(info);
            }

            if (thumbnail && path.endsWith(".jpg")) {
                return buildThumbnailPath(path);
            }
            return path;
        }

        return "";
    }

    private ImageCache() {
    }

    public static BufferedImage getCardbackImage() {
        BufferedImage image = ImageCache.loadImage(new TFile(CardImageUtils.buildImagePathToDefault(DirectLinksForDownload.cardbackFilename)));
        image = getRoundCorner(image);
        return image;
    }

    public static BufferedImage getMorphImage() {
        // TODO: replace by morth image
        CardDownloadData info = new CardDownloadData("Morph", "KTK", "0", false, 0, "KTK", "");
        info.setToken(true);
        String path = CardImageUtils.generateTokenImagePath(info);
        if (path == null) {
            return null;
        }
        TFile file = getTFile(path);

        BufferedImage image = loadImage(file);
        image = getRoundCorner(image);
        return image;
    }

    public static BufferedImage getManifestImage() {
        // TODO: replace by manifestest image
        CardDownloadData info = new CardDownloadData("Manifest", "FRF", "0", false, 0, "FRF", "");
        info.setToken(true);
        String path = CardImageUtils.generateTokenImagePath(info);
        if (path == null) {
            return null;
        }
        TFile file = getTFile(path);

        BufferedImage image = loadImage(file);
        image = getRoundCorner(image);
        return image;
    }

    private static String buildThumbnailPath(String path) {
        String thumbnailPath;
        if (PreferencesDialog.isSaveImagesToZip()) {
            thumbnailPath = path.replace(".zip", ".thumb.zip");
        } else {
            thumbnailPath = path.replace(".jpg", ".thumb.jpg");
        }
        return thumbnailPath;
    }

    public static BufferedImage getRoundCorner(BufferedImage image) {
        if (image != null) {
            BufferedImage cornerImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // corner
            float ROUNDED_CORNER_SIZE = 0.11f; // see CardPanelRenderModeImage
            int cornerSizeBorder = Math.max(4, Math.round(image.getWidth() * ROUNDED_CORNER_SIZE));

            // corner mask
            Graphics2D gg = cornerImage.createGraphics();
            gg.setComposite(AlphaComposite.Src);
            gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gg.setColor(Color.white);
            gg.fill(new RoundRectangle2D.Float(0, 0, cornerImage.getWidth(), cornerImage.getHeight(), cornerSizeBorder, cornerSizeBorder));

            // image draw to buffer
            gg.setComposite(AlphaComposite.SrcAtop);
            gg.drawImage(image, 0, 0, null);
            gg.dispose();

            return cornerImage;
        } else {
            return image;
        }
    }

    public static BufferedImage getWizardsCard(BufferedImage image) {
        if (image != null && image.getWidth() == 265 && image.getHeight() == 370) {
            BufferedImage crop = new BufferedImage(256, 360, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = crop.createGraphics();
            graphics2D.drawImage(image, 0, 0, 255, 360, 5, 5, 261, 365, null);
            graphics2D.dispose();
            return crop;
        } else {
            return image;
        }
    }

    public static boolean isFaceImagePresent(CardView card) {
        String path;
        path = CardImageUtils.generateFaceImagePath(card.getName(), card.getExpansionSetCode());

        if (path == null) {
            return false;
        }
        TFile file = getTFile(path);
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    public static BufferedImage getThumbnail(CardView card) {
        return getImage(getKey(card, card.getName(), "#thumb"));
    }

    public static BufferedImage tryGetThumbnail(CardView card) {
        return tryGetImage(getKey(card, card.getName(), "#thumb"));
    }

    public static BufferedImage getImageOriginal(CardView card) {
        return getImage(getKey(card, card.getName(), ""));
    }

    public static BufferedImage getImageOriginalAlternateName(CardView card) {
        return getImage(getKey(card, card.getAlternateName(), ""));
    }

    public static BufferedImage getCardIconImage(String resourceName, int iconSize, String cardColorName) {
        return getCardIconImage(getCardIconKey(resourceName, iconSize, cardColorName));
    }

    /**
     * Returns the Image corresponding to the key
     */
    private static BufferedImage getImage(String key) {
        try {
            return IMAGE_CACHE.getOrNull(key);
        } catch (ComputationException ex) {
            // too low memory
            if (ex.getCause() instanceof NullPointerException) {
                return null;
            }
            LOGGER.error(ex, ex);
            return null;
        }
    }

    /**
     * Returns the Image corresponding to the key
     */
    private static BufferedImage getFaceImage(String key) {
        try {
            return FACE_IMAGE_CACHE.getOrNull(key);
        } catch (ComputationException ex) {
            if (ex.getCause() instanceof NullPointerException) {
                return null;
            }
            LOGGER.error(ex, ex);
            return null;
        }
    }

    private static BufferedImage getCardIconImage(String key) {
        try {
            return CARD_ICONS_CACHE.getOrNull(key);
        } catch (ComputationException ex) {
            if (ex.getCause() instanceof NullPointerException) {
                return null;
            }
            LOGGER.error(ex, ex);
            return null;
        }
    }

    /**
     * Returns the Image corresponding to the key only if it already exists in
     * the cache.
     */
    private static BufferedImage tryGetImage(String key) {
        return IMAGE_CACHE.peekIfPresent(key);
    }

    /**
     * Returns the map key for a card, without any suffixes for the image size.
     */
    private static String getKey(CardView card, String name, String suffix) {
        return (card.isToken() ? name.replace(" Token", "") : name)
                + '#' + card.getExpansionSetCode()
                + '#' + card.getType()
                + '#' + card.getCardNumber()
                + '#' + (card.getTokenSetCode() == null ? "" : card.getTokenSetCode())
                + suffix
                + (card.getUsesVariousArt() ? "#usesVariousArt" : "")
                + '#' + (card.getTokenDescriptor() != null ? card.getTokenDescriptor() : "");
    }

    /**
     * Returns the map key for a card, without any suffixes for the image size.
     */
    private static String getFaceKey(CardView card, String name, String set) {
        return name + '#' + set + "####";
    }

    private static String getCardIconKey(String resourceName, int size, String cardColorName) {
        return size + "#" + resourceName + "#" + cardColorName;
    }

    /**
     * Load image from file
     *
     * @param file file to load image from
     * @return {@link BufferedImage}
     */
    public static BufferedImage loadImage(TFile file) {
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            LOGGER.debug("File does not exist: " + file.toString());
            return null;
        }
        BufferedImage image = null;
        try {
            try (TFileInputStream inputStream = new TFileInputStream(file)) {
                image = ImageIO.read(inputStream);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return image;
    }

    public static BufferedImage makeThumbnail(BufferedImage original, String path) {
        BufferedImage image = TransformedImageCache.getResizedImage(original, Constants.THUMBNAIL_SIZE_FULL.width, Constants.THUMBNAIL_SIZE_FULL.height);
        TFile imageFile = getTFile(path);
        if (imageFile == null) {
            return null;
        }
        try {
            try (TFileOutputStream outputStream = new TFileOutputStream(imageFile)) {
                String format = image.getColorModel().getNumComponents() > 3 ? "png" : "jpg";
                ImageIO.write(image, format, outputStream);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            imageFile.delete();
        }
        return image;
    }

    /**
     * Returns an image scaled to the size given
     *
     * @param original
     * @return
     */
    public static BufferedImage getNormalSizeImage(BufferedImage original) {
        if (original == null) {
            return null;
        }

        int srcWidth = original.getWidth();
        int srcHeight = original.getHeight();

        int tgtWidth = Constants.CARD_SIZE_FULL.width;
        int tgtHeight = Constants.CARD_SIZE_FULL.height;

        if (srcWidth == tgtWidth && srcHeight == tgtHeight) {
            return original;
        }

        return TransformedImageCache.getResizedImage(original, tgtWidth, tgtHeight);
    }

    /**
     * Returns the image appropriate to display the card in the picture panel
     *
     * @param card
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage getImage(CardView card, int width, int height) {
        if (Constants.THUMBNAIL_SIZE_FULL.width + 10 > width) {
            return getThumbnail(card);
        }
        String key = getKey(card, card.getName(), Integer.toString(width));
        BufferedImage original = getImage(key);
        if (original == null) {
            LOGGER.debug(key + " not found");
            return null;
        }

        double scale = Math.min((double) width / original.getWidth(), (double) height / original.getHeight());
        if (scale >= 1) {
            return original;
        }

        return TransformedImageCache.getResizedImage(original, (int) (original.getWidth() * scale), (int) (original.getHeight() * scale));
    }

    /**
     * Returns the image appropriate to display the card in the picture panel
     *
     * @param card
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage getFaceImage(CardView card, int width, int height) {
        String key = getFaceKey(card, card.getName(), card.getExpansionSetCode());
        BufferedImage original = getFaceImage(key);
        if (original == null) {
            LOGGER.debug(key + " (faceimage) not found");
            return null;
        }

        return original;
    }

    /**
     * Returns the image appropriate to display for a card in a picture panel,
     * but only it was ALREADY LOADED. That is, the call is immediate and will
     * not block on file IO.
     *
     * @param card
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage tryGetImage(CardView card, int width, int height) {
        if (Constants.THUMBNAIL_SIZE_FULL.width + 10 > width) {
            return tryGetThumbnail(card);
        }
        String key = getKey(card, card.getName(), Integer.toString(width));
        BufferedImage original = tryGetImage(key);
        if (original == null) {
            LOGGER.debug(key + " not found");
            return null;
        }

        double scale = Math.min((double) width / original.getWidth(), (double) height / original.getHeight());
        if (scale >= 1) {
            return original;
        }

        return TransformedImageCache.getResizedImage(original, (int) (original.getWidth() * scale), (int) (original.getHeight() * scale));
    }

    public static TFile getTFile(String path) {
        try {
            return new TFile(path);
        } catch (NullPointerException ex) {
            LOGGER.warn("Imagefile does not exist: " + path);
        }
        return null;
    }
}
