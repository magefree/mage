package org.mage.plugins.card.images;

import com.google.common.base.Function;
import com.google.common.collect.ComputationException;
import com.google.common.collect.MapMaker;
import com.mortennobel.imagescaling.ResampleOp;
import mage.view.CardView;
import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class stores ALL card images in a cache with soft values. this means
 * that the images may be garbage collected when they are not needed any more, but will
 * be kept as long as possible.
 * 
 * Key format: "<cardname>#<setname>#<type>#<collectorID>#<param>"
 * 
 * where param is:
 * 
 * <ul>
 * <li>#Normal: request for unrotated image</li>
 * <li>#Tapped: request for rotated image</li>
 * <li>#Cropped: request for cropped image that is used for Shandalar like card
 * look</li>
 * </ul>
 */
public class ImageCache {

    private static final Logger log = Logger.getLogger(ImageCache.class);

    private static final Map<String, BufferedImage> imageCache;

    /**
     * Common pattern for keys.
     * Format: "<cardname>#<setname>#<collectorID>"
     */
    private static final Pattern KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)#(.*)");

    static {
        imageCache = new MapMaker().softValues().makeComputingMap(new Function<String, BufferedImage>() {
            @Override
            public BufferedImage apply(String key) {
                try {
                    boolean thumbnail = false;
                    if (key.endsWith("#thumb")) {
                        thumbnail = true;
                        key = key.replace("#thumb", "");
                    }
                    Matcher m = KEY_PATTERN.matcher(key);

                    if (m.matches()) {
                        String name = m.group(1);
                        String set = m.group(2);
                        Integer type = Integer.parseInt(m.group(3));
                        Integer collectorId = Integer.parseInt(m.group(4));

                        CardInfo info = new CardInfo(name, set, collectorId, type);

                        if (collectorId == 0) info.setToken(true);
                        String path = CardImageUtils.getImagePath(info);
                        if (path == null) return null;
                        File file = new File(path);

                        if (thumbnail && path.endsWith(".jpg")) {
                            String thumbnailPath = path.replace(".jpg", ".thumb.jpg");
                            File thumbnailFile = new File(thumbnailPath);
                            if (thumbnailFile.exists()) {
                                //log.debug("loading thumbnail for " + key + ", path="+thumbnailPath);
                                return loadImage(thumbnailFile);
                            } else {
                                BufferedImage image = loadImage(file);
                                image = getWizardsCard(image);
                                if (image == null) return null;
                                //log.debug("creating thumbnail for " + key);
                                return makeThumbnail(image, thumbnailPath);
                            }
                        } else {
                            return getWizardsCard(loadImage(file));
                        }
                    } else {
                        throw new RuntimeException(
                                "Requested image doesn't fit the requirement for key (<cardname>#<setname>#<collectorID>): " + key);
                    }
                } catch (Exception ex) {
                    if (ex instanceof ComputationException)
                        throw (ComputationException) ex;
                    else
                        throw new ComputationException(ex);
                }
            }
        });
    }

    public static BufferedImage getWizardsCard(BufferedImage image) {
        if (image.getWidth() == 265 && image.getHeight() == 370) {
            BufferedImage crop = new BufferedImage(256, 360, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = crop.createGraphics();
            graphics2D.drawImage(image, 0, 0, 255, 360, 5, 5, 261, 365, null);
            graphics2D.dispose();
            return crop;
        } else {
            return image;
        }
    }

    public static BufferedImage getThumbnail(CardView card) {
        String key = getKey(card) + "#thumb";
        //log.debug("#key: " + key);
        return getImage(key);
    }

    public static BufferedImage getImageOriginal(CardView card) {
        String key = getKey(card);
        //log.debug("#key: " + key);
        return getImage(key);
    }

    /**
     * Returns the Image corresponding to the key
     */
    private static BufferedImage getImage(String key) {
        try {
            BufferedImage image = imageCache.get(key);
            return image;
        } catch (NullPointerException ex) {
            // unfortunately NullOutputException, thrown when apply() returns
            // null, is not public
            // NullOutputException is a subclass of NullPointerException
            // legitimate, happens when a card has no image
            return null;
        } catch (ComputationException ex) {
            if (ex.getCause() instanceof NullPointerException)
                return null;
            log.error(ex,ex);
            return null;
        }
    }

    /**
     * Returns the map key for a card, without any suffixes for the image size.
     */
    private static String getKey(CardView card) {
        String set = card.getExpansionSetCode();
        int type = card.getType();
        String key = card.getName() + "#" + set + "#" + type + "#" + String.valueOf(card.getCardNumber());

        return key;
    }

    /**
     * Load image from file
     * 
     * @param file
     *            file to load image from
     * @return {@link BufferedImage}
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage image = null;
        if (!file.exists()) {
            return null;
        }
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            log.error(e, e);
        }

        return image;
    }

    public static BufferedImage makeThumbnail(BufferedImage original, String path) {
        BufferedImage image = getResizedImage(original, Constants.THUMBNAIL_SIZE_FULL);
        File imagePath = new File(path);
        try {
            //log.debug("thumbnail path:"+path);
            ImageIO.write(image, "jpg", imagePath);
        } catch (Exception e) {
            log.error(e,e);
        }
        return image;
    }

    /**
     * Returns an image scaled to the size given
     */
    public static BufferedImage getNormalSizeImage(BufferedImage original) {
        if (original == null) {
            return null;
        }

        int srcWidth = original.getWidth();
        int srcHeight = original.getHeight();

        int tgtWidth = Constants.CARD_SIZE_FULL.width;
        int tgtHeight = Constants.CARD_SIZE_FULL.height;

        if (srcWidth == tgtWidth && srcHeight == tgtHeight)
            return original;

        ResampleOp resampleOp = new ResampleOp(tgtWidth, tgtHeight);
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

    /**
     * Returns an image scaled to the size appropriate for the card picture
     * panel For future use.
     */
    private static BufferedImage getFullSizeImage(BufferedImage original, double scale) {
        if (scale == 1)
            return original;
        ResampleOp resampleOp = new ResampleOp((int) (original.getWidth() * scale), (int) (original.getHeight() * scale));
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

    /**
     * Returns an image scaled to the size appropriate for the card picture
     * panel
     */
    public static BufferedImage getResizedImage(BufferedImage original, Rectangle sizeNeed) {
        ResampleOp resampleOp = new ResampleOp(sizeNeed.width, sizeNeed.height);
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

    /**
     * Returns the image appropriate to display the card in the picture panel
     */
    public static BufferedImage getImage(CardView card, int width, int height) {
        String key = getKey(card);
        BufferedImage original = getImage(key);
        if (original == null)
            return null;

        double scale = Math.min((double) width / original.getWidth(), (double) height / original.getHeight());
        if (scale > 1)
            scale = 1;

        return getFullSizeImage(original, scale);
    }
}
