package mage.client.components;

import mage.cards.action.TransferData;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.VirtualCardInfo;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.GamePanel;
import mage.game.command.Dungeon;
import mage.game.command.Plane;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.view.CardView;
import mage.view.DungeonView;
import mage.view.PlaneView;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.*;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * GUI: improved editor pane with html, hyperlinks/popup support
 * <p>
 * Can be used as:
 * - read only text panel (example: chats and game logs)
 * - read only text label (example: header messages in choice dialogs)
 * <p>
 * Call in form's constructor or show dialog:
 * - xxx.enableTextLabelMode to enable text label mode
 * - xxx.enableHyperlinksAndCardPopups + xxx.setGameData to enable card popup support
 *
 * @author JayDi85
 */
public class MageEditorPane extends JEditorPane {

    private static final int CHAT_TOOLTIP_DELAY_MS = 50; // cards popup from chat must be fast all time

    private Element lastUrlElementEntered = null; // for cursor changes, element of this pane's document only

    final HTMLEditorKit kit = new HTMLEditorKit();
    final HTMLDocument doc;

    public MageEditorPane() {
        super();
        // merge with UI.setHTMLEditorKit
        this.setEditorKit(kit);
        this.doc = (HTMLDocument) this.getDocument(); // HTMLEditorKit must create HTMLDocument, os use it here

        // improved style: browser's url style with underline on mouse over and hand cursor
        kit.getStyleSheet().addRule(" a { text-decoration: none; } ");

        changeGUISize(this.getFont());
    }

    public void changeGUISize(Font font) {
        this.setFont(font);

        // workaround to change editor's font at runtime
        String bodyRule = "body { "
                + " font-family: " + font.getFamily() + "; "
                + " font-size: " + font.getSize() + "pt; "
                + "}";
        kit.getStyleSheet().addRule(bodyRule);
    }

    /**
     * Simulate JLabel (non-editable and transparent background)
     */
    public void enableTextLabelMode() {
        this.setOpaque(false);
        this.setFocusable(false);
        this.setBorder(null);
        this.setAutoscrolls(false);
        this.setBackground(new Color(0, 0, 0, 0)); // transparent background
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
        setCursorToDefault();
    }

    private void addHyperlinkHandlers() {
        if (Arrays.stream(getHyperlinkListeners()).findAny().isPresent()) {
            throw new IllegalStateException("Wrong code usage: popup links support enabled already");
        }
        addHyperlinkListener(e -> {
            // hyperlink events come from mouse events in the swing thread, so the document is used here, in the swing
            // thread only - a document change from another thread breaks the text views on the next layout
            // (StateInvariantError: GlyphView: Stale view, ArrayIndexOutOfBoundsException in FlowView/BoxView)
            if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_DELAY, 300) == 0) {
                // if disabled
                return;
            }

            Map<String, String> extraData = getExtraData(e.getSourceElement());

            if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                AttributeSet as = e.getSourceElement().getAttributes();
                AttributeSet asAnchor = (AttributeSet) as.getAttribute(HTML.Tag.A);
                if (asAnchor != null) {
                    urlHighlightEnable(e.getSourceElement());
                }
            }

            if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
                urlHighlightDisable();
            }

            // card search can be slow (database), so popup data is prepared outside the swing thread
            MageUI.threadPoolPopups.submit(() -> showCardPopup(e, extraData));
        });

        addMouseListener(new MouseAdapter() {
            // TODO: add popup mouse wheel here?
            @Override
            public void mouseExited(MouseEvent e) {
                cardInfo.onMouseExited();
                setCursorToDefault();
            }
        });
    }

    /**
     * Find extra data in html element like object_id, alternative_name, etc
     */
    private static Map<String, String> getExtraData(Element sourceElement) {
        Map<String, String> extraData = new HashMap<>();
        if (sourceElement instanceof HTMLDocument.RunElement) {
            HTMLDocument.RunElement el = (HTMLDocument.RunElement) sourceElement;
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
        return extraData;
    }

    /**
     * Card popup for hyperlink events, must be called outside the swing thread (can search cards in the database)
     */
    private void showCardPopup(HyperlinkEvent e, Map<String, String> extraData) {
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
            // show real object by priority (workable card hints and actual info)
            CardView cardView = needCard;

            // if no game object found then show default card/object
            if (cardView == null) {
                CardInfo card = CardRepository.instance.findCards(cardName).stream().findFirst().orElse(null);
                if (card != null) {
                    cardView = new CardView(card.createMockCard());
                }
            }

            // plane
            if (cardView == null) {
                Plane plane = Plane.createPlaneByFullName(cardName);
                if (plane != null) {
                    cardView = new CardView(new PlaneView(plane, null));
                }
            }

            // dungeon
            if (cardView == null) {
                Dungeon dungeon = Dungeon.createDungeon(cardName, false);
                if (dungeon != null) {
                    cardView = new CardView(new DungeonView(dungeon));
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
    }

    private void setCursorToDefault() {
        Window parent = SwingUtilities.windowForComponent(this);
        if (parent != null) {
            parent.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void setCursorToHand() {
        Window parent = SwingUtilities.windowForComponent(this);
        if (parent != null) {
            parent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
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
        setCursorToDefault();
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
        // old elements are removed with the text, so the highlighted one must be forgotten
        lastUrlElementEntered = null;
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
