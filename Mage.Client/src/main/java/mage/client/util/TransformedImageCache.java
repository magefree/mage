/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Map;
import mage.client.util.ImageHelper;

/**
 *
 * @author user
 */
public class TransformedImageCache {
    private final static class Key
    {
        final int width;
        final int height;
        final double angle;

        public Key(int width, int height, double angle) {
            this.width = width;
            this.height = height;
            this.angle = angle;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + this.width;
            hash = 53 * hash + this.height;
            hash = 53 * hash + (int) (Double.doubleToLongBits(this.angle) ^ (Double.doubleToLongBits(this.angle) >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if (this.width != other.width) {
                return false;
            }
            if (this.height != other.height) {
                return false;
            }
            if (Double.doubleToLongBits(this.angle) != Double.doubleToLongBits(other.angle)) {
                return false;
            }
            return true;
        }
    }

    static Map<Key, Map<BufferedImage, BufferedImage>> IMAGE_CACHE;

    static
    {
        // TODO: can we use a single map?
        IMAGE_CACHE = ImageCaches.register(new MapMaker().softValues().makeComputingMap(new Function<Key, Map<BufferedImage, BufferedImage>>() {
            @Override
            public Map<BufferedImage, BufferedImage> apply(final Key key) {
                return new MapMaker().weakKeys().softValues().makeComputingMap(new Function<BufferedImage, BufferedImage>() {
                    @Override
                    public BufferedImage apply(BufferedImage image) {
                        if(key.width != image.getWidth() || key.height != image.getHeight())
                            image = resizeImage(image, key.width, key.height);
                        if(key.angle != 0.0)
                            image = rotateImage(image, key.angle);
                        return image;
                    }
                });
            }
        }));
    }

    private static BufferedImage rotateImage(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();

        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static BufferedImage resizeImage(BufferedImage original, int width, int height) {
        ResampleOp resampleOp = new ResampleOp(width, height);
        BufferedImage image = resampleOp.filter(original, null);
        return image;
    }

    public static BufferedImage getResizedImage(BufferedImage image, int width, int height)
    {
        return getRotatedResizedImage(image, width, height, 0.0);
    }

    public static BufferedImage getRotatedImage(BufferedImage image, double angle)
    {
        return getRotatedResizedImage(image, -1, -1, angle);
    }

    public static BufferedImage getRotatedResizedImage(BufferedImage image, int width, int height, double angle)
    {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if(angle == 0.0 && (width < 0 || imageWidth == width) && (height < 0 || imageHeight == height))
            return image;

        int resWidth;
        int resHeight;
        if(width < 0 && height < 0) {
            resWidth = imageWidth;
            resHeight = imageHeight;
        } else if((height < 0) || (width >= 0 && imageHeight * width <= imageWidth * height)) {
            resWidth = width;
            resHeight = imageHeight * width / imageWidth;
        } else {
            resWidth = imageWidth * height / imageHeight;
            resHeight = height;
        }

        if(angle == 0.0 && imageWidth == resWidth && imageHeight == resHeight)
            return image;

        return IMAGE_CACHE.get(new Key(resWidth, resHeight, angle)).get(image);
    }
}
