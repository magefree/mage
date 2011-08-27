package mage.cards;

import java.awt.Image;
import java.util.UUID;

import javax.swing.JPanel;

import mage.cards.action.ActionCallback;
import mage.view.CardView;

public abstract class MageCard extends JPanel {
	private static final long serialVersionUID = 6089945326434301879L;

	abstract public void onBeginAnimation();
	abstract public void onEndAnimation();
	abstract public boolean isTapped();
	abstract public boolean isFlipped();
	abstract public void setAlpha(float transparency);
	abstract public float getAlpha();
	abstract public CardView getOriginal();
	abstract public void setCardBounds(int x, int y, int width, int height);
	abstract public void update(CardView card);
	abstract public void updateImage();
	abstract public Image getImage();
    abstract public void setFoil(boolean foil);
    abstract public boolean isFoil();
    abstract public void setZone(String zone);
    abstract public String getZone();
	abstract public void updateCallback(ActionCallback callback, UUID gameId);
}
