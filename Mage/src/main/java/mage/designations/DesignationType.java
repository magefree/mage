package mage.designations;

/**
 * @author LevelX2
 */
public enum DesignationType {
    THE_MONARCH("The Monarch"), // global
    CITYS_BLESSING("City's Blessing"), // per player
    THE_INITIATIVE("The Initiative"), // global
    SPEED("Speed"), // per player
    ENDURING_STORY("Enduring Story"); // per player
    private final String text;

    DesignationType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
