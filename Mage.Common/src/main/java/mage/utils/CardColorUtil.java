package mage.utils;

import mage.ObjectColor;
import mage.view.CardView;

import java.util.List;

/**
 * Utility class for {@link CardView}
 *
 * @version 0.1 02.11.2010
 * @author nantuko
 */
public final class CardColorUtil {

    private static final String regexBlack = ".*\\x7b.{0,2}B.{0,2}\\x7d.*";
    private static final String regexBlue = ".*\\x7b.{0,2}U.{0,2}\\x7d.*";
    private static final String regexRed = ".*\\x7b.{0,2}R.{0,2}\\x7d.*";
    private static final String regexGreen = ".*\\x7b.{0,2}G.{0,2}\\x7d.*";
    private static final String regexWhite = ".*\\x7b.{0,2}W.{0,2}\\x7d.*";

    public static int getColorIdentitySortValue(List<String> manaCost, ObjectColor originalColor, List<String> rules) {
        ObjectColor color = new ObjectColor(originalColor);
        for (String rule : rules) {
            rule = rule.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
            if (rule.matches(regexBlack)) {
                color.setBlack(true);
            }
            if (rule.matches(regexBlue)) {
                color.setBlue(true);
            }
            if (rule.matches(regexGreen)) {
                color.setGreen(true);
            }
            if (rule.matches(regexRed)) {
                color.setRed(true);
            }
            if (rule.matches(regexWhite)) {
                color.setWhite(true);
            }
        }

        int hash = 3;
        hash = 23 * hash + (color.isWhite() || manaCost.contains("{W}") ? 1 : 0);
        hash = 23 * hash + (color.isBlue() || manaCost.contains("{U}") ? 1 : 0);
        hash = 23 * hash + (color.isBlack() || manaCost.contains("{B}") ? 1 : 0);
        hash = 23 * hash + (color.isRed() || manaCost.contains("{R}") ? 1 : 0);
        hash = 23 * hash + (color.isGreen() || manaCost.contains("{G}") ? 1 : 0);
        return hash;
    }
}
