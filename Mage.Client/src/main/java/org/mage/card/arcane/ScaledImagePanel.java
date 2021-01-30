package org.mage.card.arcane;

import mage.client.util.TransformedImageCache;

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
        g.drawImage(TransformedImageCache.getResizedImage(srcImage, width, height), x, y, width, height, null);

        super.paint(g);
    }

    public BufferedImage getSrcImage() {
        return srcImage;
    }
}
