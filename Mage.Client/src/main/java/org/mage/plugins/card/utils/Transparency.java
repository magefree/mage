package org.mage.plugins.card.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

public final class Transparency {
    public static Image makeColorTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public final int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static BufferedImage makeImageTranslucent(BufferedImage source,
            double alpha) {
        BufferedImage target = new BufferedImage(source.getWidth(), source
                .getHeight(), java.awt.Transparency.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g2 = target.createGraphics();
        try {
            // Set the Graphics composite to Alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    (float) alpha));
            // Draw the image into the prepared reciver image
            g2.drawImage(source, null, 0, 0);
        } finally {
            // let go of all system resources in this Graphics
            g2.dispose();
        }

        // Return the image
        return target;
    }
}
