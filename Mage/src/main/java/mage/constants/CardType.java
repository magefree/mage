package main.java.mage.constants;

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

}
