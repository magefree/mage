package mage.cards;

import mage.cards.action.ActionCallback;
import mage.view.CardView;

import java.awt.Image;
import java.util.UUID;
import javax.swing.JPanel;

public abstract class MageCard extends JPanel {
    private static final long serialVersionUID = 6089945326434301879L;

    public abstract void onBeginAnimation();
    public abstract void onEndAnimation();
    public abstract boolean isTapped();
    public abstract boolean isFlipped();
    public abstract void setAlpha(float transparency);
    public abstract float getAlpha();
    public abstract CardView getOriginal();
    public abstract void setCardBounds(int x, int y, int width, int height);
    public abstract void update(CardView card);
    public abstract void updateImage();
    public abstract Image getImage();
    public abstract void setFoil(boolean foil);
    public abstract boolean isFoil();
    public abstract void setZone(String zone);
    public abstract String getZone();
    public abstract void updateCallback(ActionCallback callback, UUID gameId);
    public abstract void toggleTransformed();
    public abstract boolean isTransformed();
    public abstract void showCardTitle();
    public abstract void setSelected(boolean selected);
}
