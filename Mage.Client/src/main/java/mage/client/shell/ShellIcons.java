package mage.client.shell;

import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
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
    private static final Map<String, Glyph> PHASE_GLYPHS = new HashMap<>();

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

        // deck-editor / lobby buttons (matched by /buttons/<name>.png via the component sweep)
        GLYPHS.put("search", ShellIcons::search);
        GLYPHS.put("copy", ShellIcons::copy);
        GLYPHS.put("paste", ShellIcons::paste);
        GLYPHS.put("deck_in", ShellIcons::deckIn);
        GLYPHS.put("deck_out", ShellIcons::deckOut);
        GLYPHS.put("left", ShellIcons::arrowLeft);
        GLYPHS.put("right", ShellIcons::arrowRight);
        GLYPHS.put("up", ShellIcons::arrowUp);
        GLYPHS.put("down", ShellIcons::arrowDown);

        PHASE_GLYPHS.put("untap", ShellIcons::phaseUntap);
        PHASE_GLYPHS.put("upkeep", ShellIcons::phaseUpkeep);
        PHASE_GLYPHS.put("draw", ShellIcons::phaseDraw);
        PHASE_GLYPHS.put("main1", ShellIcons::phaseMain1);
        PHASE_GLYPHS.put("combat_start", ShellIcons::phaseCombatStart);
        PHASE_GLYPHS.put("combat_attack", ShellIcons::phaseCombatAttack);
        PHASE_GLYPHS.put("combat_block", ShellIcons::phaseCombatBlock);
        PHASE_GLYPHS.put("combat_damage", ShellIcons::phaseCombatDamage);
        PHASE_GLYPHS.put("combat_end", ShellIcons::phaseCombatEnd);
        PHASE_GLYPHS.put("main2", ShellIcons::phaseMain2);
        PHASE_GLYPHS.put("cleanup", ShellIcons::phaseCleanup);
        PHASE_GLYPHS.put("next_turn", ShellIcons::phaseNextTurn);
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

    /**
     * @return a modern square phase icon for the phase (e.g. {@code "Combat_Attack"}), or
     * {@code null} if no modern glyph is defined (caller should fall back to the original image).
     */
    public static BufferedImage renderPhase(String phaseName, int size) {
        Glyph glyph = phaseName == null ? null
                : PHASE_GLYPHS.get(phaseName.toLowerCase(Locale.ENGLISH).trim());
        if (glyph == null) {
            return null;
        }
        int s = Math.max(1, size);
        BufferedImage img = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            double box = s * 0.66;
            double o = (s - box) / 2.0;
            g.translate(o, o);
            g.scale(box, box);
            g.setStroke(new BasicStroke(0.10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            glyph.draw(g, foreground(), accent());
        } finally {
            g.dispose();
        }
        return img;
    }

    /**
     * Modern round dialog button (Accept/Cancel/Next/Prev, normal or {@code .hover}) for the custom
     * rendered dialogs. Recognised by the {@code /dlg/} resource path; returns {@code null} for
     * anything else so the caller falls back to the original PNG.
     */
    public static BufferedImage renderDialogButton(String resourcePath, int size) {
        if (resourcePath == null) {
            return null;
        }
        String n = resourcePath.toLowerCase(Locale.ENGLISH);
        if (!n.contains("/dlg/")) {
            return null;
        }
        int kind; // 0 ok, 1 cancel, 2 next, 3 prev
        if (n.contains("dlg.ok")) {
            kind = 0;
        } else if (n.contains("dlg.cancel")) {
            kind = 1;
        } else if (n.contains("dlg.next")) {
            kind = 2;
        } else if (n.contains("dlg.prev")) {
            kind = 3;
        } else {
            return null;
        }
        boolean hover = n.contains(".hover");
        int s = size > 0 ? size : 60;
        BufferedImage img = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            double box = s * 0.92;
            double o = (s - box) / 2.0;
            g.translate(o, o);
            g.scale(box, box);
            g.setStroke(new BasicStroke(0.085f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Color col;
            switch (kind) {
                case 0:  col = new Color(0x3F, 0xB3, 0x6B); break; // accept = green
                case 1:  col = new Color(0xE0, 0x52, 0x60); break; // cancel = red
                default: col = accent();                           // next/prev = theme accent
            }
            Ellipse2D.Double circle = new Ellipse2D.Double(0.10, 0.10, 0.80, 0.80);
            if (hover) {
                g.setColor(col);
                g.fill(circle);
                g.setColor(Color.WHITE);
            } else {
                g.setColor(col);
                g.draw(circle);
            }
            switch (kind) {
                case 0: line(g, 0.32, 0.52, 0.44, 0.66); line(g, 0.44, 0.66, 0.70, 0.34); break; // check
                case 1: line(g, 0.37, 0.37, 0.63, 0.63); line(g, 0.63, 0.37, 0.37, 0.63); break; // X
                case 2: line(g, 0.45, 0.32, 0.61, 0.50); line(g, 0.61, 0.50, 0.45, 0.68); break; // ›
                default: line(g, 0.55, 0.32, 0.39, 0.50); line(g, 0.39, 0.50, 0.55, 0.68); break; // ‹
            }
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
        // drop a trailing pixel-size suffix so copy_24 / search_32 map to copy / search
        int us = n.lastIndexOf('_');
        if (us > 0 && n.substring(us + 1).matches("\\d+")) {
            n = n.substring(0, us);
        }
        return n;
    }

    /**
     * Icon glyph colour. Independently adjustable from body text via the {@code Shell.iconColor}
     * theme key (see the FlatLaf*.properties files); falls back to the label foreground. Icons read
     * better a little brighter than text, so the themes set this higher-contrast.
     */
    private static Color foreground() {
        Color c = UIManager.getColor("Shell.iconColor");
        if (c == null) {
            c = UIManager.getColor("Label.foreground");
        }
        return c != null ? c : new Color(0xED, 0xEF, 0xF3);
    }

    private static Color accent() {
        Color c = UIManager.getColor("Shell.iconAccent");
        if (c == null) {
            c = UIManager.getColor("Component.accentColor");
        }
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

    // --- deck-editor / lobby glyphs -------------------------------------------------------------

    private static void search(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        g.draw(new Ellipse2D.Double(0.16, 0.16, 0.44, 0.44)); // lens
        line(g, 0.56, 0.56, 0.84, 0.84);                       // handle
    }

    private static void copy(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // two overlapping sheets
        g.draw(new java.awt.geom.RoundRectangle2D.Double(0.40, 0.16, 0.42, 0.52, 0.10, 0.10));
        g.draw(new java.awt.geom.RoundRectangle2D.Double(0.18, 0.34, 0.42, 0.52, 0.10, 0.10));
    }

    private static void paste(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // clipboard + clip
        g.draw(new java.awt.geom.RoundRectangle2D.Double(0.24, 0.22, 0.52, 0.62, 0.10, 0.10));
        g.setColor(accent);
        g.fill(new java.awt.geom.RoundRectangle2D.Double(0.40, 0.14, 0.20, 0.14, 0.06, 0.06));
    }

    private static void deckIn(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        tray(g);
        g.setColor(accent);                                    // arrow down into tray = import
        line(g, 0.5, 0.18, 0.5, 0.54);
        line(g, 0.5, 0.54, 0.40, 0.44);
        line(g, 0.5, 0.54, 0.60, 0.44);
    }

    private static void deckOut(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        tray(g);
        g.setColor(accent);                                    // arrow up out of tray = export
        line(g, 0.5, 0.54, 0.5, 0.18);
        line(g, 0.5, 0.18, 0.40, 0.28);
        line(g, 0.5, 0.18, 0.60, 0.28);
    }

    private static void arrowLeft(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        line(g, 0.60, 0.24, 0.36, 0.50);
        line(g, 0.36, 0.50, 0.60, 0.76);
    }

    private static void arrowRight(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        line(g, 0.40, 0.24, 0.64, 0.50);
        line(g, 0.64, 0.50, 0.40, 0.76);
    }

    private static void arrowUp(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        line(g, 0.24, 0.60, 0.50, 0.36);
        line(g, 0.50, 0.36, 0.76, 0.60);
    }

    private static void arrowDown(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        line(g, 0.24, 0.40, 0.50, 0.64);
        line(g, 0.50, 0.64, 0.76, 0.40);
    }

    /** An open tray / inbox used by the import & export glyphs. */
    private static void tray(Graphics2D g) {
        line(g, 0.20, 0.62, 0.20, 0.80);
        line(g, 0.20, 0.80, 0.80, 0.80);
        line(g, 0.80, 0.80, 0.80, 0.62);
    }

    // --- phase glyphs ---------------------------------------------------------------------------

    private static void phaseUntap(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        Arc2D.Double arc = new Arc2D.Double(0.20, 0.20, 0.60, 0.60, 75, 290, Arc2D.OPEN); // rotation
        g.draw(arc);
        arrowAtArcEnd(g, arc);
    }

    private static void phaseUpkeep(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // hourglass = maintenance/time
        line(g, 0.26, 0.14, 0.74, 0.14);
        line(g, 0.26, 0.86, 0.74, 0.86);
        Path2D.Double top = new Path2D.Double();
        top.moveTo(0.28, 0.16); top.lineTo(0.72, 0.16); top.lineTo(0.5, 0.5); top.closePath();
        Path2D.Double bot = new Path2D.Double();
        bot.moveTo(0.28, 0.84); bot.lineTo(0.72, 0.84); bot.lineTo(0.5, 0.5); bot.closePath();
        g.draw(top); g.draw(bot);
    }

    private static void phaseDraw(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // card + up arrow = draw a card
        g.draw(new Rectangle2D.Double(0.30, 0.34, 0.40, 0.52));
        g.setColor(accent);
        line(g, 0.50, 0.42, 0.50, 0.12);
        line(g, 0.50, 0.12, 0.40, 0.24);
        line(g, 0.50, 0.12, 0.60, 0.24);
    }

    private static void phaseMain1(Graphics2D g, Color fg, Color accent) {
        romanBars(g, fg, accent, 1);                           // main phase I
    }

    private static void phaseMain2(Graphics2D g, Color fg, Color accent) {
        romanBars(g, fg, accent, 2);                           // main phase II
    }

    private static void phaseCombatStart(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        sword(g);                                              // sword up = combat begins
    }

    private static void phaseCombatAttack(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // crossed swords = attackers
        AffineTransform save = g.getTransform();
        g.rotate(Math.toRadians(28), 0.5, 0.5); sword(g); g.setTransform(save);
        g.rotate(Math.toRadians(-28), 0.5, 0.5); sword(g); g.setTransform(save);
    }

    private static void phaseCombatBlock(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);
        shield(g);                                             // shield = blockers
    }

    private static void phaseCombatDamage(Graphics2D g, Color fg, Color accent) {
        g.setColor(accent);                                    // burst = damage
        for (int i = 0; i < 8; i++) {
            double a = Math.toRadians(i * 45);
            double r0 = (i % 2 == 0) ? 0.14 : 0.10;
            double r1 = (i % 2 == 0) ? 0.40 : 0.30;
            line(g, 0.5 + r0 * Math.cos(a), 0.5 + r0 * Math.sin(a),
                    0.5 + r1 * Math.cos(a), 0.5 + r1 * Math.sin(a));
        }
    }

    private static void phaseCombatEnd(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // sword down = combat over
        AffineTransform save = g.getTransform();
        g.rotate(Math.toRadians(180), 0.5, 0.5);
        sword(g);
        g.setTransform(save);
    }

    private static void phaseCleanup(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // broom = cleanup
        line(g, 0.72, 0.16, 0.42, 0.62);                       // handle
        Path2D.Double head = new Path2D.Double();
        head.moveTo(0.30, 0.58); head.lineTo(0.54, 0.66);
        head.lineTo(0.46, 0.86); head.lineTo(0.22, 0.78); head.closePath();
        g.draw(head);
        g.setColor(accent);                                    // bristles
        line(g, 0.27, 0.74, 0.30, 0.86);
        line(g, 0.34, 0.76, 0.37, 0.88);
        line(g, 0.41, 0.78, 0.44, 0.90);
    }

    private static void phaseNextTurn(Graphics2D g, Color fg, Color accent) {
        g.setColor(fg);                                        // chevrons to a bar = advance turn
        chevronRight(g, 0.20);
        chevronRight(g, 0.42);
        g.setColor(accent);
        line(g, 0.78, 0.18, 0.78, 0.82);
    }

    // --- shape helpers --------------------------------------------------------------------------

    /** Vertical sword with the tip pointing up, centred on x=0.5. */
    private static void sword(Graphics2D g) {
        line(g, 0.5, 0.10, 0.5, 0.70);   // blade
        line(g, 0.36, 0.62, 0.64, 0.62); // crossguard
        line(g, 0.5, 0.70, 0.5, 0.84);   // grip
    }

    private static void shield(Graphics2D g) {
        Path2D.Double s = new Path2D.Double();
        s.moveTo(0.5, 0.12);
        s.lineTo(0.82, 0.24);
        s.lineTo(0.82, 0.52);
        s.curveTo(0.82, 0.72, 0.66, 0.82, 0.5, 0.88);
        s.curveTo(0.34, 0.82, 0.18, 0.72, 0.18, 0.52);
        s.lineTo(0.18, 0.24);
        s.closePath();
        g.draw(s);
    }

    private static void chevronRight(Graphics2D g, double x) {
        line(g, x, 0.24, x + 0.20, 0.5);
        line(g, x + 0.20, 0.5, x, 0.76);
    }

    /** Roman-numeral phase markers: "I" or "II" with small serifs. */
    private static void romanBars(Graphics2D g, Color fg, Color accent, int count) {
        g.setColor(fg);
        double[] xs = count == 1 ? new double[]{0.5} : new double[]{0.38, 0.62};
        for (double x : xs) {
            line(g, x, 0.20, x, 0.80);          // stem
            line(g, x - 0.10, 0.20, x + 0.10, 0.20); // top serif
            line(g, x - 0.10, 0.80, x + 0.10, 0.80); // bottom serif
        }
    }

    /** Draw a small arrowhead at the terminal end of an arc (for the rotation glyph). */
    private static void arrowAtArcEnd(Graphics2D g, Arc2D arc) {
        Point2D e = arc.getEndPoint();
        double rx = e.getX() - 0.5, ry = e.getY() - 0.5;       // radial direction
        double len = Math.hypot(rx, ry);
        if (len == 0) {
            return;
        }
        double tx = -ry / len, ty = rx / len;                  // tangent
        double s = 0.13;
        double bx = e.getX() - tx * s, by = e.getY() - ty * s; // back along tangent
        double nx = rx / len, ny = ry / len;                   // outward normal
        g.draw(new Line2D.Double(e.getX(), e.getY(), bx + nx * s * 0.7, by + ny * s * 0.7));
        g.draw(new Line2D.Double(e.getX(), e.getY(), bx - nx * s * 0.7, by - ny * s * 0.7));
    }
}
