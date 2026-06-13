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
 * Survivability: all behaviour lives here. The game panel calls {@link #install} from a single
 * guarded seam (see {@code SHELL.md}). We wrap the split pane's right (chat) side in a thin
 * container so the toggle strip stays visible even when the chat is collapsed; nothing in the
 * existing chat classes is modified.
 * <p>
 * The unread indicator is driven off the <b>player chat</b> ({@code userChatPanel}) only — the game
 * log updates on every action and would make the badge meaningless.
 *
 * @author modern-shell
 */
public final class ShellChat {

    private static final int STRIP_WIDTH = 24;
    private static final double DEFAULT_EXPANDED_PROPORTION = 0.80;

    private final JSplitPane split;
    private final JComponent chatSide;       // the original right component (chat + logs)
    private final ChatToggleButton toggle;

    private boolean collapsed;
    private double expandedProportion = DEFAULT_EXPANDED_PROPORTION;
    private int unread;

    private ShellChat(JSplitPane split, JComponent chatSide) {
        this.split = split;
        this.chatSide = chatSide;
        this.toggle = new ChatToggleButton();
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

        // keep the collapsed width pinned when the window is resized
        split.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (collapsed) {
                    applyCollapsedDivider();
                }
            }
        });
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
            applyCollapsedDivider();
        } else {
            collapsed = false;
            chatSide.setVisible(true);
            split.setDividerLocation(expandedProportion);
            clearUnread();
        }
        toggle.refresh();
    }

    private void applyCollapsedDivider() {
        SwingUtilities.invokeLater(() -> {
            int loc = split.getWidth() - STRIP_WIDTH - split.getDividerSize();
            if (loc > 0) {
                split.setDividerLocation(loc);
            }
        });
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
     * Thin vertical strip that toggles the chat and paints an unread badge. Kept self-contained so
     * it relies on no upstream rendering code.
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
                    g2.setColor(accent);
                    g2.fillOval(bx, by, dia, dia);

                    String label = unread > 9 ? "9+" : Integer.toString(unread);
                    g2.setColor(Color.WHITE);
                    Font f = getFont().deriveFont(Font.BOLD, 9f);
                    g2.setFont(f);
                    FontMetrics fm = g2.getFontMetrics();
                    int tx = bx + (dia - fm.stringWidth(label)) / 2;
                    int ty = by + (dia - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(label, tx, ty);
                }
            } finally {
                g2.dispose();
            }
        }
    }
}
