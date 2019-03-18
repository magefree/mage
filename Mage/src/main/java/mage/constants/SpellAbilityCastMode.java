
package mage.constants;

/**
 *
 * @author LevelX2
 */
public enum SpellAbilityCastMode {
    NORMAL("Normal"),
    MADNESS("Madness"),
    FLASHBACK("Flashback");

    private final String text;

    SpellAbilityCastMode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
