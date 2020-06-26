package mage.constants;

import java.util.ArrayList;
import java.util.Arrays;
import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author North
 */
public enum CardType {
    ARTIFACT("Artifact", true),
    CONSPIRACY("Conspiracy", false),
    CREATURE("Creature", true),
    ENCHANTMENT("Enchantment", true),
    INSTANT("Instant", false),
    LAND("Land", true),
    PHENOMENON("Phenomenon", false),
    PLANE("Plane", false),
    PLANESWALKER("Planeswalker", true),
    SCHEME("Scheme", false),
    SORCERY("Sorcery", false),
    TRIBAL("Tribal", false),
    VANGUARD("Vanguard", false);

    private final String text;
    private final boolean permanentType;
    private final CardTypePredicate predicate;

    CardType(String text, boolean permanentType) {
        this.text = text;
        this.permanentType = permanentType;
        this.predicate = new CardTypePredicate(this);
    }

    @Override
    public String toString() {
        return text;
    }

    public static CardType fromString(String value) {
        for (CardType ct : CardType.values()) {
            if (ct.toString().equals(value)) {
                return ct;
            }
        }

        throw new IllegalArgumentException("Can't find card type enum value: " + value);
    }

    public boolean isPermanentType() {
        return permanentType;
    }

    /**
     * Returns all of the card types from two lists of card types. Duplicates
     * are eliminated.
     *
     * @param a
     * @param b
     * @return
     */
    public static CardType[] mergeTypes(CardType[] a, CardType[] b) {
        ArrayList<CardType> cardTypes = new ArrayList<>();
        cardTypes.addAll(Arrays.asList(a));
        cardTypes.addAll(Arrays.asList(b));
        return cardTypes.toArray(new CardType[0]);
    }

    public CardTypePredicate getPredicate() {
        return this.predicate;
    }

    public static class CardTypePredicate implements Predicate<MageObject> {

        private final CardType cardType;

        private CardTypePredicate(CardType cardType) {
            this.cardType = cardType;
        }

        @Override
        public boolean apply(MageObject input, Game game) {
            return input.getCardType().contains(cardType);
        }

        @Override
        public String toString() {
            return "CardType(" + cardType.toString() + ')';
        }
    }
}
