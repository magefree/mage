package mage.constants;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author North
 */
public enum CardType {
    ARTIFACT("Artifact", true, true),
    BATTLE("Battle", true, true),
    CONSPIRACY("Conspiracy", false, false),
    CREATURE("Creature", true, true),
    DUNGEON("Dungeon", false, false),
    ENCHANTMENT("Enchantment", true, true),
    INSTANT("Instant", false, true),
    LAND("Land", true, true),
    PHENOMENON("Phenomenon", false, false),
    PLANE("Plane", false, false),
    PLANESWALKER("Planeswalker", true, true),
    SCHEME("Scheme", false, false),
    SORCERY("Sorcery", false, true),
    TRIBAL("Tribal", false, false),
    VANGUARD("Vanguard", false, false);

    private final String text;
    private final boolean permanentType;
    private final boolean includeInSearch; // types that can be searched/filtered by Deck Editor
    private final CardTypePredicate predicate;

    CardType(String text, boolean permanentType, boolean includeInSearch) {
        this.text = text;
        this.permanentType = permanentType;
        this.includeInSearch = includeInSearch;
        this.predicate = new CardTypePredicate(this);
    }

    @Override
    public String toString() {
        return text;
    }

    public String getPluralName() {
        return text.endsWith("y") ? text.substring(0, text.length() - 1) + "ies" : text + 's';
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

    public boolean isIncludeInSearch() {
        return includeInSearch;
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
        List<CardType> cardTypes = new ArrayList<>();
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
            return input.getCardType(game).contains(cardType);
        }

        @Override
        public String toString() {
            return "CardType(" + cardType.toString() + ')';
        }
    }
}
