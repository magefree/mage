package mage.cards.action;

import java.awt.Component;
import java.awt.Point;
import java.util.UUID;

import mage.cards.TextPopup;
import mage.view.CardView;

public class TransferData {
    public Component component;
    public TextPopup popupText;
    public Point locationOnScreen;
    public int popupOffsetX;
    public int popupOffsetY;
    public UUID gameId;
    public CardView card;
}
