package mage.util;

import mage.MageObject;
import mage.ObjectColor;

import java.util.UUID;
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
        return getColoredObjectIdName(
                mageObject.getColor(null),
                mageObject.getId(),
                mageObject.getName(),
                String.format("[%s]", mageObject.getId().toString().substring(0, 3)),
                alternativeObject == null ? null : alternativeObject.getName()
        );
    }

    /**
     * Prepare html text with additional object info (can be used for card popup in GUI)
     *
     * @param color text color of the colored part
     * @param objectID object id
     * @param visibleColorPart colored part, popup will be work on it
     * @param visibleNormalPart additional part with default color
     * @param alternativeName alternative name, popup will use it on unknown object id or name
     * @return
     */
    public static String getColoredObjectIdName(ObjectColor color,
                                                UUID objectID,
                                                String visibleColorPart,
                                                String visibleNormalPart,
                                                String alternativeName) {
        String additionalText = !visibleColorPart.isEmpty() && !visibleNormalPart.isEmpty() ? " " : "";
        additionalText += visibleNormalPart;
        return "<font"
                + " color='" + getColorName(color) + "'"
                + " object_id='" + objectID + "'"
                + (alternativeName == null ? "" : " alternative_name='" + CardUtil.urlEncode(alternativeName) + "'")
                + ">" + visibleColorPart + "</font>"
                + additionalText;
    }

    /**
     * Add popup card support in game logs (client will process all href links)
     */
    public static String injectPopupSupport(String htmlLogs) {
        // input/output examples:
        // some text
        //   ignore
        // <font color='red'>some text</font>
        //   ignore
        // <font color='#B0C4DE' object_id='xxx'>Mountain</font> [233]
        //   <a href="#Mountain"><font color='#B0C4DE' object_id='xxx'>Mountain</font></a> [233]
        // <font color='#FF6347' object_id='xxx'>[fc7]</font>
        //   <a href="#[fc7]"><font color='#FF6347' object_id='xxx'>[fc7]</font></a>
        // <font color='#CCCC33'>16:45, T1.M1: </font><font color='White'><font color='#20B2AA'>Human</font> puts <font color='#B0C4DE' object_id='3d2cae7c-9785-47b6-a636-84b07d939425'>Mountain</font> [3d2] from hand onto the Battlefield</font> [123]
        //   <font color='#CCCC33'>16:45, T1.M1: </font><font color='White'><font color='#20B2AA'>Human</font> puts <a href="#Mountain"><font color='#B0C4DE' object_id='3d2cae7c-9785-47b6-a636-84b07d939425'>Mountain</font></a> [3d2] from hand onto the Battlefield</font> [123]
        return htmlLogs.replaceAll("<br>", "\r\n<br>\r\n").replaceAll(
                "<font (color=[^<]*object_id=[^>]*)>([^<]*)</font>",
                "<a href=\"#$2\"><font $1>$2</font></a>"
        );
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
