package mage.constants;

/**
 *
 * @author North
 */
public enum AbilityType {
    PLAY_LAND("Play land"),
    MANA("Mana"),
    SPELL("Spell"),
    ACTIVATED("Activated"),
    STATIC("Static"),
    TRIGGERED("Triggered"),
    EVASION("Evasion"),
    LOYALTY("Loyalty"),
    SPECIAL_ACTION("Special Action");

    private String text;

    AbilityType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
