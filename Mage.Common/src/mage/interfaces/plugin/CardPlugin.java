package mage.interfaces.plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.JComponent;

import mage.cards.Card;
import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.Plugin;

/**
 * Interface for card plugins
 * 
 * @version 0.3 21.11.2010 #getMageCard
 * @version 0.2 07.11.2010 #downloadImages
 * @version 0.1 31.10.2010 #getMagePermanent, #sortPermanents
 * @author nantuko
 */
public interface CardPlugin extends Plugin {
	MagePermanent getMagePermanent(PermanentView permanent, CardDimensions dimension, UUID gameId, ActionCallback callback);
	MagePermanent getMageCard(CardView permanent, CardDimensions dimension, UUID gameId, ActionCallback callback);
	void sortPermanents(Map<String, JComponent> ui, Collection<MagePermanent> cards);
	void downloadImages(Set<Card> allCards);
}
