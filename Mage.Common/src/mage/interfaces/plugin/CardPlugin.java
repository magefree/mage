package mage.interfaces.plugin;

import java.util.UUID;

import mage.cards.CardDimensions;
import mage.cards.MagePermanent;
import mage.view.CardView;
import net.xeoh.plugins.base.Plugin;

/**
 * Interface for card plugins
 * 
 * @version 0.1 31.10.2010
 * @author nantuko
 */
public interface CardPlugin extends Plugin {
	MagePermanent getMagePermanent(CardView card, CardDimensions dimension, UUID gameId);
}
