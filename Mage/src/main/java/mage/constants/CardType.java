package mage.constants;

import java.util.Arrays;
import java.util.EnumSet;

/**
 *
 * @author North
 */
public enum CardType {
    ARTIFACT("Artifact", true),
    CONSPIRACY("Conspiracy", false),
    CREATURE("Creature", true),
    ENCHANTMENT("Enchantment", true),
    INSTANT("Instant", false),
    LAND("Land", true),
    PLANESWALKER("Planeswalker", true),
    SORCERY("Sorcery", false),
    TRIBAL("Tribal", false);

    private final String text;
    private final boolean permanentType;

    CardType(String text, boolean permanentType) {
        this.text = text;
        this.permanentType = permanentType;
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
        EnumSet<CardType> cardTypes = EnumSet.noneOf(CardType.class);
        cardTypes.addAll(Arrays.asList(a));
        cardTypes.addAll(Arrays.asList(b));
        return cardTypes.toArray(new CardType[0]);
    }

}
