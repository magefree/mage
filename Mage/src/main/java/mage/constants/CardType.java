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
    ARTIFACT("Artifact", "artifacts", true, true),
    CONSPIRACY("Conspiracy", "conspiracies", false, false),
    CREATURE("Creature", "creatures", true, true),
    DUNGEON("Dungeon", "dungeons", false, false),
    ENCHANTMENT("Enchantment", "enchantments", true, true),
    INSTANT("Instant", "instants", false, true),
    LAND("Land", "lands", true, true),
    PHENOMENON("Phenomenon", "phenomena", false, false),
    PLANE("Plane", "planes", false, false),
    PLANESWALKER("Planeswalker", "planeswalkers", true, true),
    SCHEME("Scheme", "schemes", false, false),
    SORCERY("Sorcery", "sorceries", false, true),
    TRIBAL("Tribal", "tribal", false, false),
    VANGUARD("Vanguard", "vanguards", false, false);

    private final String text;
    private final String plural;
    private final boolean permanentType;
    private final boolean includeInSearch; // types that can be searched/filtered by Deck Editor
    private final CardTypePredicate predicate;

    CardType(String text, String plural, boolean permanentType, boolean includeInSearch) {
        this.text = text;
        this.plural = plural;
        this.permanentType = permanentType;
        this.includeInSearch = includeInSearch;
        this.predicate = new CardTypePredicate(this);
    }

    @Override
    public String toString() {
        return text;
    }

    public String getPlural() {
        return plural;
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
