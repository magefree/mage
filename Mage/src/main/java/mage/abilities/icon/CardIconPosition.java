package mage.abilities.icon;

/**
 * Card icons position on the card
 *
 * @author JayDi85
 */
public enum CardIconPosition {

    TOP(7),
    LEFT(7),
    BOTTOM(7),
    RIGHT(7),
    CORNER_TOP_LEFT(1),
    CORNER_TOP_RIGHT(1),
    CORNER_BOTTOM_LEFT(1),
    CORNER_BOTTOM_RIGHT(1);

    private final int maxIconsAmount; // max icons for the panel

    CardIconPosition(int maxIconsAmount) {
        this.maxIconsAmount = maxIconsAmount;
    }

    public int getMaxIconsAmount() {
        return this.maxIconsAmount;
    }

    public static CardIconPosition fromString(String value) {
        for (CardIconPosition item : CardIconPosition.values()) {
            if (item.toString().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
