package mage.constants;

/**
 *
 * @author North
 */
public enum ManaType {
    BLACK ("black"),
    BLUE  ("blue"),
    GREEN ("greeen"),
    RED   ("red"),
    WHITE ("white"),
    COLORLESS("colorless");

    private String text;

    ManaType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
};