package mage.client.util.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for creating BufferedImage object from Image instance.
 *
 * @author nantuko
 */
public class BufferedImageBuilder {

    private static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

    /**
     * Hide constructor
     */
    private BufferedImageBuilder() {

    }

    public static BufferedImage bufferImage(Image image) {
        return bufferImage(image, DEFAULT_IMAGE_TYPE);
    }

    public static BufferedImage bufferImage(Image image, int type) {
        if (image == null) {
            return null;
        }
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        //waitForImage(bufferedImage);
        return bufferedImage;
    }

    public static BufferedImage bufferImage(Image image, int type, Color color) {
        if (image == null) {
            return null;
        }
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        g.setColor(color);
        g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
        return bufferedImage;
    }

    private void waitForImage(BufferedImage bufferedImage) {
        final ImageLoadStatus imageLoadStatus = new ImageLoadStatus();
        bufferedImage.getHeight((img, infoflags, x, y, width, height) -> {
            if (infoflags == ImageObserver.ALLBITS) {
                imageLoadStatus.heightDone = true;
                return true;
            }
            return false;
        });
        bufferedImage.getWidth((img, infoflags, x, y, width, height) -> {
            if (infoflags == ImageObserver.ALLBITS) {
                imageLoadStatus.widthDone = true;
                return true;
            }
            return false;
        });
        while (!imageLoadStatus.widthDone && !imageLoadStatus.heightDone) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {

            }
        }
    }

    static class ImageLoadStatus {

        public boolean widthDone = false;
        public boolean heightDone = false;
    }

}
