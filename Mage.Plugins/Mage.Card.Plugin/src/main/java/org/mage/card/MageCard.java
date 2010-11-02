package org.mage.card;

public abstract class MageCard {
	abstract public void onBeginAnimation();
	abstract public void onEndAnimation();
	abstract public void repaint();
	abstract public boolean isTapped();
	abstract public void setTransparency(double transparency);
}