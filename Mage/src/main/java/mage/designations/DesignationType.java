
package mage.designations;

/**
 *
 * @author LevelX2
 */
public enum DesignationType {
    THE_MONARCH("The Monarch"),
    CITYS_BLESSING("City's Blessing");

    private final String text;

    DesignationType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
