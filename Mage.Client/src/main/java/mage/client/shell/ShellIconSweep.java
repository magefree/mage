package mage.client.shell;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Phase 2 icons: modernises the deck-editor / lobby button artwork that is loaded ad-hoc as
 * {@code new ImageIcon(getClass().getResource("/buttons/x.png"))} across many (mostly
 * NetBeans-generated) files, which have no central loading funnel.
 * <p>
 * Rather than editing dozens of generated call sites (which would break the survive-upstream goal),
 * this walks the component tree of each window as it opens and swaps any icon whose source path
 * (recorded in {@link ImageIcon#getDescription()}) matches a {@code /buttons/<name>.png} for which
 * {@link ShellIcons} has a modern glyph. Icons we don't render (mana colours, card types, …) are
 * left untouched. The replacement keeps the original description, so re-sweeping on a theme change
 * simply re-renders in the new colours.
 * <p>
 * Survivability: there is <b>no new upstream seam</b> — {@link #install()} is called from
 * {@link Shell#installLookAndFeel()} (which already runs once at startup, behind the shell flag).
 *
 * @author modern-shell
 */
public final class ShellIconSweep {

    private static final String MARKER = "/buttons/";
    private static boolean listenerInstalled = false;

    private ShellIconSweep() {
    }

    /**
     * Install the window listener (once) and sweep any already-open windows. Safe to call repeatedly
     * (e.g. on every theme refresh) — the listener is only added once and re-sweeps re-render icons.
     */
    public static synchronized void install() {
        if (!listenerInstalled) {
            Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
                if (event instanceof WindowEvent && event.getID() == WindowEvent.WINDOW_OPENED) {
                    Window w = ((WindowEvent) event).getWindow();
                    SwingUtilities.invokeLater(() -> sweep(w));
                }
            }, AWTEvent.WINDOW_EVENT_MASK);
            listenerInstalled = true;
        }
        for (Window w : Window.getWindows()) {
            if (w.isShowing()) {
                sweep(w);
            }
        }
    }

    private static void sweep(Component c) {
        if (c instanceof AbstractButton) {
            sweepButton((AbstractButton) c);
        } else if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            Icon modern = modern(label.getIcon());
            if (modern != null) {
                label.setIcon(modern);
            }
        }
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                sweep(child);
            }
        }
    }

    private static void sweepButton(AbstractButton b) {
        Icon i;
        if ((i = modern(b.getIcon())) != null) {
            b.setIcon(i);
        }
        if ((i = modern(b.getPressedIcon())) != null) {
            b.setPressedIcon(i);
        }
        if ((i = modern(b.getSelectedIcon())) != null) {
            b.setSelectedIcon(i);
        }
        if ((i = modern(b.getRolloverIcon())) != null) {
            b.setRolloverIcon(i);
        }
        if ((i = modern(b.getRolloverSelectedIcon())) != null) {
            b.setRolloverSelectedIcon(i);
        }
    }

    /**
     * @return a modern replacement icon for the given icon if it was loaded from a {@code /buttons/}
     * resource we have a glyph for; otherwise {@code null} (leave the original in place).
     */
    private static Icon modern(Icon icon) {
        if (!(icon instanceof ImageIcon)) {
            return null;
        }
        ImageIcon ii = (ImageIcon) icon;
        String desc = ii.getDescription();
        if (desc == null) {
            return null;
        }
        int idx = desc.indexOf(MARKER);
        if (idx < 0) {
            return null;
        }
        String name = desc.substring(idx + MARKER.length());
        int q = name.indexOf('?');
        if (q >= 0) {
            name = name.substring(0, q);
        }
        int w = ii.getIconWidth();
        int h = ii.getIconHeight();
        if (w <= 0 || h <= 0) {
            return null;
        }
        BufferedImage img = ShellIcons.renderButton(name, w, h);
        if (img == null) {
            return null;
        }
        ImageIcon out = new ImageIcon(img);
        out.setDescription(desc); // keep so a later re-sweep recognises and re-renders it
        return out;
    }
}
