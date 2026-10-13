package org.mage.card.arcane;

import mage.client.util.TransformedImageCache;
import mage.client.util.gui.HiDpiRenderUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaledImagePanel extends JPanel {

    private static final long serialVersionUID = -1523279873208605664L;
    private volatile BufferedImage srcImage;

    public ScaledImagePanel() {
        super(false);
        setOpaque(false);
    }

    public void clearImage() {
        srcImage = null;
        repaint();
    }

    public void setImage(BufferedImage srcImage) {
        this.srcImage = srcImage;
    }

    public boolean hasImage() {
        return srcImage != null;
    }

    @Override
    public void paint(Graphics g) {
        if (srcImage == null) {
            return;
        }

        Insets border = getInsets();
        int x = border.left;
        int y = border.top;
        int width = getWidth() - border.left - border.right;
        int height = getHeight() - border.top - border.bottom;
        if (width > 0 && height > 0) {
            Graphics2D graphics = (Graphics2D) g.create();
            try {
                Dimension raster = HiDpiRenderUtil.getRasterSize(graphics, width, height);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.drawImage(TransformedImageCache.getResizedImage(srcImage, raster.width, raster.height),
                        x, y, width, height, null);
            } finally {
                graphics.dispose();
            }
        }

        super.paint(g);
    }

    public BufferedImage getSrcImage() {
        return srcImage;
    }
}
