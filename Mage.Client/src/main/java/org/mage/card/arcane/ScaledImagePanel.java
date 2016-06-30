package org.mage.card.arcane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import mage.client.util.TransformedImageCache;

public class ScaledImagePanel extends JPanel {
    private static final long serialVersionUID = -1523279873208605664L;
    private volatile BufferedImage srcImage;

    public ScaledImagePanel () {
        super(false);
        setOpaque(false);
    }
    
    public void clearImage () {
        srcImage = null;
        repaint();
    }

    public void setImage(BufferedImage srcImage) {
        this.srcImage = srcImage;
    }

    public boolean hasImage () {
        return srcImage != null;
    }

    @Override
    public void paint (Graphics g) {
        if (srcImage == null) {
            return;
        }

        g.drawImage(TransformedImageCache.getResizedImage(srcImage, getWidth(), getHeight()), 0, 0, null);
    }

    public BufferedImage getSrcImage() {
        return srcImage;
    }
}
