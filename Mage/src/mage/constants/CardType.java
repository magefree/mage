package mage.constants;

/**
 *
 * @author North
 */
public enum CardType {
    ARTIFACT ("Artifact"),
    CREATURE ("Creature"),
    ENCHANTMENT ("Enchantment"),
    INSTANT ("Instant"),
    LAND ("Land"),
    PLANESWALKER ("Planeswalker"),
    SORCERY ("Sorcery"),
    TRIBAL ("Tribal");

    private String text;

    CardType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
