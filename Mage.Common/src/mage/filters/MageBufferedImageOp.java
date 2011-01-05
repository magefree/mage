package mage.filters;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

/**
 * Mage abstract class that implements single-input/single-output
 * operations performed on {@link java.awt.image.BufferedImage}.
 *
 * @author nantuko
 */
public abstract class MageBufferedImageOp implements BufferedImageOp {

    /**
     * Creates compatible image for @param src image.
     */
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dest) {
        if (dest == null) {
            dest = src.getColorModel();
        }
        return new BufferedImage(dest, dest.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dest.isAlphaPremultiplied(), null);
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D destPt) {
        if (destPt == null) {
            destPt = new Point2D.Double();
        }
        destPt.setLocation(srcPt.getX(), srcPt.getY());
        return destPt;
    }

    /**
     * Gets ARGB pixels from image. Solves the performance
     * issue of BufferedImage.getRGB method.
     */
    public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
        }
        return image.getRGB(x, y, width, height, pixels, 0, width);
    }

    /**
     * Sets ARGB pixels in image. Solves the performance
     * issue of BufferedImage.setRGB method.
     */
    public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            image.getRaster().setDataElements(x, y, width, height, pixels);
        } else {
            image.setRGB(x, y, width, height, pixels, 0, width);
        }
    }
}
