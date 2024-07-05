package org.mage.plugins.card.images;

import mage.abilities.icon.CardIconColor;
import mage.client.constants.Constants;
import mage.client.util.ImageCaches;
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
 * This class stores ALL card images in a cache with soft values. This means
 * that the images may be garbage collected when they are not needed any more,
 * but will be kept as long as possible.
 * <p>
 * It used to refresh themes at runtime too. Use GUISizeHelper.refreshGUIAndCards()
 *
 * @author JayDi85
 */
public final class ImageCache {

    private static final Logger LOGGER = Logger.getLogger(ImageCache.class);

    // global cache for both mtgo and image render modes
    private static final SoftValuesLoadingCache<String, ImageCacheData> SHARED_CARD_IMAGES_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(ImageCache::createCardOrTokenImage));
    private static final SoftValuesLoadingCache<String, ImageCacheData> SHARED_CARD_ICONS_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(ImageCache::createIcon));

    // format: name #setcode #imagenumber #cardnumber #size #usesVariousArt
    private static final Pattern CARD_IMAGE_KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)#(.*)#(.*)");

    // format: size #icon #color
    private static final Pattern CARD_ICON_KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)");

    private ImageCache() {
    }

    private static ImageCacheData createCardOrTokenImage(String key) {
        boolean usesVariousArt = false;
        if (key.matches(".*#usesVariousArt.*")) {
            usesVariousArt = true;
            key = key.replace("#usesVariousArt", "");
        }
        Matcher m = CARD_IMAGE_KEY_PATTERN.matcher(key);

        if (m.matches()) {
            String name = m.group(1);
            String setCode = m.group(2);
            Integer imageNumber = Integer.parseInt(m.group(3));
            String collectorId = m.group(4);
            if (collectorId.equals("null")) {
                collectorId = "0";
            }

            CardDownloadData info = new CardDownloadData(name, setCode, collectorId, usesVariousArt, imageNumber);

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
                // TODO: unused code?
                // TODO: return image from another set on empty image?
                if (tokenFile == null || !tokenFile.exists()) {
                    CardDownloadData tempInfo = new CardDownloadData(info);
                    tempInfo.setToken(false);
                    path = CardImageUtils.buildImagePathToCardOrToken(info);
                    tokenFile = getTFile(path);
                }

                // try unknown token image
                if (tokenFile == null || !tokenFile.exists()) {
                    // TODO: replace empty token by other default card, not cardback
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

            BufferedImage image = loadImage(file);
            image = getRoundCorner(image);
            return new ImageCacheData(path, image);
        } else {
            throw new IllegalArgumentException("Unknown card image's key format: " + key);
        }
    }

    private static ImageCacheData createIcon(String key) {
        Matcher m = CARD_ICON_KEY_PATTERN.matcher(key);
        if (m.matches()) {
            int cardSize = Integer.parseInt(m.group(1));
            String resourceName = m.group(2);
            CardIconColor cardIconColor = CardIconColor.valueOf(m.group(3));
            BufferedImage image = ImageManagerImpl.instance.getCardIcon(resourceName, cardSize, cardIconColor);
            return new ImageCacheData(resourceName, image);
        } else {
            throw new IllegalArgumentException("Unknown card icon's key format: " + key);
        }
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

    /**
     * Find image for current side
     */
    public static ImageCacheData getCardImageOriginal(CardView card) {
        return getCardImage(getKey(card, card.getName(), 0));
    }

    /**
     * Find image for other side
     */
    public static ImageCacheData getCardImageAlternate(CardView card) {
        return getCardImage(getKey(card, card.getAlternateName(), 0));
    }

    public static ImageCacheData getCardIconImage(String resourceName, int iconSize, String cardColorName) {
        return getCardIconImage(getCardIconKey(resourceName, iconSize, cardColorName));
    }

    private static ImageCacheData getCardImage(String key) {
        try {
            ImageCacheData data = SHARED_CARD_IMAGES_CACHE.getOrNull(key);
            return data != null ? data : new ImageCacheData("ERROR: key - " + key, null);
        } catch (Exception e) {
            if (e.getCause() instanceof NullPointerException) {
                // low memory error???
                return new ImageCacheData("ERROR: possible low memory", null);
            } else {
                // other error
                LOGGER.error("Error while loading card image: " + e, e);
                return new ImageCacheData("ERROR: see client logs for details", null);
            }
        }
    }

    private static ImageCacheData getCardIconImage(String key) {
        try {
            ImageCacheData data = SHARED_CARD_ICONS_CACHE.getOrNull(key);
            return data != null ? data : new ImageCacheData("ERROR: key - " + key, null);
        } catch (Exception e) {
            if (e.getCause() instanceof NullPointerException) {
                // low memory error???
                return new ImageCacheData("ERROR: possible low memory", null);
            } else {
                // other error
                LOGGER.error("Error while loading card icon: " + e, e);
                return new ImageCacheData("ERROR: see client logs for details", null);
            }
        }
    }

    /**
     * Returns the Image corresponding to the key only if it already exists in
     * the cache.
     */
    private static ImageCacheData tryGetImage(String key) {
        return SHARED_CARD_IMAGES_CACHE.peekIfPresent(key);
    }

    /**
     * Generate key for images cache (it must contain all info to search and load image from the disk)
     *
     * @param card
     * @param cardName  - can be alternative name
     * @param imageSize - size info, 0 to use original image (with max size)
     */
    private static String getKey(CardView card, String cardName, int imageSize) {
        String imageFileName = card.getImageFileName();
        if (imageFileName.isEmpty()) {
            imageFileName = cardName;
        }
        return imageFileName.replace(" Token", "")
                + '#' + card.getExpansionSetCode()
                + '#' + card.getImageNumber()
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
    public static ImageCacheData getCardImage(CardView card, int width, int height) {
        String key = getKey(card, card.getName(), width);
        ImageCacheData data = getCardImage(key);
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
