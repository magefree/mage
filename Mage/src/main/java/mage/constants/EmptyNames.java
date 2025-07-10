package mage.constants;

import java.util.Arrays;

/**
 * @author JayDi85
 */
public enum EmptyNames {
    // TODO: replace all getName().equals to haveSameNames and haveEmptyName
    FACE_DOWN_CREATURE("", "[face_down_creature]"), // "Face down creature"
    FACE_DOWN_TOKEN("", "[face_down_token]"), // "Face down token"
    FACE_DOWN_CARD("", "[face_down_card]"), // "Face down card"
    FULLY_LOCKED_ROOM("", "[fully_locked_room]"); // "Fully locked room"

    public static final String EMPTY_NAME_IN_LOGS = "face down object";

    private final String objectName; // for mtg rules
    private final String testCommand; // for unit tests

    EmptyNames(String objectName, String testCommand) {
        this.objectName = objectName;
        this.testCommand = testCommand;
    }

    @Override
    public String toString() {
        return objectName;
    }

    /**
     * Face down choice for unit tests (use it instead empty string)
     */
    public String getTestCommand() {
        return this.testCommand;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public static boolean isEmptyName(String objectName) {
        return objectName.equals(FACE_DOWN_CREATURE.getObjectName())
                || objectName.equals(FACE_DOWN_TOKEN.getObjectName())
                || objectName.equals(FACE_DOWN_CARD.getObjectName()) 
                || objectName.equals(FULLY_LOCKED_ROOM.getObjectName());
    }

    public static String replaceTestCommandByObjectName(String searchCommand) {
        EmptyNames res = Arrays.stream(values()).filter(e -> e.testCommand.equals(searchCommand)).findAny().orElse(null);
        return res == null ? searchCommand : res.objectName;
    }
}
