package mage.constants;

/**
 *
 * @author JayDi85
 */
public enum EmptyNames {

    // TODO: make names for that cards and enable Assert.assertNotEquals("", permanentName); for assertXXX tests
    // TODO: replace all getName().equals to haveSameNames and haveEmptyName
    FACE_DOWN_CREATURE(""), // "Face down creature"
    FACE_DOWN_TOKEN(""), // "Face down token"
    FACE_DOWN_CARD(""); // "Face down card"

    public static final String EMPTY_NAME_IN_LOGS = "face down object";

    private final String cardName;

    EmptyNames(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public String toString() {
        return cardName;
    }

    public static boolean isEmptyName(String objectName) {
        return objectName.equals(FACE_DOWN_CREATURE.toString())
                || objectName.equals(FACE_DOWN_TOKEN.toString())
                || objectName.equals(FACE_DOWN_CARD.toString());
    }
}
