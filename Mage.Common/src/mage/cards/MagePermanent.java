package mage.cards;

import java.util.List;

import javax.swing.JPanel;

import mage.cards.interfaces.CardInterface;
import mage.cards.interfaces.PermanentInterface;

public abstract class MagePermanent extends JPanel implements PermanentInterface, CardInterface {
	private static final long serialVersionUID = -3469258620601702171L;
	abstract public List<MagePermanent> getLinks();
}
