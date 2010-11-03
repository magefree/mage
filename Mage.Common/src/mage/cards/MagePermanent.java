package mage.cards;

import java.util.List;

import javax.swing.JPanel;

import mage.cards.interfaces.PermanentInterface;
import mage.view.PermanentView;

public abstract class MagePermanent extends JPanel implements PermanentInterface {
	private static final long serialVersionUID = -3469258620601702171L;
	abstract public List<MagePermanent> getLinks();
	
	abstract public void onBeginAnimation();
	abstract public void onEndAnimation();
	abstract public boolean isTapped();
	abstract public void setAlpha(float transparency);
	abstract public PermanentView getOriginal();
	abstract public void setCardBounds(int x, int y, int width, int height);
}
