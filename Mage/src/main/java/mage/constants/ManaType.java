package mage.constants;

/**
 *
 * @author North
 */
public enum ManaType {

    BLACK("black"),
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    WHITE("white"),
    GENERIC("generic"),
    COLORLESS("colorless");

    private final String text;

    ManaType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
