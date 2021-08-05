package mage.abilities.icon;

/**
 * Card icons order on the card's side
 *
 * @author JayDi85
 */
public enum CardIconOrder {

    START,
    CENTER,
    END;

    public static CardIconOrder fromString(String value) {
        for (CardIconOrder item : CardIconOrder.values()) {
            if (item.toString().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
