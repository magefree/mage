package org.mage.plugins.card.images;

import com.google.common.collect.ComputationException;
import mage.abilities.icon.CardIconColor;
import mage.client.constants.Constants;
import mage.client.util.SoftValuesLoadingCache;
import mage.client.util.TransformedImageCache;
import mage.view.CardView;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.DirectLinksForDownload;
import org.mage.plugins.card.utils.CardImageUtils;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class stores ALL card images in a cache with soft values. this means
 * that the images may be garbage collected when they are not needed any more,
 * but will be kept as long as possible.
 * <p>
 * Key format: "[cardname]#[setname]#[type]#[collectorID]#[image size]#[additional data]"
 *
 * <li>#Normal: request for unrotated image</li>
 * <li>#Tapped: request for rotated image</li>
 * <li>#Cropped: request for cropped image that is used for Shandalar like card
 * look</li>
 * </ul>
 *
 * @author JayDi85
 */
public final class ImageCache {

    private static final Logger LOGGER = Logger.getLogger(ImageCache.class);

    private static final SoftValuesLoadingCache<String, ImageCacheData> IMAGE_CACHE; // cards and tokens
    private static final SoftValuesLoadingCache<String, ImageCacheData> FACE_IMAGE_CACHE;
    private static final SoftValuesLoadingCache<String, ImageCacheData> CARD_ICONS_CACHE;

    /**
     * Common pattern for keys. See ImageCache.getKey for structure info
     */
    private static final Pattern KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)#(.*)#(.*)");
    private static final Pattern CARD_ICON_KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)");

    static {
        // softValues() = Specifies that each value (not key) stored in the map should be wrapped in a SoftReference
        // (by default, strong references are used). Softly-referenced objects will be garbage-collected in a
        // globally least-recently-used manner, in response to memory demand.
        IMAGE_CACHE = SoftValuesLoadingCache.from(key -> {
            try {
                boolean usesVariousArt = false;
                if (key.matches(".*#usesVariousArt.*")) {
                    usesVariousArt = true;
                    key = key.replace("#usesVariousArt", "");
                }
                Matcher m = KEY_PATTERN.matcher(key);

                if (m.matches()) {
                    String name = m.group(1);
                    String setCode = m.group(2);
                    Integer type = Integer.parseInt(m.group(3));
                    String collectorId = m.group(4);
                    if (collectorId.equals("null")) {
                        collectorId = "0";
                    }

                    CardDownloadData info = new CardDownloadData(name, setCode, collectorId, usesVariousArt, type);

                    boolean cardback = false;
                    String path;
                    if (collectorId.isEmpty() || "0".equals(collectorId)) {
                        // TOKEN
                        // can be a normal token or a token from card (see embalm ability)
                        info.setToken(true);
                        TFile tokenFile;

                        // try normal token
                        path = CardImageUtils.buildImagePathToCardOrToken(info);
                        tokenFile = getTFile(path);

                        // try token from card
                        if (tokenFile == null || !tokenFile.exists()) {
                            CardDownloadData tempInfo = new CardDownloadData(info);
                            tempInfo.setToken(false);
                            path = CardImageUtils.buildImagePathToCardOrToken(info);
                            tokenFile = getTFile(path);
                        }

                        // try unknown token image
                        if (tokenFile == null || !tokenFile.exists()) {
                            // TODO: replace empty token by other default card, not cardback
                            cardback = true;
                            path = CardImageUtils.buildImagePathToDefault(DirectLinksForDownload.cardbackFilename);
                        }
                    } else {
                        // CARD
                        path = CardImageUtils.buildImagePathToCardOrToken(info);
                    }

                    TFile file = getTFile(path);
                    if (file == null) {
                        return new ImageCacheData(path, null);
                    }

                    if (cardback) {
                        // TODO: is there any different in images styles? Cardback must be from scryfall, not wizards
                        // need cardback image
                        BufferedImage image = loadImage(file);
                        image = getRoundCorner(image);
                        return new ImageCacheData(path, image);
                    } else {
                        // need normal card image
                        BufferedImage image = loadImage(file);
                        image = getWizardsCard(image);
                        image = getRoundCorner(image);
                        return new ImageCacheData(path, image);
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
        });

        FACE_IMAGE_CACHE = SoftValuesLoadingCache.from(key -> {
            try {
                Matcher m = KEY_PATTERN.matcher(key);

                if (m.matches()) {
                    String name = m.group(1);
                    String setCode = m.group(2);
                    // skip type
                    // skip collectorId

                    String path = CardImageUtils.generateFaceImagePath(name, setCode);
                    TFile file = getTFile(path);
                    if (file == null) {
                        return new ImageCacheData(path, null);
                    }

                    BufferedImage image = loadImage(file);
                    return new ImageCacheData(path, image);
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
                    return new ImageCacheData(resourceName, image);
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

    private ImageCache() {
    }

    public static ImageCacheData getCardbackImage() {
        String path = CardImageUtils.buildImagePathToDefault(DirectLinksForDownload.cardbackFilename);
        BufferedImage image = ImageCache.loadImage(getTFile(path));
        image = getRoundCorner(image);
        return new ImageCacheData(path, image);
    }

    public static ImageCacheData getMorphImage() {
        // TODO: replace by downloadable morth image
        CardDownloadData info = new CardDownloadData("Morph", "KTK", "0", false, 0);
        info.setToken(true);
        String path = CardImageUtils.buildImagePathToCardOrToken(info);

        TFile file = getTFile(path);
        BufferedImage image = loadImage(file);
        image = getRoundCorner(image);
        return new ImageCacheData(path, image);
    }

    public static ImageCacheData getManifestImage() {
        // TODO: replace by downloadable manifestest image
        CardDownloadData info = new CardDownloadData("Manifest", "FRF", "0", false, 0);
        info.setToken(true);
        String path = CardImageUtils.buildImagePathToCardOrToken(info);

        TFile file = getTFile(path);
        BufferedImage image = loadImage(file);
        image = getRoundCorner(image);
        return new ImageCacheData(path, image);
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
        // TODO: can be removed?
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

    public static ImageCacheData getImageOriginal(CardView card) {
        return getImage(getKey(card, card.getName(), 0));
    }

    public static ImageCacheData getImageOriginalAlternateName(CardView card) {
        return getImage(getKey(card, card.getAlternateName(), 0));
    }

    public static ImageCacheData getCardIconImage(String resourceName, int iconSize, String cardColorName) {
        return getCardIconImage(getCardIconKey(resourceName, iconSize, cardColorName));
    }

    /**
     * Returns the Image corresponding to the key
     */
    private static ImageCacheData getImage(String key) {
        try {
            ImageCacheData data = IMAGE_CACHE.getOrNull(key);
            return data != null ? data : new ImageCacheData("ERROR: key - " + key, null);
        } catch (ComputationException ex) {
            // too low memory
            if (ex.getCause() instanceof NullPointerException) {
                return new ImageCacheData("ERROR: low memory?", null);
            }
            LOGGER.error(ex, ex);
            return new ImageCacheData("ERROR: see logs", null);
        }
    }

    /**
     * Returns the Image corresponding to the key
     */
    private static ImageCacheData getFaceImage(String key) {
        try {
            ImageCacheData data = FACE_IMAGE_CACHE.getOrNull(key);
            return data != null ? data : new ImageCacheData("ERROR: key " + key, null);
        } catch (ComputationException ex) {
            if (ex.getCause() instanceof NullPointerException) {
                return new ImageCacheData("ERROR: low memory?", null);
            }
            LOGGER.error(ex, ex);
            return new ImageCacheData("ERROR: see logs", null);
        }
    }

    private static ImageCacheData getCardIconImage(String key) {
        try {
            ImageCacheData data = CARD_ICONS_CACHE.getOrNull(key);
            return data != null ? data : new ImageCacheData("ERROR: key - " + key, null);
        } catch (ComputationException ex) {
            if (ex.getCause() instanceof NullPointerException) {
                return new ImageCacheData("ERROR: low memory?", null);
            }
            LOGGER.error(ex, ex);
            return new ImageCacheData("ERROR: see logs", null);
        }
    }

    /**
     * Returns the Image corresponding to the key only if it already exists in
     * the cache.
     */
    private static ImageCacheData tryGetImage(String key) {
        return IMAGE_CACHE.peekIfPresent(key);
    }

    /**
     * Generate key for images cache (it must contain all info to search and load image from the disk)
     *
     * @param card
     * @param cardName  - can be alternative name
     * @param imageSize - size info, 0 to use original image (with max size)
     */
    private static String getKey(CardView card, String cardName, int imageSize) {
        return (card.isToken() ? cardName.replace(" Token", "") : cardName)
                + '#' + card.getExpansionSetCode()
                + '#' + card.getType()
                + '#' + card.getCardNumber()
                + '#' + imageSize
                + (card.getUsesVariousArt() ? "#usesVariousArt" : "");
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
    public static ImageCacheData getImage(CardView card, int width, int height) {
        String key = getKey(card, card.getName(), width);
        ImageCacheData data = getImage(key);
        if (data.getImage() == null) {
            LOGGER.debug("Image doesn't exists in the cache: " + key);
            return data;
        }

        double scale = Math.min((double) width / data.getImage().getWidth(), (double) height / data.getImage().getHeight());
        if (scale >= 1) {
            return data;
        }

        BufferedImage newImage = TransformedImageCache.getResizedImage(data.getImage(), (int) (data.getImage().getWidth() * scale), (int) (data.getImage().getHeight() * scale));
        data.setImage(newImage);
        return data;
    }

    /**
     * Returns the image appropriate to display the card in the picture panel
     *
     * @param card
     * @param width
     * @param height
     * @return
     */
    public static ImageCacheData getFaceImage(CardView card, int width, int height) {
        String key = getFaceKey(card, card.getName(), card.getExpansionSetCode());
        ImageCacheData data = getFaceImage(key);
        if (data.getImage() == null) {
            LOGGER.debug(key + " (faceimage) not found");
        }
        return data;
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
    public static ImageCacheData tryGetImage(CardView card, int width, int height) {
        String key = getKey(card, card.getName(), width);
        ImageCacheData data = tryGetImage(key);
        if (data.getImage() == null) {
            LOGGER.debug(key + " not found");
            return data;
        }

        double scale = Math.min((double) width / data.getImage().getWidth(), (double) height / data.getImage().getHeight());
        if (scale >= 1) {
            return data;
        }

        BufferedImage newImage = TransformedImageCache.getResizedImage(data.getImage(), (int) (data.getImage().getWidth() * scale), (int) (data.getImage().getHeight() * scale));
        data.setImage(newImage);
        return data;
    }

    public static TFile getTFile(String path) {
        try {
            if (path != null) {
                return new TFile(path);
            }
        } catch (NullPointerException ex) {
            // TODO: raise error on path == null -- is it actual?!
            LOGGER.warn("Imagefile does not exist: " + path);
        }
        return null;
    }
}
