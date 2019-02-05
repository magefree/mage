package mage.abilities.hint;

import java.awt.*;

/**
 * @author JayDi85
 */
public class HintUtils {

    // icons changes to real files on client side (see mana icons replacement)
    public static final String HINT_ICON_GOOD = "ICON_GOOD";
    public static final String HINT_ICON_BAD = "ICON_BAD";

    public static String prepareText(String text, Color color) {
        return prepareText(text, color, null);
    }

    public static String prepareText(String text, Color color, String icon) {
        String res;

        // text
        if (text != null && color != null) {
            String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getGreen());
            res = String.format("<font color=%s>%s</font>", hex, text);
        } else {
            res = text;
        }

        // icon
        if (res != null && icon != null) {
            res = icon + res;
        }

        return res;
    }
}
