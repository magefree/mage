package mage.cards;

import java.awt.Image;
import java.util.List;

import javax.swing.JPanel;

import mage.view.PermanentView;

public abstract class MagePermanent extends JPanel {
	private static final long serialVersionUID = -3469258620601702171L;
	abstract public List<MagePermanent> getLinks();
	
	abstract public void onBeginAnimation();
	abstract public void onEndAnimation();
	abstract public boolean isTapped();
	abstract public void setAlpha(float transparency);
	abstract public PermanentView getOriginal();
	abstract public void setCardBounds(int x, int y, int width, int height);
	abstract public void update(PermanentView card);
	abstract public Image getImage();
}
