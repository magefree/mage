package mage.client.util.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

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
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        //waitForImage(bufferedImage);
        return bufferedImage;
    }

    private void waitForImage(BufferedImage bufferedImage) {
        final ImageLoadStatus imageLoadStatus = new ImageLoadStatus();
        bufferedImage.getHeight(new ImageObserver() {
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                if (infoflags == ALLBITS) {
                    imageLoadStatus.heightDone = true;
                    return true;
                }
                return false;
            }
        });
        bufferedImage.getWidth(new ImageObserver() {
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                if (infoflags == ALLBITS) {
                    imageLoadStatus.widthDone = true;
                    return true;
                }
                return false;
            }
        });
        while (!imageLoadStatus.widthDone && !imageLoadStatus.heightDone) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }
        }
    }

    class ImageLoadStatus {
        public boolean widthDone = false;
        public boolean heightDone = false;
    }

}