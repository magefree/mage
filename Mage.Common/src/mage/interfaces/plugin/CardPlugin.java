package mage.interfaces.plugin;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.swing.JComponent;

import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.cards.interfaces.ActionCallback;
import mage.view.PermanentView;
import net.xeoh.plugins.base.Plugin;

/**
 * Interface for card plugins
 * 
 * @version 0.1 31.10.2010
 * @author nantuko
 */
public interface CardPlugin extends Plugin {
	MagePermanent getMagePermanent(PermanentView permanent, CardDimensions dimension, UUID gameId, ActionCallback callback);
	void sortPermanents(Map<String, JComponent> ui, Collection<MagePermanent> cards);
}
