package mage.cards.decks;

import java.util.regex.Pattern;

public class CardNameUtil {

    public static final Pattern CARD_NAME_PATTERN = Pattern.compile("[+ !\"&',\\-./0-9:A-Za-z]+");

    /**
     * Convert card names with unicode symbols to ascii, uses to deck import from a third party services
     */
    public static String normalizeCardName(String name) {
        // new symbols checks in verify test, no need to manually search it
        return name
                .replace("&amp;", "//")
                .replace("///", "//")
                .replace("—", "")
                .replace("†", "+")
                .replace("꞉", "")
                .replace("®", "")
                .replace("★", "*")
                .replace("á", "a")
                .replace("Á", "a")
                .replace("à", "a")
                .replace("À", "a")
                .replace("â", "a")
                .replace("Â", "a")
                .replace("ä", "a")
                .replace("Ä", "a")
                .replace("Ã¶", "A")
                .replace("Ã†", "Ae")
                .replace("é", "e")
                .replace("É", "E")
                .replace("í", "i")
                .replace("Í", "I")
                .replace("î", "i")
                .replace("Î", "I")
                .replace("ï", "i")
                .replace("Ï", "I")
                .replace("ñ", "n")
                .replace("Ñ", "N")
                .replace("ó", "o")
                .replace("Ó", "O")
                .replace("ö", "o")
                .replace("Ö", "O")
                .replace("ō", "o")
                .replace("Ō", "O")
                .replace("ú", "u")
                .replace("Ú", "U")
                .replace("û", "u")
                .replace("Û", "U")
                .replace("ü", "u")
                .replace("Ü", "U")
                .replace("Φ", "Ph");
    }

    private CardNameUtil() {
        // utility class
    }

}
