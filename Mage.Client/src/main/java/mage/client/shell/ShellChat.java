package mage.client.shell;

import mage.client.chat.ChatPanelBasic;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Phase 2: collapsible in-game chat that reclaims horizontal play-area width, with a small unread
 * indicator so messages aren't missed while the chat is hidden.
 * <p>
 * Behaviour: the chat <b>starts collapsed</b> to a thin strip; when a new player message arrives
 * while collapsed, the unread badge briefly <b>pulses</b>. Clicking the strip toggles the chat.
 * <p>
 * Survivability: all behaviour lives here. The game panel calls {@link #install} from a single
 * guarded seam (see {@code SHELL.md}). We wrap the split pane's right (chat) side in a thin
 * container so the toggle strip stays visible even when collapsed; nothing in the existing chat
 * classes is modified. The collapsed divider position is <b>pinned</b> via a listener so it survives
 * the game panel's later "restore saved divider locations" pass.
 * <p>
 * The unread indicator is driven off the <b>player chat</b> ({@code userChatPanel}) only — the game
 * log updates on every action and would make the badge meaningless.
 *
 * @author modern-shell
 */
public final class ShellChat {

    private static final int STRIP_WIDTH = 24;
    private static final double DEFAULT_EXPANDED_PROPORTION = 0.80;
    private static final long PULSE_MS = 750;

    private final JSplitPane split;
    private final JComponent chatSide;       // the original right component (chat + logs)
    private final ChatToggleButton toggle;
    private final Timer pulseTimer;

    private boolean collapsed;
    private boolean pinning;                  // guards reentrancy while we re-pin the divider
    private double expandedProportion = DEFAULT_EXPANDED_PROPORTION;
    private int unread;
    private long pulseStart;
    private float pulse;                       // 0..1 during a pulse, else 0

    private ShellChat(JSplitPane split, JComponent chatSide) {
        this.split = split;
        this.chatSide = chatSide;
        this.toggle = new ChatToggleButton();
        this.pulseTimer = new Timer(30, e -> advancePulse());
        this.pulseTimer.setCoalesce(true);
    }

    /**
     * Wire collapsible behaviour onto the battlefield/chats split pane.
     *
     * @param split         the {@code splitBattlefieldAndChats} pane (left = play area, right = chats)
     * @param userChatPanel the player chat panel, used to detect unread messages while collapsed
     */
    public static void install(JSplitPane split, ChatPanelBasic userChatPanel) {
        if (split == null) {
            return;
        }
        Component right = split.getRightComponent();
        if (!(right instanceof JComponent)) {
            return;
        }
        ShellChat shell = new ShellChat(split, (JComponent) right);
        shell.wrap();
        shell.watchForMessages(userChatPanel);
    }

    private void wrap() {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(toggle, BorderLayout.WEST);
        container.add(chatSide, BorderLayout.CENTER);
        split.setRightComponent(container);

        toggle.addActionListener(e -> setCollapsed(!collapsed));

        // Re-pin the collapsed position whenever the divider moves (window resize, or the game
        // panel's restore-saved-locations pass) so "start collapsed" sticks.
        split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            if (collapsed && !pinning) {
                pinCollapsed();
            }
        });
        split.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (collapsed) {
                    pinCollapsed();
                }
            }
        });

        // start closed
        collapsed = true;
        chatSide.setVisible(false);
        toggle.refresh();
        SwingUtilities.invokeLater(this::pinCollapsed);
    }

    private void setCollapsed(boolean value) {
        if (value == collapsed) {
            return;
        }
        if (value) {
            // remember the current proportion so we can return to it
            int width = split.getWidth();
            if (width > 0) {
                double p = (double) split.getDividerLocation() / width;
                if (p > 0.05 && p < 0.95) {
                    expandedProportion = p;
                }
            }
            collapsed = true;
            chatSide.setVisible(false);
            pinCollapsed();
        } else {
            collapsed = false;
            chatSide.setVisible(true);
            split.setDividerLocation(expandedProportion);
            clearUnread();
        }
        toggle.refresh();
    }

    private void pinCollapsed() {
        if (!collapsed) {
            return;
        }
        int loc = split.getWidth() - STRIP_WIDTH - split.getDividerSize();
        if (loc > 0 && split.getDividerLocation() != loc) {
            pinning = true;
            try {
                split.setDividerLocation(loc);
            } finally {
                pinning = false;
            }
        }
    }

    private void watchForMessages(ChatPanelBasic userChatPanel) {
        if (userChatPanel == null) {
            return;
        }
        JTextComponent textComponent = findTextComponent(userChatPanel);
        if (textComponent == null) {
            return;
        }
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (collapsed && e.getLength() > 0) {
                    unread++;
                    startPulse();
                    toggle.refresh();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // chat clear() etc. — not a new message
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // styling only
            }
        });
    }

    private void clearUnread() {
        unread = 0;
        pulse = 0f;
        pulseTimer.stop();
    }

    private void startPulse() {
        pulseStart = System.currentTimeMillis();
        if (!pulseTimer.isRunning()) {
            pulseTimer.start();
        }
    }

    private void advancePulse() {
        long elapsed = System.currentTimeMillis() - pulseStart;
        if (elapsed >= PULSE_MS) {
            pulse = 0f;
            pulseTimer.stop();
        } else {
            pulse = elapsed / (float) PULSE_MS; // 0..1
        }
        toggle.repaint();
    }

    private static JTextComponent findTextComponent(Container root) {
        for (Component child : root.getComponents()) {
            if (child instanceof JTextComponent) {
                return (JTextComponent) child;
            }
            if (child instanceof Container) {
                JTextComponent found = findTextComponent((Container) child);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /**
     * Thin vertical strip that toggles the chat and paints a (pulsing) unread badge. Kept
     * self-contained so it relies on no upstream rendering code.
     */
    private final class ChatToggleButton extends JButton {

        ChatToggleButton() {
            setFocusable(false);
            setBorder(BorderFactory.createEmptyBorder());
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Dimension d = new Dimension(STRIP_WIDTH, 10);
            setPreferredSize(d);
            setMinimumSize(d);
            refresh();
        }

        void refresh() {
            setToolTipText(collapsed
                    ? (unread > 0 ? "Show chat (" + unread + " new)" : "Show chat")
                    : "Hide chat");
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                // subtle strip background using current theme separator tone
                Color bg = UIManager.getColor("Separator.foreground");
                if (bg == null) {
                    bg = getBackground();
                }
                g2.setColor(bg);
                g2.fillRect(0, 0, w, h);

                // toggle arrow: points left to OPEN when collapsed, right to CLOSE when expanded
                Color arrow = UIManager.getColor("Label.foreground");
                if (arrow == null) {
                    arrow = Color.LIGHT_GRAY;
                }
                g2.setColor(arrow);
                int cy = h / 2;
                int aw = 6;
                int ah = 10;
                int cx = w / 2;
                Polygon p = new Polygon();
                if (collapsed) {
                    p.addPoint(cx + aw / 2, cy - ah / 2);
                    p.addPoint(cx - aw / 2, cy);
                    p.addPoint(cx + aw / 2, cy + ah / 2);
                } else {
                    p.addPoint(cx - aw / 2, cy - ah / 2);
                    p.addPoint(cx + aw / 2, cy);
                    p.addPoint(cx - aw / 2, cy + ah / 2);
                }
                g2.fillPolygon(p);

                // unread badge near the top when collapsed
                if (collapsed && unread > 0) {
                    Color accent = UIManager.getColor("Component.accentColor");
                    if (accent == null) {
                        accent = new Color(0x7C9CE6);
                    }
                    int dia = w - 6;
                    int bx = (w - dia) / 2;
                    int by = 6;

                    // pulsing ripple ring (fades outward) + gentle "beat" on the badge itself
                    float beat = 0f;
                    if (pulse > 0f) {
                        double s = Math.sin(pulse * Math.PI); // 0..1..0 over the pulse
                        beat = (float) s;
                        int ripple = (int) (pulse * 9);
                        int alpha = Math.max(0, (int) ((1f - pulse) * 170));
                        g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), alpha));
                        g2.setStroke(new BasicStroke(1.5f));
                        g2.drawOval(bx - ripple, by - ripple, dia + 2 * ripple, dia + 2 * ripple);
                    }
                    int grow = Math.round(beat * 3);
                    int gx = bx - grow;
                    int gy = by - grow;
                    int gdia = dia + 2 * grow;

                    g2.setColor(accent);
                    g2.fillOval(gx, gy, gdia, gdia);

                    String label = unread > 9 ? "9+" : Integer.toString(unread);
                    g2.setColor(Color.WHITE);
                    Font f = getFont().deriveFont(Font.BOLD, 9f);
                    g2.setFont(f);
                    FontMetrics fm = g2.getFontMetrics();
                    int tx = gx + (gdia - fm.stringWidth(label)) / 2;
                    int ty = gy + (gdia - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(label, tx, ty);
                }
            } finally {
                g2.dispose();
            }
        }
    }
}
