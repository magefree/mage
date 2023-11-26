package mage.client.components;

import mage.cards.action.TransferData;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.cards.BigCard;
import mage.client.cards.VirtualCardInfo;
import mage.client.dialog.PreferencesDialog;
import mage.game.command.Plane;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import mage.view.PlaneView;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * GUI: chats with html and hints popup support for objects
 *
 * @author nantuko, JayDi85
 */
public class ColorPane extends JEditorPane {

    final HTMLEditorKit kit = new HTMLEditorKit();
    final HTMLDocument doc = new HTMLDocument();

    private static final int CHAT_TOOLTIP_DELAY_MS = 50; // cards popup from chat must be fast all time

    // cards popup info
    private boolean hyperlinkEnabled = false;
    VirtualCardInfo cardInfo = new VirtualCardInfo();
    UUID gameId = null;
    BigCard bigCard = null;


    public ColorPane() {
        this.setEditorKit(kit);
        this.setDocument(doc);
    }

    private void addHyperlinkHandlers() {
        if (Arrays.stream(getHyperlinkListeners()).findAny().isPresent()) {
            throw new IllegalStateException("Wrong code usage: popup links support enabled already");
        }
        addHyperlinkListener(e -> ThreadUtils.threadPoolPopups.submit(() -> {
            if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_DELAY, 300) == 0) {
                // if disabled
                return;
            }

            // finds extra data in html element like object_id, alternative_name, etc
            Map<String, String> extraData = new HashMap<>();
            if (e.getSourceElement() instanceof HTMLDocument.RunElement) {
                HTMLDocument.RunElement el = (HTMLDocument.RunElement) e.getSourceElement();
                Enumeration attNames = el.getAttributeNames();
                while (attNames.hasMoreElements()) {
                    Object attName = attNames.nextElement();
                    Object attValue = el.getAttribute(attName);
                    // custom attributes in SimpleAttributeSet element
                    if (attValue instanceof SimpleAttributeSet) {
                        SimpleAttributeSet attReal = (SimpleAttributeSet) attValue;
                        Enumeration attRealNames = attReal.getAttributeNames();
                        while (attRealNames.hasMoreElements()) {
                            Object attRealName = attRealNames.nextElement();
                            Object attRealValue = attReal.getAttribute(attRealName);
                            String name = attRealName.toString();
                            String value = attRealValue.toString();
                            extraData.put(name, value);
                        }
                    }
                }
            }

            String cardName = e.getDescription().substring(1);
            String alternativeName = CardUtil.urlDecode(extraData.getOrDefault("alternative_name", ""));
            if (!alternativeName.isEmpty()) {
                cardName = alternativeName;
            }

            if (e.getEventType() == EventType.ENTERED) {
                CardView cardView = null;

                // TODO: add real game object first

                // card
                CardInfo card = CardRepository.instance.findCards(cardName).stream().findFirst().orElse(null);
                if (card != null) {
                    cardView = new CardView(card.getMockCard());
                }

                // plane
                if (cardView == null) {
                    Plane plane = Plane.createPlaneByFullName(cardName);
                    if (plane != null) {
                        cardView = new CardView(new PlaneView(plane));
                    }
                }

                // TODO: add other objects like dungeon, emblem, commander

                if (cardView != null) {
                    cardInfo.init(cardView, this.bigCard, this.gameId);
                    cardInfo.setTooltipDelay(CHAT_TOOLTIP_DELAY_MS);
                    cardInfo.setPopupAutoLocationMode(TransferData.PopupAutoLocationMode.PUT_NEAR_MOUSE_POSITION);
                    SwingUtilities.invokeLater(() -> {
                        cardInfo.onMouseEntered(MouseInfo.getPointerInfo().getLocation());
                        cardInfo.onMouseMoved(MouseInfo.getPointerInfo().getLocation());
                    });
                }
            }

            if (e.getEventType() == EventType.ACTIVATED) {
                SwingUtilities.invokeLater(() -> {
                    cardInfo.onMouseWheel(MouseInfo.getPointerInfo().getLocation());
                });
            }

            if (e.getEventType() == EventType.EXITED) {
                SwingUtilities.invokeLater(() -> {
                    cardInfo.onMouseExited();
                });
            }
        }));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                cardInfo.onMouseExited();
            }
        });
    }

    public void setGameData(UUID gameId, BigCard bigCard) {
        this.gameId = gameId;
        this.bigCard = bigCard;
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
    public void setText(String text) {
        // must use append to enable popup/hyperlinks support
        super.setText("");
        append(text);
    }

    public void append(String text) {
        try {
            if (hyperlinkEnabled) {
                text = GameLog.injectPopupSupport(text);
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

    public void enableHyperlinksAndCardPopups() {
        hyperlinkEnabled = true;
        addHyperlinkHandlers();
    }

}
