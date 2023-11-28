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
import javax.swing.text.*;
import javax.swing.text.html.*;
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
    private static Element lastUrlElementEntered = null; // for cursor changes

    final HTMLEditorKit kit = new HTMLEditorKit();
    final HTMLDocument doc;


    MageEditorPane() {
        super();
        // merge with UI.setHTMLEditorKit
        this.setEditorKit(kit);
        this.doc = (HTMLDocument) this.getDocument(); // HTMLEditorKit must create HTMLDocument, os use it here

        // improved style: browser's url style with underline on mouse over and hand cursor
        kit.getStyleSheet().addRule(" a { text-decoration: none; } ");
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

    public void cleanUp() {
        resetCursor();
    }

    private void addHyperlinkHandlers() {
        if (Arrays.stream(getHyperlinkListeners()).findAny().isPresent()) {
            throw new IllegalStateException("Wrong code usage: popup links support enabled already");
        }
        addHyperlinkListener(e -> MageUI.threadPoolPopups.submit(() -> {
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
                try {
                    UUID needObjectId = UUID.fromString(extraData.getOrDefault("object_id", ""));
                    needCard = gamePanel.getLastGameData().findCard(needObjectId);
                } catch (IllegalArgumentException ignore) {
                }
            }

            String cardName = e.getDescription().substring(1);
            String alternativeName = CardUtil.urlDecode(extraData.getOrDefault("alternative_name", ""));
            if (!alternativeName.isEmpty()) {
                cardName = alternativeName;
            }

            if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                AttributeSet as = e.getSourceElement().getAttributes();
                AttributeSet asAnchor = (AttributeSet) as.getAttribute(HTML.Tag.A);
                if (asAnchor != null) {
                    urlHighlightEnable(e.getSourceElement());
                }

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
                urlHighlightDisable();

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
                resetCursor();
            }
        });
    }

    private void resetCursor() {
        SwingUtilities.windowForComponent(this).setCursor(Cursor.getDefaultCursor());
    }

    private void setCursorToHand() {
        SwingUtilities.windowForComponent(this).setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void urlHighlightEnable(Element hyperlinkElement) {
        if (hyperlinkElement != lastUrlElementEntered) {
            lastUrlElementEntered = hyperlinkElement;
            changeUrlTextDecoration(hyperlinkElement, "underline");
        }
        setCursorToHand();
    }

    private void urlHighlightDisable() {
        if (lastUrlElementEntered != null) {
            changeUrlTextDecoration(lastUrlElementEntered, "none");
            lastUrlElementEntered = null;
        }
        resetCursor();
    }

    private void changeUrlTextDecoration(Element el, String decoration) {
        if (lastUrlElementEntered != null) {
            HTMLDocument doc = (HTMLDocument) this.getDocument();
            int start = el.getStartOffset();
            int end = el.getEndOffset();
            StyleContext ss = doc.getStyleSheet();
            Style style = ss.addStyle("HighlightedUrl", null);
            style.addAttribute(CSS.Attribute.TEXT_DECORATION, decoration);
            doc.setCharacterAttributes(start, end - start, style, false);
        }
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
        if (this.isEditable()) {
            throw new IllegalStateException("Wrong code usage: hyper links works with non-editable components");
        }

        hyperlinkEnabled = true;
        addHyperlinkHandlers();
    }


}
