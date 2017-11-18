package mage.cards;

import java.awt.Image;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import mage.cards.action.ActionCallback;
import mage.view.CardView;

public abstract class MageCard extends JPanel {

    private static final long serialVersionUID = 6089945326434301879L;

    public abstract void onBeginAnimation();

    public abstract void onEndAnimation();

    public abstract boolean isTapped();

    public abstract boolean isFlipped();

    public abstract void setAlpha(float transparency);

    public abstract float getAlpha();

    public abstract CardView getOriginal();

    // sets the vertical text offset for the card name on the image, use to move caption to card center
    public abstract void setCardCaptionTopOffset(int yOffsetPercent);

    public abstract void setCardBounds(int x, int y, int width, int height);

    public abstract void update(CardView card);

    public abstract void updateArtImage();

    public abstract Image getImage();

    public abstract void setZone(String zone);

    public abstract String getZone();

    public abstract void updateCallback(ActionCallback callback, UUID gameId);

    public abstract void toggleTransformed();

    public abstract boolean isTransformed();

    public abstract void showCardTitle();

    public abstract void setSelected(boolean selected);

    public abstract void setCardAreaRef(JPanel cardArea);

    public abstract void setChoosable(boolean isChoosable);

    public abstract void setPopupMenu(JPopupMenu popupMenu);

    public abstract JPopupMenu getPopupMenu();

}
