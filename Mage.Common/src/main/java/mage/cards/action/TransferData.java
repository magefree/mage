package mage.cards.action;

import java.awt.Component;
import java.awt.Point;
import java.util.UUID;
import mage.cards.TextPopup;
import mage.view.CardView;

public class TransferData {
    private Component component;
    private TextPopup popupText;
    private Point locationOnScreen;
    private int popupOffsetX;
    private int popupOffsetY;
    private UUID gameId;
    private CardView card;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
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
}
