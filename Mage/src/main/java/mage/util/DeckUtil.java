
package mage.util;

/**
 *
 * @author LevelX2
 */
public final class DeckUtil {

    public static long fixedHash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            h = 31 * h + string.charAt(i);
        }
        return h;
    }
}
