package mage.abilities.hint;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author JayDi85
 */
public class HintUtils {

    public static final boolean ABILITY_HINTS_ENABLE = true;
    public static final boolean RESTRICT_HINTS_ENABLE = true;

    // icons changes to real files on client side (see mana icons replacement)
    public static final String HINT_ICON_GOOD = "ICON_GOOD";
    public static final String HINT_ICON_BAD = "ICON_BAD";
    public static final String HINT_ICON_RESTRICT = "ICON_RESTRICT";
    public static final String HINT_ICON_REQUIRE = "ICON_REQUIRE";
    public static final String HINT_ICON_DUNGEON_ROOM_CURRENT = "ICON_DUNGEON_ROOM_CURRENT";
    public static final String HINT_ICON_DUNGEON_ROOM_NEXT = "ICON_DUNGEON_ROOM_NEXT";

    //
    public static final String HINT_START_MARK = "<br/><hintstart/>"; // workaround to find hint text in rules list and shows it in html

    public static String prepareText(String text, Color color) {
        return prepareText(text, color, null);
    }

    public static String prepareText(String text, Color color, String icon) {
        String res;

        // text
        if (text != null && color != null) {
            String hex = colorToHtml(color);
            res = String.format("<font color=%s>%s</font>", hex, text);
        } else {
            res = (text == null ? "" : text);
        }

        // icon
        if (res != null && icon != null) {
            res = icon + res;
        }

        return res;
    }

    public static void appendHints(List<String> destList, List<String> newHints) {
        // append only unique hints
        Set<String> used = new HashSet<>();
        for (String s : newHints) {
            if (!used.contains(s)) {
                destList.add(s);
                used.add(s);
            }
        }
    }

    /**
     * Conver color to html hex format like #7FFFD4
     *
     * @param color
     * @return
     */
    public static String colorToHtml(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
