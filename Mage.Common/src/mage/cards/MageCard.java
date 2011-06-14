package mage.cards;

import java.awt.Image;

import javax.swing.JPanel;

import mage.view.CardView;

public abstract class MageCard extends JPanel {
	private static final long serialVersionUID = 6089945326434301879L;

	abstract public void onBeginAnimation();
	abstract public void onEndAnimation();
	abstract public boolean isTapped();
	abstract public void setAlpha(float transparency);
	abstract public float getAlpha();
	abstract public CardView getOriginal();
	abstract public void setCardBounds(int x, int y, int width, int height);
	abstract public void update(CardView card);
	abstract public Image getImage();
    abstract public void setFoil(boolean foil);
    abstract public boolean isFoil();
}
