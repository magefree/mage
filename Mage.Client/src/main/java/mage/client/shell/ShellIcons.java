package mage.client.shell;

import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Phase 2: modern, flat vector replacements for XMage's dated button artwork.
 * <p>
 * Icons are drawn in code (Java2D) in a normalised 0..1 box, so they are crisp at any size and
 * theme-coloured (they follow the current FlatLaf foreground/accent). No binary assets are shipped,
 * which keeps everything additive and merge-safe.
 * <p>
 * Survivability &amp; incrementality: {@code ImageManagerImpl}'s image-cache loaders call
 * {@link #renderButton} from a single guarded seam (see {@code SHELL.md}). If we have a renderer for
 * a given resource name it is used; otherwise this returns {@code null} and the caller falls back to
 * the original PNG. So unfinished icons simply keep the old art — nothing breaks.
 *
 * @author modern-shell
 */
public final class ShellIcons {

    /** A glyph drawn into a Graphics2D already transformed so (0,0)..(1,1) is the icon box. */
    @FunctionalInterface
    private interface Glyph {
        void draw(Graphics2D g, Color fg, Color accent);
    }

    private static final Map<String, Glyph> GLYPHS = new HashMap<>();

    static {
        GLYPHS.put("concede", ShellIcons::concede);
        GLYPHS.put("switch_hands", ShellIcons::switchHands);
        GLYPHS.put("stop_watching", ShellIcons::stopWatching);
        GLYPHS.put("cancel_skip", ShellIcons::cancelSkip);
        GLYPHS.put("toggle_macro", ShellIcons::toggleMacro);
        GLYPHS.put("skip_to_end", ShellIcons::skipToEnd);
        GLYPHS.put("skip_turn", ShellIcons::skipTurn);
        GLYPHS.put("skip_all", ShellIcons::skipAll);
        GLYPHS.put("skip_to_main", ShellIcons::skipToMain);
        GLYPHS.put("skip_stack", ShellIcons::skipStack);
        GLYPHS.put("skip_to_previous_end", ShellIcons::skipToPreviousEnd);
    }

    private ShellIcons() {
    }

    /**
     * @return a freshly rendered modern icon for the resource (e.g. {@code "skip_turn.png"}), or
     * {@code null} if no modern glyph is defined (caller should fall back to the original image).
     */
    public static BufferedImage renderButton(String resourceName, int width, int height) {
        Glyph glyph = GLYPHS.get(stripName(resourceName));
        if (glyph == null) {
            return null;
        }
        int w = Math.max(1, width);
        int h = Math.max(1, height);
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            // centre a square icon box and switch to 0..1 coordinates
            double box = Math.min(w, h) * 0.62;
            double ox = (w - box) / 2.0;
            double oy = (h - box) / 2.0;
            g.translate(ox, oy);
            g.scale(box, box);
            g.setStroke(new BasicStroke(0.11f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            glyph.draw(g, foreground(), accent());
        } finally {
            g.dispose();
        }
        return img;
    }

    private static String stripName(String resourceName) {
        if (resourceName == null) {
            return "";
        }
        String n = resourceName.toLowerCase(Locale.ENGLISH).trim();
        int slash = Math.max(n.lastIndexOf('/'), n.lastIndexOf('\\'));
        if (slash >= 0) {
            n = n.substring(slash + 1);
        }
        if (n.endsWith(".png")) {
            n = n.substring(0, n.length() - 4);
        }
        return n;
    }

    private static Color foreground() {
        Color c = UIManager.getColor("Label.foreground");
        return c != null ? c : new Color(0xD6D9E0);
    }

    private static Color accent() {
        Color c = UIManager.getColor("Component.accentColor");
        return c != null ? c : new Color(0x7C9CE6);
    }

    // --- small drawing helpers (unit box) -------------------------------------------------------

    private static void line(Graphics2D g, double x1, double y1, double x2, double y2) {
        g.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    /** Filled right-pointing triangle: vertical edge at {@code left}, apex at {@code left+size}. */
    private static void playRight(Graphics2D g, double left, double size) {
        Path2D.Double p = new Path2D.Double();
        p.moveTo(left, 0.5 - size * 0.62);
        p.lineTo(left + size, 0.5);
        p.lineTo(left, 0.5 + size * 0.62);
        p.closePath();
        g.fill(p);
    }

    /** Vertical "end" bar. */
    private static void endBar(Graphics2D g, double x) {
        line(g, x, 0.16, x, 0.84);
    }

    // --- glyphs ---------------------------------------------------------------------------------

    private static void concede(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        line(g, 0.30, 0.08, 0.30, 0.92);       // flagpole
        g.setColor(accent);
        Path2D.Double flag = new Path2D.Double(); // waving flag
        flag.moveTo(0.30, 0.12);
        flag.lineTo(0.86, 0.27);
        flag.lineTo(0.30, 0.42);
        flag.closePath();
        g.fill(flag);
    }

    private static void switchHands(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        // top arrow → right
        line(g, 0.16, 0.34, 0.78, 0.34);
        line(g, 0.78, 0.34, 0.66, 0.25);
        line(g, 0.78, 0.34, 0.66, 0.43);
        // bottom arrow ← left
        line(g, 0.84, 0.66, 0.22, 0.66);
        line(g, 0.22, 0.66, 0.34, 0.57);
        line(g, 0.22, 0.66, 0.34, 0.75);
    }

    private static void stopWatching(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        g.draw(new Ellipse2D.Double(0.10, 0.30, 0.80, 0.40)); // eye outline
        g.fill(new Ellipse2D.Double(0.40, 0.38, 0.20, 0.24)); // pupil
        line(g, 0.14, 0.14, 0.86, 0.86);                      // "not watching" slash
    }

    private static void cancelSkip(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        g.draw(new Ellipse2D.Double(0.12, 0.12, 0.76, 0.76)); // ring
        line(g, 0.34, 0.34, 0.66, 0.66);                      // X
        line(g, 0.66, 0.34, 0.34, 0.66);
    }

    private static void toggleMacro(Graphics2D g, Color fg, Color accent) {
        g.setColor(new Color(0xE0, 0x52, 0x60));              // record red
        g.fill(new Ellipse2D.Double(0.22, 0.22, 0.56, 0.56));
    }

    // Skip family — a consistent "media transport" language: play triangle(s) + end bar(s).

    private static void skipToEnd(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        playRight(g, 0.20, 0.42);
        g.setColor(accent);
        endBar(g, 0.80);                                       // skip to end of this turn
    }

    private static void skipTurn(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        playRight(g, 0.12, 0.30);
        playRight(g, 0.42, 0.30);                              // double play = skip your whole turn
        g.setColor(accent);
        endBar(g, 0.84);
    }

    private static void skipAll(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        playRight(g, 0.10, 0.28);
        playRight(g, 0.38, 0.28);
        g.setColor(accent);
        endBar(g, 0.80);                                       // spaced double bar = skip onward
        endBar(g, 0.90);
    }

    private static void skipToMain(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        playRight(g, 0.16, 0.38);
        g.setColor(accent);
        g.fill(new Rectangle2D.Double(0.70, 0.38, 0.20, 0.24)); // square marker = main phase
    }

    private static void skipStack(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        line(g, 0.28, 0.28, 0.72, 0.28);                       // stacked layers
        line(g, 0.28, 0.44, 0.72, 0.44);
        line(g, 0.28, 0.60, 0.72, 0.60);
        g.setColor(accent);
        line(g, 0.40, 0.70, 0.50, 0.82);                       // down chevron = resolve/skip stack
        line(g, 0.60, 0.70, 0.50, 0.82);
    }

    private static void skipToPreviousEnd(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        playRight(g, 0.16, 0.36);
        g.setColor(accent);
        endBar(g, 0.72);                                       // two close bars = step just before
        endBar(g, 0.82);
    }
}
