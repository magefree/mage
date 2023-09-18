package mage.cards.decks;

import java.util.regex.Pattern;

public class CardNameUtil {

    public static final Pattern CARD_NAME_PATTERN = Pattern.compile("[ !\"&',\\-./0-9:A-Za-z]+");

    /**
     * Convert card names with unicode symbols to ascii, uses to deck import from a third party services
     *
     * @param name
     * @return
     */
    public static String normalizeCardName(String name) {
        // new symbols checks in verify test, no need to manually search it
        return name
                .replace("&amp;", "//")
                .replace("///", "//")
                .replace("Ã†", "Ae")
                .replace("Ã¶", "A")
                .replace("ö", "o")
                .replace("û", "u")
                .replace("í", "i")
                .replace("â", "a")
                .replace("á", "a")
                .replace("à", "a")
                .replace("é", "e")
                .replace("ú", "u")
                .replace("†", "+")
                .replace("★", "*")
                .replace("ó", "o")
                .replace("ä", "a")
                .replace("ü", "u")
                .replace("É", "E")
                .replace("ñ", "n")
                .replace("®", "");
    }
}
