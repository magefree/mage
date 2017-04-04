package mage.constants;

import java.util.HashSet;

/**
 *
 * @author North
 */
public enum CardType {
    ARTIFACT ("Artifact"),
    CONSPIRACY ("Conspiracy"),
    CREATURE ("Creature"),
    ENCHANTMENT ("Enchantment"),
    INSTANT ("Instant"),
    LAND ("Land"),
    PLANESWALKER ("Planeswalker"),
    SORCERY ("Sorcery"),
    TRIBAL ("Tribal");

    private final String text;

    CardType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Returns all of the card types from two lists of card types.
     * Duplicates are eliminated.
     */
    public static CardType[] mergeTypes(CardType[] a, CardType[] b) {
        HashSet<CardType> cardTypes = new HashSet<>();
        for (CardType t: a) {
            cardTypes.add(t);
        }
        for (CardType t: b) {
            cardTypes.add(t);
        }
        return cardTypes.toArray(new CardType[0]);
    }

}
