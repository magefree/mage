package mage.utils;

import mage.Constants.CardType;
import mage.cards.Card;
import mage.cards.MagePermanent;
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

    public static boolean isLand(CardView card) {
        return is(card, CardType.LAND);
    }

    public static boolean isCreature(MagePermanent card) {
        return is(card.getOriginal(), CardType.CREATURE);
    }

    public static boolean isPlaneswalker(MagePermanent card) {
        return is(card.getOriginal(), CardType.PLANESWALKER);
    }

    public static boolean isLand(MagePermanent card) {
        return is(card.getOriginal(), CardType.LAND);
    }

    public static boolean is(CardView card, CardType type) {
        return card.getCardTypes().contains(type);
    }

    public static boolean isBasicLand(Card card) {
        return card.getSupertype().contains("Basic");
    }

    public static boolean isLand(Card card) {
        return card.getCardType().contains(CardType.LAND);
    }
}
