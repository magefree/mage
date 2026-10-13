package mage.client.util.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.IntFunction;

/** Keep icon layout in logical units while requesting a raster for the current display. */
public final class HiDpiIcon implements Icon {
    private final int size;
    private final IntFunction<BufferedImage> images;
    private int cachedSize = -1;
    private BufferedImage cachedImage;

    public HiDpiIcon(int size, IntFunction<BufferedImage> images) {
        this.size = size;
        this.images = images;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component component, Graphics graphics, int x, int y) {
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            Dimension raster = HiDpiRenderUtil.getRasterSize(g, size, size);
            int pixelSize = Math.max(raster.width, raster.height);
            if (pixelSize != cachedSize) {
                cachedImage = images.apply(pixelSize);
                cachedSize = pixelSize;
            }
            if (cachedImage != null) {
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g.drawImage(cachedImage, x, y, size, size, component);
            }
        } finally {
            g.dispose();
        }
    }
}
