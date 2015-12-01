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
    PROMOTIONAL("Promotional"),
    JOKESET("Joke Set");

    private final String text;

    SetType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
