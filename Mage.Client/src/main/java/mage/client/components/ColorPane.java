package mage.client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.utils.ThreadUtils;
import mage.view.CardView;

/**
 * Enhanced {@link JTextPane} with text highlighting support.
 *
 * @author nantuko
 */
public class ColorPane extends JEditorPane {

    final HTMLEditorKit kit = new HTMLEditorKit();
    final HTMLDocument doc = new HTMLDocument();
    private int tooltipDelay;
    private int tooltipCounter;
    private boolean hyperlinkEnabled = false;

    public ColorPane() {
        this.setEditorKit(kit);
        this.setDocument(doc);
    }

    private void addHyperlinkHandlers() {
        addHyperlinkListener(e -> ThreadUtils.threadPool2.submit(() -> {
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
                }
                if (e.getEventType() == EventType.ENTERED && card != null) {
                    CardInfoPane cardInfoPane = (CardInfoPane) MageFrame.getUI().getComponent(MageComponents.CARD_INFO_PANE);
                    cardInfoPane.setCard(new CardView(card.getMockCard()), container);
                    setPopupVisibility(container, true);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                tooltipCounter = 1; // will decrement and become effectively zero on leaving the pane
                try {
                    setPopupVisibility(MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER), false);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void setPopupVisibility(final Component container, final boolean show) throws InterruptedException {
        final Component c = MageFrame.getUI().getComponent(MageComponents.DESKTOP_PANE);
        SwingUtilities.invokeLater(() -> {
            tooltipCounter += show ? 1 : -1;
            if (tooltipCounter < 0) {
                tooltipCounter = 0;
            }
            if (tooltipCounter > 0) {
                Point location = new Point(getLocationOnScreen().x - container.getWidth(), MouseInfo.getPointerInfo().getLocation().y);
                Component parentComponent = MageFrame.getInstance();
                location = GuiDisplayUtil.keepComponentInsideParent(location, parentComponent.getLocationOnScreen(), container, parentComponent);
                container.setLocation(location);
            }
            container.setVisible(tooltipCounter > 0);
            c.repaint();
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
            if (hyperlinkEnabled) {
                text = text.replaceAll("(<font color=[^>]*>([^<]*)) (\\[[0-9a-fA-F]*\\])</font>", "<a href=\"#$2\">$1</a> $3");
            }
            kit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
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

    public void enableHyperlinks(){
        hyperlinkEnabled = true;
        addHyperlinkHandlers();
    }
    
}
