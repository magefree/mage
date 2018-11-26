package org.mage.card.arcane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stravant@gmail.com
 * <p>
 * Various static utilities for use in the card renderer
 */
public final class CardRendererUtils {

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

        int plus_r = (int) ((255 - r) / 2);
        int plus_g = (int) ((255 - g) / 2);
        int plus_b = (int) ((255 - b) / 2);

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

        int plus_r = (int) (Math.min(255 - r, r) / 2);
        int plus_g = (int) (Math.min(255 - g, g) / 2);
        int plus_b = (int) (Math.min(255 - b, b) / 2);

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
        g.drawOval(x, y, bevel * 2 - 1, h - 1);
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
}
