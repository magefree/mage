package org.mage.card.arcane;

import mage.MageInt;
import mage.util.DebugUtil;
import mage.view.CardView;
import mage.view.PermanentView;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stravant@gmail.com, JayDi85
 * <p>
 * Various static utilities for use in the card renderer
 */
public final class CardRendererUtils {

    // text colors for PT (mtgo and image render modes)
    private static final Color CARD_TEXT_COLOR_GOOD_LIGHT = new Color(182, 235, 168);
    private static final Color CARD_TEXT_COLOR_GOOD_DARK = new Color(52, 135, 88);
    private static final Color CARD_TEXT_COLOR_BAD_LIGHT = new Color(234, 153, 153);
    private static final Color CARD_TEXT_COLOR_BAD_DARK = new Color(200, 33, 33);

    /**
     * Convert an abstract image, whose underlying implementation may or may not
     * be a BufferedImage into a BufferedImage by creating one and coping the
     * contents if it is not, and simply up-casting if it is.
     *
     * @param img The image to convert
     * @return The converted image
     */
    public static BufferedImage toBufferedImage(Image img) {
        // Null? No conversion to do
        if (img == null) {
            return null;
        }

        // Already a buffered image?
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static Color abitbrighter(Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int alpha = c.getAlpha();

        int plus_r = (255 - r) / 2;
        int plus_g = (255 - g) / 2;
        int plus_b = (255 - b) / 2;

        return new Color(r + plus_r,
                g + plus_g,
                b + plus_b,
                alpha);
    }

    public static Color abitdarker(Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int alpha = c.getAlpha();

        int plus_r = Math.min(255 - r, r) / 2;
        int plus_g = Math.min(255 - g, g) / 2;
        int plus_b = Math.min(255 - b, b) / 2;

        return new Color(r - plus_r,
                g - plus_g,
                b - plus_b,
                alpha);
    }

    // Draw a rounded box with a 2-pixel border
    // Used on various card parts.
    public static void drawRoundedBox(Graphics2D g, int x, int y, int w, int h, int bevel, Paint border, Paint fill) {
        g.setColor(new Color(0, 0, 0, 150));
        g.drawOval(x - 1, y - 1, bevel * 2, h);
        g.setPaint(border);
        g.fillOval(x, y, bevel * 2 - 1, h - 2);
        g.drawOval(x + w - bevel * 2, y, bevel * 2 - 1, h - 1);
        g.drawOval(x + 1, y + 1, bevel * 2 - 3, h - 3);
        g.drawOval(x + 1 + w - bevel * 2, y + 1, bevel * 2 - 3, h - 3);
        g.drawRect(x + bevel, y, w - 2 * bevel, h - 1);
        g.drawRect(x + 1 + bevel, y + 1, w - 2 * bevel - 2, h - 3);
        g.setPaint(fill);
        g.fillOval(x + 2, y + 2, bevel * 2 - 4, h - 4);
        g.fillOval(x + 2 + w - bevel * 2, y + 2, bevel * 2 - 4, h - 4);
        g.fillRect(x + bevel, y + 2, w - 2 * bevel, h - 4);
        g.setPaint(fill);
        g.setColor(abitbrighter(g.getColor()));
        g.drawLine(x + 1 + bevel, y + 1, x + 1 + bevel + w - 2 * bevel - 2, y + 1);
        g.setPaint(fill);
        g.setColor(abitdarker(g.getColor()));
        g.drawLine(x + 1 + bevel, y + h - 2, x + 1 + bevel + w - 2 * bevel - 2, y + h - 2);
    }

    public static void drawZendikarLandBox(Graphics2D g, int x, int y, int w, int h, int bevel, Paint border, Paint fill) {
        g.setColor(new Color(0, 0, 0, 150));

        g.drawOval(x - 1, y, bevel * 2, h);
        g.setPaint(border);
        g.drawOval(x, y, bevel * 2 - 1, h - 1);
        g.drawOval(x + w - bevel * 2, y, bevel * 2 - 1, h - 1);
        g.drawOval(x + 1, y + 1, bevel * 2 - 3, h - 3);
        g.drawOval(x + 1 + w - bevel * 2, y + 1, bevel * 2 - 3, h - 3);

        // The big circle in the middle.. (diameter=2+1/4 of height) - 3/4 above line, 1/2 below  0.75 + .5 + 1= 2.25 = 9/4
        g.drawOval(x + w / 2 - h - h / 8, y - 3 * h / 4, 9 * h / 4, 9 * h / 4);

        g.drawRect(x + bevel, y, w - 2 * bevel, h - 1);
        g.drawRect(x + 1 + bevel, y + 1, w - 2 * bevel - 2, h - 3);
        g.setPaint(fill);
        g.setColor(abitbrighter(g.getColor()));
        g.drawLine(x + 1 + bevel, y + 1, x + 1 + bevel + w - 2 * bevel - 2, y + 1);
        g.setPaint(fill);
        g.setColor(abitdarker(g.getColor()));
        g.drawLine(x + 1 + bevel, y + h - 2, x + 1 + bevel + w - 2 * bevel - 2, y + h - 2);

        g.fillOval(x + 2, y + 2, bevel * 2 - 4, h - 4);
        g.fillOval(x + 2 + w - bevel * 2, y + 2, bevel * 2 - 4, h - 4);
        g.fillRect(x + bevel, y + 2, w - 2 * bevel, h - 4);

        g.fillOval(x + w / 2 - h - h / 8, y - 3 * h / 4, 9 * h / 4, 9 * h / 4);
    }

    // Get the width of a mana cost rendered with ManaSymbols.draw
    public static int getManaCostWidth(String manaCost, int symbolSize) {
        int width = 0;
        manaCost = manaCost.replace("\\", "");
        StringTokenizer tok = new StringTokenizer(manaCost, " ");
        while (tok.hasMoreTokens()) {
            tok.nextToken();
            width += symbolSize;
        }
        return width;
    }

    // Abbreviate a piece of rules text, making substitutions to decrease its
    // length. Also abbreviate reminder text.
    private static final Pattern abbreviationPattern;
    private static final Map<String, String> abbreviations = new HashMap<>();
    private static final Pattern killReminderTextPattern;

    static {
        // Available abbreviations
        abbreviations.put("enters the battlefield", "ETB");
        abbreviations.put("less than", "<");
        abbreviations.put("greater than", ">");

        // Compile into regex
        String patternString = "(";
        Iterator<String> it = abbreviations.keySet().iterator();
        while (it.hasNext()) {
            patternString += it.next();
            if (it.hasNext()) {
                patternString += "|";
            }
        }
        patternString += ")";
        abbreviationPattern = Pattern.compile(patternString);

        // Reminder text killing
        killReminderTextPattern = Pattern.compile("\\([^\\)]*\\)");
    }

    public static String abbreviateRule(String rule) {
        StringBuffer build = new StringBuffer();
        Matcher match = abbreviationPattern.matcher(rule);
        while (match.find()) {
            match.appendReplacement(build, abbreviations.get(match.group(1)));
        }
        match.appendTail(build);
        return build.toString();
    }

    public static String killReminderText(String rule) {
        return killReminderTextPattern.matcher(rule).replaceAll("")
                .replaceAll("<i>", "")
                .replaceAll("</i>", "");
    }

    public static Color copyColor(Color color) {
        if (color != null) {
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        } else {
            return null;
        }
    }

    public static String getCardLifeWithDamage(CardView cardView) {
        // life with damage
        String originLife = cardView.getToughness();
        if (cardView instanceof PermanentView) {
            int damage = ((PermanentView) cardView).getDamage();
            int life;
            try {
                life = Integer.parseInt(originLife);
                originLife = String.valueOf(Math.max(0, life - damage));
            } catch (NumberFormatException e) {
                //
            }
        }
        return originLife;
    }

    public static boolean isCardWithDamage(CardView cardView) {
        boolean haveDamage = false;
        if (cardView instanceof PermanentView) {
            haveDamage = ((PermanentView) cardView).getDamage() > 0;
        }
        return haveDamage;
    }

    public static Color getCardTextColor(MageInt value, boolean drawAsDamaged, Color defaultColor, boolean textLight) {
        if (drawAsDamaged) {
            return textLight ? CARD_TEXT_COLOR_BAD_LIGHT : CARD_TEXT_COLOR_BAD_DARK;
        }

        // boost colorizing
        if (value != null) {
            int currentValue = value.getValue();
            int baseValue = value.getModifiedBaseValue();
            if (currentValue < baseValue) {
                return textLight ? CARD_TEXT_COLOR_BAD_LIGHT : CARD_TEXT_COLOR_BAD_DARK;
            } else if (currentValue > baseValue) {
                return textLight ? CARD_TEXT_COLOR_GOOD_LIGHT : CARD_TEXT_COLOR_GOOD_DARK;
            } else {
                return defaultColor;
            }
        }

        return defaultColor;
    }

    /**
     * Reduce rect by percent (add empty space from all sides and keep rect position)
     * Example usage: reduce rect to fit auto-size text
     *
     * @param rect
     * @param reduceFactor
     * @return
     */
    public static Rectangle reduceRect(Rectangle rect, float reduceFactor) {
        float newWidth = rect.width * reduceFactor;
        float newHeight = rect.height * reduceFactor;
        int offsetX = Math.round((rect.width - newWidth) / 2f);
        int offsetY = Math.round((rect.height - newHeight) / 2f);
        return new Rectangle(rect.x + offsetX, rect.y + offsetY, Math.round(newWidth), Math.round(newHeight));
    }

    /**
     * Draw a String centered in the middle of a rectangle.
     *
     * @param g2d             The graphics instance
     * @param text            The string to draw
     * @param rect            The rectangle to center the text in
     * @param font
     * @param isAutoScaleFont if the text is too big then it will scale a font to fit it in the rect
     */
    public static void drawCenteredText(Graphics2D g2d, String text, Rectangle rect, Font font, boolean isAutoScaleFont) {
        if (DebugUtil.GUI_RENDER_CENTERED_TEXT_DRAW_DEBUG_LINES) {
            g2d.drawLine(rect.x, rect.y + rect.height / 2, rect.x + rect.width, rect.y + rect.height / 2);
            g2d.drawLine(rect.x + rect.width / 2, rect.y, rect.x + rect.width / 2, rect.y + rect.height);
        }

        // https://stackoverflow.com/a/23730104/1276632
        Font affectedFont = font;
        if (isAutoScaleFont) {
            affectedFont = scaleFont(g2d, text, rect, font);
        }

        g2d.setFont(affectedFont);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = affectedFont.createGlyphVector(frc, text);
        Rectangle2D box = gv.getVisualBounds();
        float offsetX = (float) (((rect.getWidth() - box.getWidth()) / 2d) + (-box.getX()));
        float offsetY = (float) (((rect.getHeight() - box.getHeight()) / 2d) + (-box.getY()));

        g2d.drawString(text, rect.x + offsetX, rect.y + offsetY);
    }

    /**
     * Auto scale font to fit current text inside the rect (e.g. decrease font size for too big text)
     *
     * @param g2d  graphics context
     * @param text text to draw
     * @param font base font
     * @param rect the bounds for fitting the string
     * @return a scaled font
     */
    private static Font scaleFont(Graphics2D g2d, String text, Rectangle rect, Font font) {
        // https://stackoverflow.com/a/876266/1276632
        FontRenderContext frc = g2d.getFontRenderContext();

        double needWidth = rect.getWidth();
        double needHeight = rect.getHeight();

        float fontMinSize = 1f;
        float fontMaxSize = 1000f;
        Font scaledFont = font;
        float scaledFontSize = scaledFont.getSize();

        while (fontMaxSize - fontMinSize > 1f) {
            scaledFont = scaledFont.deriveFont(scaledFontSize);

            TextLayout layout = new TextLayout(text, scaledFont, frc);
            float currentWidth = layout.getVisibleAdvance();
            LineMetrics metrics = scaledFont.getLineMetrics(text, frc);
            float currentHeight = metrics.getHeight();

            if ((currentWidth > needWidth) || (currentHeight > needHeight)) {
                fontMaxSize = scaledFontSize;
            } else {
                fontMinSize = scaledFontSize;
            }
            scaledFontSize = (fontMinSize + fontMaxSize) / 2f;
        }

        return scaledFont.deriveFont((float) Math.floor(scaledFontSize));
    }
}
