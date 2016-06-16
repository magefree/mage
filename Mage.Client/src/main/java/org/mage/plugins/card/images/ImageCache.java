package org.mage.plugins.card.images;

import com.google.common.base.Function;
import com.google.common.collect.ComputationException;
import com.google.common.collect.MapMaker;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import mage.client.dialog.PreferencesDialog;
import mage.view.CardView;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TFileOutputStream;
import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.dl.sources.DirectLinksForDownload;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 * This class stores ALL card images in a cache with soft values. this means
 * that the images may be garbage collected when they are not needed any more,
 * but will be kept as long as possible.
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

    private static final Logger LOGGER = Logger.getLogger(ImageCache.class);

    private static final Map<String, BufferedImage> IMAGE_CACHE;

    /**
     * Common pattern for keys. Format: "<cardname>#<setname>#<collectorID>"
     */
    private static final Pattern KEY_PATTERN = Pattern.compile("(.*)#(.*)#(.*)#(.*)#(.*)");

    static {
        IMAGE_CACHE = new MapMaker().softValues().makeComputingMap(new Function<String, BufferedImage>() {
            @Override
            public BufferedImage apply(String key) {
                try {

                    boolean usesVariousArt = false;
                    if (key.endsWith("#usesVariousArt")) {
                        usesVariousArt = true;
                        key = key.replace("#usesVariousArt", "");
                    }
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
                        String tokenSetCode = m.group(5);

                        CardDownloadData info = new CardDownloadData(name, set, collectorId, usesVariousArt, type, tokenSetCode);

                        String path;
                        if (collectorId == 0) {
                            info.setToken(true);
                            path = CardImageUtils.generateTokenImagePath(info);
                            if (path == null) {
                                path = DirectLinksForDownload.outDir + File.separator + DirectLinksForDownload.cardbackFilename;
                            }
                        } else {
                            path = CardImageUtils.generateImagePath(info);
                        }

                        if (path == null) {
                            return null;
                        }
                        TFile file = getTFile(path);
                        if (file == null) {
                            return null;
                        }

                        if (thumbnail && path.endsWith(".jpg")) {
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
                                return thumbnailImage;
                            } else {
                                return makeThumbnailByFile(key, file, thumbnailPath);
                            }
                        } else {
                            return getWizardsCard(loadImage(file));
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
                if (image == null) {
                    return null;
                }
                LOGGER.debug("creating thumbnail for " + key);
                return makeThumbnail(image, thumbnailPath);
            }
        });
    }

    public static BufferedImage getMorphImage() {
        CardDownloadData info = new CardDownloadData("Morph", "KTK", 0, false, 0, "KTK");
        info.setToken(true);
        String path = CardImageUtils.generateTokenImagePath(info);
        if (path == null) {
            return null;
        }
        TFile file = getTFile(path);
        return loadImage(file);
    }

    public static BufferedImage getManifestImage() {
        CardDownloadData info = new CardDownloadData("Manifest", "FRF", 0, false, 0, "FRF");
        info.setToken(true);
        String path = CardImageUtils.generateTokenImagePath(info);
        if (path == null) {
            return null;
        }
        TFile file = getTFile(path);
        return loadImage(file);
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

    public static BufferedImage getThumbnail(CardView card) {
        return getImage(getKey(card, card.getName(), "#thumb"));
    }

    public static BufferedImage getImageOriginal(CardView card) {
        return getImage(getKey(card, card.getName(), ""));
    }

    public static BufferedImage getImageOriginalAlternateName(CardView card) {
        return getImage(getKey(card, card.getAlternateName(), ""));
    }

    /**
     * Returns the Image corresponding to the key
     */
    private static BufferedImage getImage(String key) {
        try {
            BufferedImage image = IMAGE_CACHE.get(key);
            return image;
        } catch (NullPointerException ex) {
            // unfortunately NullOutputException, thrown when apply() returns
            // null, is not public
            // NullOutputException is a subclass of NullPointerException
            // legitimate, happens when a card has no image
            return null;
        } catch (ComputationException ex) {
            if (ex.getCause() instanceof NullPointerException) {
                return null;
            }
            LOGGER.error(ex, ex);
            return null;
        }
    }

    /**
     * Returns the map key for a card, without any suffixes for the image size.
     */
    private static String getKey(CardView card, String name, String suffix) {
        return name + "#" + card.getExpansionSetCode() + "#" + card.getType() + "#" + card.getCardNumber() + "#"
                + (card.getTokenSetCode() == null ? "" : card.getTokenSetCode())
                + suffix
                + (card.getUsesVariousArt() ? "#usesVariousArt" : "");

    }

//    /**
//     * Returns the map key for the flip image of a card, without any suffixes for the image size.
//     */
//    private static String getKeyAlternateName(CardView card, String alternateName) {
//        return alternateName + "#" + card.getExpansionSetCode() + "#" +card.getType()+ "#" + card.getCardNumber() + "#"
//                + (card.getTokenSetCode() == null ? "":card.getTokenSetCode());
//    }
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
        BufferedImage image = null;
        if (!file.exists()) {
            LOGGER.debug("File does not exist: " + file.toString());
            return null;
        }
        try {
            try (TFileInputStream inputStream = new TFileInputStream(file)) {
                image = ImageIO.read(inputStream);
            }
        } catch (Exception e) {
            LOGGER.error(e, e);
        }

        return image;
    }

    public static BufferedImage makeThumbnail(BufferedImage original, String path) {
        BufferedImage image = getResizedImage(original, Constants.THUMBNAIL_SIZE_FULL);
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
            LOGGER.error(e, e);
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

        ResampleOp resampleOp = new ResampleOp(tgtWidth, tgtHeight);
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

    /**
     * Returns an image scaled to the size appropriate for the card picture
     * panel For future use.
     */
    private static BufferedImage getFullSizeImage(BufferedImage original, double scale) {
        if (scale == 1) {
            return original;
        }
        ResampleOp resampleOp = new ResampleOp((int) (original.getWidth() * scale), (int) (original.getHeight() * scale));
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

    /**
     * Returns an image scaled to the size appropriate for the card picture
     * panel
     *
     * @param original
     * @param sizeNeed
     * @return
     */
    public static BufferedImage getResizedImage(BufferedImage original, Rectangle sizeNeed) {
        ResampleOp resampleOp = new ResampleOp(sizeNeed.width, sizeNeed.height);
        BufferedImage image = resampleOp.filter(original, null);
        return image;
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
        if (scale > 1) {
            scale = 1;
        }

        return getFullSizeImage(original, scale);
    }

    public static TFile getTFile(String path) {
        try {
            TFile file = new TFile(path);
            return file;
        } catch (NullPointerException ex) {
            LOGGER.warn("Imagefile does not exist: " + path);
        }
        return null;
    }
}
