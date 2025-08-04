package mage.util;

/**
 * Helper class to work with console logs
 */
public class ConsoleUtil {

    public static String asRed(String text) {
        return "\u001B[31m" + text + "\u001B[0m";
    }

    public static String asGreen(String text) {
        return "\u001B[32m" + text + "\u001B[0m";
    }

    public static String asYellow(String text) {
        return "\u001B[33m" + text + "\u001B[0m";
    }
}
