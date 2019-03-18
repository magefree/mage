
package mage.client.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import static mage.client.constants.Constants.FRAME_MAX_HEIGHT;
import static mage.client.constants.Constants.FRAME_MAX_WIDTH;
import static mage.client.constants.Constants.SYMBOL_MAX_SPACE;
import mage.view.CardView;
import org.mage.card.arcane.UI;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ImageHelper {

    protected static final HashMap<String, BufferedImage> images = new HashMap<>();
    protected static final HashMap<String, BufferedImage> backgrounds = new HashMap<>();

    public static BufferedImage loadImage(String ref, int width, int height) {
        BufferedImage image = loadImage(ref);
        if (image != null) {
            return scaleImage(image, width, height);
        }
        return null;
    }

    public static BufferedImage loadImage(String ref) {
        if (!images.containsKey(ref)) {
            try {
                images.put(ref, ImageIO.read(ImageHelper.class.getResourceAsStream(ref)));
            } catch (Exception e) {
                return null;
            }
        }
        return images.get(ref);
    }

    public static BufferedImage getBackground(CardView card, String backgroundName) {
        if (backgrounds.containsKey(backgroundName)) {
            return backgrounds.get(backgroundName);
        }

        BufferedImage background = new BufferedImage(FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
        backgrounds.put(backgroundName, background);
        return background;
    }

    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        return TransformedImageCache.getResizedImage(image, width, height);
    }

    public static void drawCosts(List<String> costs, Graphics2D g, int xOffset, int yOffset, ImageObserver o) {
        if (!costs.isEmpty()) {
            int costLeft = xOffset;
            for (int i = costs.size() - 1; i >= 0; i--) {
                String symbol = costs.get(i);
                g.drawString(symbol, costLeft, yOffset + SYMBOL_MAX_SPACE);
                costLeft -= SYMBOL_MAX_SPACE + 4;
            }
        }
    }

    /**
     * Returns an image scaled to the size appropriate for the card picture
     * panel
     *
     * @param original
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage getResizedImage(BufferedImage original, int width, int height) {
        return TransformedImageCache.getResizedImage(original, width, height);
    }

    /**
     * scale image
     *
     * @param sbi image to scale
     * @param imageType type of image
     * @param dWidth width of destination image
     * @param dHeight height of destination image
     * @return scaled image
     */
    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight) {
        return TransformedImageCache.getResizedImage(sbi, dWidth, dHeight);
    }

    /**
     * Returns an image scaled to the needed size
     *
     * @param original
     * @param sizeNeed
     * @return
     */
    public static BufferedImage getResizedImage(BufferedImage original, Rectangle sizeNeed) {
        return TransformedImageCache.getResizedImage(original, sizeNeed.width, sizeNeed.height);
    }

    /**
     * Get image using relative path in resources.
     *
     * @param path
     * @return
     */
    public static Image getImageFromResources(String path) {

        try(InputStream stream = UI.class.getResourceAsStream(path)) {
            ImageIO.setUseCache(false);
            BufferedImage image = ImageIO.read(stream);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
