package mage.client.components;

import mage.cards.action.TransferData;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.VirtualCardInfo;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.GamePanel;
import mage.game.command.Plane;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import mage.view.PlaneView;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * GUI: improved editor pane with html, hyperlinks/popup support
 *
 * @author JayDi85
 */
public class MageEditorPane extends JEditorPane {

    private static final int CHAT_TOOLTIP_DELAY_MS = 50; // cards popup from chat must be fast all time
    final HTMLEditorKit kit = new HTMLEditorKit();
    final HTMLDocument doc;

    MageEditorPane() {
        super();
        // merge with UI.setHTMLEditorKit
        this.setEditorKit(kit);
        this.doc = (HTMLDocument) this.getDocument(); // HTMLEditorKit must creates HTMLDocument, os use it here
    }

    // cards popup info
    private boolean hyperlinkEnabled = false;
    VirtualCardInfo cardInfo = new VirtualCardInfo();
    UUID gameId = null;
    BigCard bigCard = null;

    public void setGameData(UUID gameId, BigCard bigCard) {
        this.gameId = gameId;
        this.bigCard = bigCard;
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

            // try object first
            CardView needCard = null;
            GamePanel gamePanel = MageFrame.getGame(this.gameId);
            if (gamePanel != null) {
                UUID needObjectId = UUID.fromString(extraData.getOrDefault("object_id", ""));
                needCard = gamePanel.getLastGameData().findCard(needObjectId);
            }

            String cardName = e.getDescription().substring(1);
            String alternativeName = CardUtil.urlDecode(extraData.getOrDefault("alternative_name", ""));
            if (!alternativeName.isEmpty()) {
                cardName = alternativeName;
            }

            if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                // show real object by priority (workable card hints and actual info)
                CardView cardView = needCard;

                // if no game object found then show default card
                if (cardView == null) {
                    CardInfo card = CardRepository.instance.findCards(cardName).stream().findFirst().orElse(null);
                    if (card != null) {
                        cardView = new CardView(card.getMockCard());
                    }
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

            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                SwingUtilities.invokeLater(() -> {
                    cardInfo.onMouseWheel(MouseInfo.getPointerInfo().getLocation());
                });
            }

            if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
                SwingUtilities.invokeLater(() -> {
                    cardInfo.onMouseExited();
                });
            }
        }));

        addMouseListener(new MouseAdapter() {
            // TODO: add popup mouse wheel here?
            @Override
            public void mouseExited(MouseEvent e) {
                cardInfo.onMouseExited();
            }
        });
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

    public void enableHyperlinksAndCardPopups() {
        hyperlinkEnabled = true;
        addHyperlinkHandlers();
    }
}
