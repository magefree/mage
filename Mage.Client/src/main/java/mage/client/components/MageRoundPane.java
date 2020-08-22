package mage.client.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.*;

import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.graphics.ShadowRenderer;


import mage.client.util.ImageCaches;
import mage.client.util.SoftValuesLoadingCache;

/**
 * Mage round pane with transparency. Used for tooltips.
 *
 * @author nantuko
 */
public class MageRoundPane extends JPanel {

    private int X_OFFSET = 30;
    private int Y_OFFSET = 30;
    private final Color defaultBackgroundColor = new Color(141, 130, 112, 200); // color of the frame of the popup window
    private Color backgroundColor = defaultBackgroundColor;
    private static final SoftValuesLoadingCache<ShadowKey, BufferedImage> SHADOW_IMAGE_CACHE;
    private static final SoftValuesLoadingCache<Key, BufferedImage> IMAGE_CACHE;

    static {
        SHADOW_IMAGE_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(MageRoundPane::createShadowImage));
        IMAGE_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(MageRoundPane::createImage));
    }

    private static final class ShadowKey {

        final int width;
        final int height;

        public ShadowKey(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.width;
            hash = 97 * hash + this.height;
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
            final ShadowKey other = (ShadowKey) obj;
            if (this.width != other.width) {
                return false;
            }
            if (this.height != other.height) {
                return false;
            }
            return true;
        }
    }

    private static final class Key {

        final int width;
        final int height;
        final int xOffset;
        final int yOffset;
        final Color backgroundColor;

        public Key(int width, int height, int xOffset, int yOffset, Color backgroundColor) {
            this.width = width;
            this.height = height;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.backgroundColor = backgroundColor;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + this.width;
            hash = 59 * hash + this.height;
            hash = 59 * hash + this.xOffset;
            hash = 59 * hash + this.yOffset;
            hash = 59 * hash + Objects.hashCode(this.backgroundColor);
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
            if (this.xOffset != other.xOffset) {
                return false;
            }
            if (this.yOffset != other.yOffset) {
                return false;
            }
            if (!Objects.equals(this.backgroundColor, other.backgroundColor)) {
                return false;
            }
            return true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(IMAGE_CACHE.getOrThrow(new Key(getWidth(), getHeight(), X_OFFSET, Y_OFFSET, backgroundColor)), 0, 0, null);
    }

    private static BufferedImage createImage(Key key) {
        int x = key.xOffset;
        int y = key.yOffset;
        int w = key.width - 2 * key.xOffset;
        int h = key.height - 2 * key.yOffset;
        int arc = 10;

        BufferedImage image = GraphicsUtilities.createCompatibleTranslucentImage(key.width, key.height);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage shadow = SHADOW_IMAGE_CACHE.getOrThrow(new ShadowKey(w, h));

        {
            int xOffset = (shadow.getWidth() - w) / 2;
            int yOffset = (shadow.getHeight() - h) / 2;
            g2.drawImage(shadow, x - xOffset, y - yOffset, null);
        }

        //////////////////////////////////////////////////////////////////
        // fill content
        /**
         * Add white translucent substrate
         */
        /*if (ALPHA != 0) {
            g2.setColor(new Color(255, 255, 255, ALPHA));
            g2.fillRoundRect(x, y, w, h, arc, arc);
        }*/
        g2.setColor(key.backgroundColor);
        g2.fillRoundRect(x, y, w, h, arc, arc);
        //////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////
        // draw border
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(x, y, w, h, arc, arc);
        // ////////////////////////////////////////////////////////////////

        g2.dispose();
        return image;
    }

    public void setXOffset(int x_offset) {
        X_OFFSET = x_offset;
    }

    public void setYOffset(int y_offset) {
        Y_OFFSET = y_offset;
    }

    private static BufferedImage createShadowImage(ShadowKey key) {
        int w = key.width;
        int h = key.height;
        int arc = 10;
        int shadowSize = 50;

        BufferedImage base = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
        Graphics2D g2 = base.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, w, h, arc, arc);
        g2.dispose();

        ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f,
                Color.GRAY);
        return renderer.createShadow(base);
    }

    public void showDialog(boolean bShow) {
        setVisible(bShow);
    }

    public void resetBackgroundColor() {
        this.backgroundColor = defaultBackgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;
}
