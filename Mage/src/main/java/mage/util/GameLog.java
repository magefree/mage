package mage.util;

import mage.MageObject;
import mage.ObjectColor;

import java.util.regex.Pattern;

/**
 * @author LevelX2
 */
public final class GameLog {

    static final String LOG_COLOR_PLAYER = "#20B2AA"; // LightSeaGreen
    static final String LOG_COLOR_PLAYER_REQUEST = "#D2691E"; // Chocolate
    static final String LOG_COLOR_PLAYER_CONFIRM = "#D2691E"; // Chocolate
    // colors for more dark background
    static final String LOG_COLOR_GREEN = "#90EE90"; // LightGreen
    static final String LOG_COLOR_RED = "#FF6347";   // Tomato
    static final String LOG_COLOR_BLUE = "#87CEFA";  // LightSkyBlue
    static final String LOG_COLOR_BLACK = "#696969";  // DimGray  	   // "#5F9EA0"; // CadetBlue
    static final String LOG_COLOR_WHITE = "#F0E68C"; // Khaki
    static final String LOG_COLOR_MULTI = "#DAA520"; // GoldenRod
    static final String LOG_COLOR_COLORLESS = "#B0C4DE"; // LightSteelBlue
    // colors for tooltip (light background)
    static final String LOG_TT_COLOR_RED = "Red";   // Tomato
    static final String LOG_TT_COLOR_GREEN = "Green"; // LightGreen
    static final String LOG_TT_COLOR_BLUE = "Blue";
    static final String LOG_TT_COLOR_BLACK = "Black";
    static final String LOG_TT_COLOR_WHITE = "#FDFFE6";
    static final String LOG_TT_COLOR_MULTI = "#FFAC40";
    static final String LOG_TT_COLOR_COLORLESS = "#94A4BA";
    static final String LOG_COLOR_NEUTRAL = "#F0F8FF"; // AliceBlue

    public static String replaceNameByColoredName(MageObject mageObject, String text) {
        return replaceNameByColoredName(mageObject, text, null);
    }

    /**
     * @param mageObject        - original object name to replace
     * @param text              - original text to insert object's html name
     * @param alternativeObject - alternative object (object to show in card's hint in GUI)
     * @return
     */
    public static String replaceNameByColoredName(MageObject mageObject, String text, MageObject alternativeObject) {
        return text.replaceAll(Pattern.quote(mageObject.getName()), getColoredObjectIdName(mageObject, alternativeObject));
    }

    public static String getColoredObjectName(MageObject mageObject) {
        return "<font color='" + getColorName(mageObject.getColor(null)) + "'>" + mageObject.getName() + "</font>";
    }

    public static String getColoredObjectIdName(MageObject mageObject) {
        return getColoredObjectIdName(mageObject, null);
    }

    public static String getColoredObjectIdName(MageObject mageObject, MageObject alternativeObject) {
        return "<font"
                + " color='" + getColorName(mageObject.getColor(null)) + "'"
                + " object_id='" + mageObject.getId() + "'"
                + (alternativeObject == null ? "" : " alternative_name='" + CardUtil.urlEncode(alternativeObject.getName()) + "'")
                + ">" + mageObject.getIdName() + "</font>";
    }

    public static String getColoredObjectIdNameForTooltip(MageObject mageObject) {
        return "<font color='" + getTooltipColorName(mageObject.getColor(null)) + "'>" + mageObject.getIdName() + "</font>";
    }

    public static String getNeutralColoredText(String text) {
        return "<font color='" + LOG_COLOR_NEUTRAL + "'>" + text + "</font>";
    }

    public static String getColoredPlayerName(String name) {
        return "<font color='" + LOG_COLOR_PLAYER + "'>" + name + "</font>";
    }

    public static String getPlayerRequestColoredText(String name) {
        return "<font color='" + LOG_COLOR_PLAYER_REQUEST + "'>" + name + "</font>";
    }

    public static String getPlayerConfirmColoredText(String name) {
        return "<font color='" + LOG_COLOR_PLAYER_CONFIRM + "'>" + name + "</font>";
    }

    public static String getSmallSecondLineText(String text) {
        return "<div style='font-size:11pt'>" + text + "</div>";
    }

    private static String getColorName(ObjectColor objectColor) {
        if (objectColor.isMulticolored()) {
            return LOG_COLOR_MULTI;
        } else if (objectColor.isColorless()) {
            return LOG_COLOR_COLORLESS;
        } else if (objectColor.isRed()) {
            return LOG_COLOR_RED;
        } else if (objectColor.isGreen()) {
            return LOG_COLOR_GREEN;
        } else if (objectColor.isBlue()) {
            return LOG_COLOR_BLUE;
        } else if (objectColor.isWhite()) {
            return LOG_COLOR_WHITE;
        } else {
            return LOG_COLOR_BLACK;
        }
    }

    private static String getTooltipColorName(ObjectColor objectColor) {
        if (objectColor.isMulticolored()) {
            return LOG_TT_COLOR_MULTI;
        } else if (objectColor.isColorless()) {
            return LOG_TT_COLOR_COLORLESS;
        } else if (objectColor.isRed()) {
            return LOG_TT_COLOR_RED;
        } else if (objectColor.isGreen()) {
            return LOG_TT_COLOR_GREEN;
        } else if (objectColor.isBlue()) {
            return LOG_TT_COLOR_BLUE;
        } else if (objectColor.isWhite()) {
            return LOG_TT_COLOR_WHITE;
        } else {
            return LOG_TT_COLOR_BLACK;
        }
    }
}
