package mage.constants;

/**
 *
 * @author North
 */
public enum SetType {
    CORE("Core"),
    EXPANSION("Expansion"),
    REPRINT("Reprint");

    private String text;

    SetType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
