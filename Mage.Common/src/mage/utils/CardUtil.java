package mage.utils;

import mage.Constants.CardType;
import mage.view.CardView;

/**
 * Utility class for {@link CardView}
 *
 * @version 0.1 02.11.2010
 * @author nantuko
 */
public class CardUtil {
	
	public static boolean isCreature(CardView card) {
		return is(card, CardType.CREATURE);
	}
	
	public static boolean isPlaneswalker(CardView card) {
		return is(card, CardType.PLANESWALKER);
	}
	
	public static boolean is(CardView card, CardType type) {
		return card.getCardTypes().contains(type);
	}
}
