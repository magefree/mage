package org.mage.card.arcane;

import mage.client.util.ImageCaches;
import mage.client.util.SoftValuesLoadingCache;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class GlowText extends JLabel {

    private static final long serialVersionUID = 1827677946939348001L;
    private int glowSize;
    @SuppressWarnings("unused")
    private float glowIntensity;
    private Color glowColor;
    private boolean wrap;
    private int lineCount = 0;
    private static final SoftValuesLoadingCache<Key, BufferedImage> IMAGE_CACHE;

    private static final class Key {

        final int width;
        final int height;
        final String text;
        final Map<TextAttribute, ?> fontAttributes;
        final Color color;
        final int glowSize;
        final float glowIntensity;
        final Color glowColor;
        final boolean wrap;

        // used to pass the native font to the create function so we don't waste performance recreating it, but without holding onto the native object
        final transient WeakReference<Font> originalFont;

        Font getFont() {
            Font res = this.originalFont.get();
            if (res == null) {
                res = Font.getFont(this.fontAttributes);
            }
            return res;
        }

        public Key(int width, int height, String text, Font font, Color color, int glowSize, float glowIntensity, Color glowColor, boolean wrap) {
            this.width = width;
            this.height = height;
            this.text = text;
            this.originalFont = new WeakReference<>(font);
            this.fontAttributes = font.getAttributes();
            this.color = color;
            this.glowSize = glowSize;
            this.glowIntensity = glowIntensity;
            this.glowColor = glowColor;
            this.wrap = wrap;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + this.width;
            hash = 23 * hash + this.height;
            hash = 23 * hash + Objects.hashCode(this.text);
            hash = 23 * hash + Objects.hashCode(this.fontAttributes);
            hash = 23 * hash + Objects.hashCode(this.color);
            hash = 23 * hash + this.glowSize;
            hash = 23 * hash + Float.floatToIntBits(this.glowIntensity);
            hash = 23 * hash + Objects.hashCode(this.glowColor);
            hash = 23 * hash + (this.wrap ? 1 : 0);
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
            if (this.glowSize != other.glowSize) {
                return false;
            }
            if (Float.floatToIntBits(this.glowIntensity) != Float.floatToIntBits(other.glowIntensity)) {
                return false;
            }
            if (this.wrap != other.wrap) {
                return false;
            }
            if (!Objects.equals(this.text, other.text)) {
                return false;
            }
            if (!Objects.equals(this.fontAttributes, other.fontAttributes)) {
                return false;
            }
            if (!Objects.equals(this.color, other.color)) {
                return false;
            }
            return Objects.equals(this.glowColor, other.glowColor);
        }
    }

    static {
        IMAGE_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(GlowText::createGlowImage));
    }

    public void setGlow(Color glowColor, int size, float intensity) {
        this.glowColor = glowColor;
        this.glowSize = size;
        this.glowIntensity = intensity;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += glowSize;
        size.height += glowSize / 2;
        return size;
    }

    @Override
    public void paint(Graphics g) {
        if (getText().isEmpty()) {
            return;
        }

        g.drawImage(getGlowImage(), 0, 0, null);
    }

    public BufferedImage getGlowImage() {
        return IMAGE_CACHE.getOrThrow(new Key(getWidth(), getHeight(), getText(), getFont(), getForeground(), glowSize, glowIntensity, glowColor, wrap));
    }

    private static BufferedImage createGlowImage(Key key) {
        Dimension size = new Dimension(key.width, key.height);
        BufferedImage image = GraphicsUtilities.createCompatibleTranslucentImage(size.width, size.height);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int textX = 0, textY = 0;
        int wrapWidth = Math.max(0, key.wrap ? size.width - key.glowSize : Integer.MAX_VALUE);

        AttributedString attributedString = new AttributedString(key.text);
        attributedString.addAttribute(TextAttribute.FONT, key.getFont());
        AttributedCharacterIterator charIterator = attributedString.getIterator();
        FontRenderContext fontContext = g2d.getFontRenderContext();

        LineBreakMeasurer measurer = new LineBreakMeasurer(charIterator, BreakIterator.getWordInstance(Locale.ENGLISH), fontContext);
        int lineCount = 0;
        while (measurer.getPosition() < charIterator.getEndIndex()) {
            //TextLayout textLayout = measurer.nextLayout(wrapWidth);
            lineCount++;
            if (lineCount > 2) {
                break;
            }
        }
        charIterator.first();
        // Use char wrap if word wrap would cause more than two lines of text.
        if (lineCount > 2) {
            measurer = new LineBreakMeasurer(charIterator, BreakIterator.getCharacterInstance(Locale.ENGLISH), fontContext);
        } else {
            measurer.setPosition(0);
        }
        while (measurer.getPosition() < charIterator.getEndIndex()) {
            TextLayout textLayout = measurer.nextLayout(wrapWidth);
            float ascent = textLayout.getAscent();
            textY += ascent; // Move down to baseline.

            g2d.setColor(key.glowColor);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            int glowSize = key.glowSize;
            textLayout.draw(g2d, textX + glowSize / 2 + 1, textY + glowSize / 2 - 1);
            textLayout.draw(g2d, textX + glowSize / 2 + 1, textY + glowSize / 2 + 1);
            textLayout.draw(g2d, textX + glowSize / 2 - 1, textY + glowSize / 2 - 1);
            textLayout.draw(g2d, textX + glowSize / 2 - 1, textY + glowSize / 2 + 1);

            g2d.setColor(key.color);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            textLayout.draw(g2d, textX + glowSize / 2, textY + glowSize / 2);

            textY += textLayout.getDescent() + textLayout.getLeading(); // Move down to top of next line.
        }
        return image;
    }

    public void setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
    }
}
