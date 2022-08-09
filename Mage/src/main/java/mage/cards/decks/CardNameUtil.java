package mage.cards.decks;

import java.util.regex.Pattern;

public class CardNameUtil {

    public static final Pattern CARD_NAME_PATTERN = Pattern.compile("[ !\"&',\\-./0-9:A-Za-z]+");

    public static String normalizeCardName(String name) {
        return name
            .replace("&amp;", "//")
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
            .replace("†", "+");
    }

}
