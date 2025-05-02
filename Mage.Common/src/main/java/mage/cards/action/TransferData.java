package mage.cards.action;

import mage.cards.MageCard;
import mage.cards.TextPopup;
import mage.view.CardView;

import java.awt.*;
import java.util.UUID;

/**
 * Data for main card panel events like mouse moves or clicks
 *
 */
public class TransferData {

    private MageCard component; // real card panel (it may lie under multiple layer panels, so use getTopPanelRef for top)
    private TextPopup popupText;
    private Point locationOnScreen; // must contain REAL card location (e.g. without outer/draw spaces), so use getCardLocationOnScreen to update it
    private int popupOffsetX;
    private int popupOffsetY;
    private PopupAutoLocationMode popupAutoLocationMode = PopupAutoLocationMode.PUT_INSIDE_PARENT;
    private UUID gameId;
    private CardView card;
    private int tooltipDelay; // custom delay, set non-zero to overwrite preferences settings

    public enum PopupAutoLocationMode {
        PUT_INSIDE_PARENT,
        PUT_NEAR_MOUSE_POSITION
    }

    /**
     * If you use it with cards then call top layer panel like data.getComponent().getTopPanelRef()
     *
     * @return
     */
    public MageCard getComponent() {
        return component;
    }

    public void setComponent(MageCard component) {
        this.component = component;
    }

    public TextPopup getPopupText() {
        return popupText;
    }

    public void setPopupText(TextPopup popupText) {
        this.popupText = popupText;
    }

    public Point getLocationOnScreen() {
        return locationOnScreen;
    }

    public void setLocationOnScreen(Point locationOnScreen) {
        this.locationOnScreen = locationOnScreen;
    }

    public int getPopupOffsetX() {
        return popupOffsetX;
    }

    public void setPopupOffsetX(int popupOffsetX) {
        this.popupOffsetX = popupOffsetX;
    }

    public int getPopupOffsetY() {
        return popupOffsetY;
    }

    public void setPopupOffsetY(int popupOffsetY) {
        this.popupOffsetY = popupOffsetY;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public CardView getCard() {
        return card;
    }

    public void setCard(CardView card) {
        this.card = card;
    }

    public int getTooltipDelay() {
        return tooltipDelay;
    }

    public void setTooltipDelay(int tooltipDelay) {
        this.tooltipDelay = tooltipDelay;
    }

    public void setPopupAutoLocationMode(PopupAutoLocationMode mode) {
        this.popupAutoLocationMode = mode;
    }

    public PopupAutoLocationMode getPopupAutoLocationMode() {
        return this.popupAutoLocationMode;
    }
}
