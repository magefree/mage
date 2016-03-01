package mage.client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.view.CardView;

/**
 * Enhanced {@link JTextPane} with text highlighting support.
 *
 * @author nantuko
 */
public class ColorPane extends JEditorPane {

    HTMLEditorKit kit = new HTMLEditorKit();
    HTMLDocument doc = new HTMLDocument();
    private int tooltipDelay;

    public ColorPane() {
        this.setEditorKit(kit);
        this.setDocument(doc);
        addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(final HyperlinkEvent e) {
                tooltipDelay = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_DELAY, 300);
                if (tooltipDelay == 0) {
                    return;
                }
                String name = e.getDescription().substring(1);
                CardInfo card = CardRepository.instance.findCard(name);
                try {
                    final Component container = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
                    if (e.getEventType() == EventType.EXITED) {
                        setPopupVisibility(container, false);
                    } else {
                        CardInfoPane cardInfoPane = (CardInfoPane) MageFrame.getUI().getComponent(MageComponents.CARD_INFO_PANE);
                        cardInfoPane.setCard(new CardView(card.getMockCard()), container);
                        Point location = new Point(getLocationOnScreen().x - container.getWidth(), (int) MageFrame.getDesktop()
                                .getMousePosition().getY());
                        Component parentComponent = MageFrame.getInstance();
                        location = GuiDisplayUtil.keepComponentInsideParent(location, parentComponent.getLocationOnScreen(), container,
                                parentComponent);
                        container.setLocation(location);
                        setPopupVisibility(container, true);
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            private void setPopupVisibility(final Component container, final boolean show) throws InterruptedException {
                final Component c = MageFrame.getUI().getComponent(MageComponents.DESKTOP_PANE);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        container.setVisible(show);
                        c.repaint();
                    }
                });
            }
        });
    }

    /**
     * This method solves the known issue with Nimbus LAF background
     * transparency and background color.
     *
     * @param color
     */
    public void setExtBackgroundColor(Color color) {
        setBackground(new Color(0, 0, 0, 0));
        JPanel jPanel = new JPanel();
        jPanel.setBackground(color);
        setLayout(new BorderLayout());
        add(jPanel);
    }

    @Override
    public void setText(String string) {
        super.setText(string); //To change body of generated methods, choose Tools | Templates.
    }

    public void append(String text) {
        try {
            text = text.replaceAll("(<font color=[^>]*>([^<]*)) (\\[[0-9a-fA-F]*\\])</font>", "<a href='#$2'>$1</a> $3");
            setEditable(true);
            kit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
            setEditable(false);
            int len = getDocument().getLength();
            setCaretPosition(len);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A little trick to paint black background under the text.
     *
     * @param g
     */
    @Override
    public void paintChildren(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * A little trick to paint black background under the text.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintChildren(g);
    }

}
