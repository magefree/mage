package mage.constants;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Constants {

    private Constants() {
        throw new AssertionError();
    }

    public static final int FRAME_MAX_HEIGHT = 367;
    public static final int FRAME_MAX_WIDTH = 256;
    public static final int ART_MAX_HEIGHT = 168;
    public static final int ART_MAX_WIDTH = 227;
    public static final int SYMBOL_MAX_HEIGHT = 14;
    public static final int SYMBOL_MAX_WIDTH = 14;
    public static final int SYMBOL_MAX_XOFFSET = 27;
    public static final int SYMBOL_MAX_YOFFSET = 15;
    public static final int SYMBOL_MAX_SPACE = 14;
    public static final int CONTENT_MAX_XOFFSET = 15;
    public static final int ART_MAX_YOFFSET = 37;
    public static final int NAME_MAX_YOFFSET = 28;
    public static final int TYPE_MAX_YOFFSET = 223;
    public static final int ICON_MAX_HEIGHT = 16;
    public static final int ICON_MAX_WIDTH = 16;
    public static final int ICON_MAX_XOFFSET = 238;
    public static final int ICON_MAX_YOFFSET = 210;
    public static final int TEXT_MAX_YOFFSET = 232;
    public static final int TEXT_MAX_WIDTH = 227;
    public static final int TEXT_MAX_HEIGHT = 105;
    public static final int NAME_FONT_MAX_SIZE = 13;
    public static final int TEXT_FONT_MAX_SIZE = 11;
    public static final int POWBOX_MAX_TOP = 336;
    public static final int POWBOX_MAX_LEFT = 202;
    public static final int POWBOX_TEXT_MAX_TOP = 352;
    public static final int POWBOX_TEXT_MAX_LEFT = 212;
    public static final int DAMAGE_MAX_LEFT = 180;

    public static final double SCALE_FACTOR = 0.5;

    public static final int MIN_AVATAR_ID = 10;
    public static final int MAX_AVATAR_ID = 32;
    public static final int DEFAULT_AVATAR_ID = 10;

    public static final int MAX_CHAT_MESSAGE_SIZE = 500; // ignore too big messages

    /**
     * Time each player has during the game to play using his\her priority.
     */
    public static final int PRIORITY_TIME_SEC = 1200;

    public enum Option {
        ;
        public static final String POSSIBLE_ATTACKERS = "possibleAttackers";
        public static final String POSSIBLE_BLOCKERS = "possibleBlockers";
        public static final String SPECIAL_BUTTON = "specialButton";
        // used to control automatic answers of optional effects
        public static final String ORIGINAL_ID = "originalId";
        public static final String SECOND_MESSAGE = "secondMessage";
        public static final String HINT_TEXT = "hintText";
    }
}
