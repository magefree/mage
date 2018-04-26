package mage.constants;

/**
 *
 * @author North
 */
public enum SetType {
    EXPANSION("Expansion"),
    CORE("Core"),
    MAGIC_ONLINE("Magic Online"),
    SUPPLEMENTAL("Supplemental"),
    SUPPLEMENTAL_STANDARD_LEGAL("Standard Legal Supplemental"),
    PROMOTIONAL("Promotional"),
    JOKESET("Joke Set"),
    CUSTOM_SET("Unofficial Set");

    private final String text;

    SetType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
