package org.mage.plugins.rating.ui;

import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.ResampleOp;

/**
 * Contains utility methods to work with images.
 * 
 * @author ayrat
 */
public class ImageHelper {
    /**
     * Returns an image scaled to the size appropriate for the card picture
     * panel
     */
    public static BufferedImage getResizedImage(BufferedImage original, int width, int height) {
        ResampleOp resampleOp = new ResampleOp(width, height);
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

}
