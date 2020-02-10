package mage.client.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JayDi85
 */
public enum CardLanguage {

    ENGLISH("en", "English"),
    SPANISH("es", "Spanish"),
    FRENCH("fr", "French"),
    GERMAN("de", "German"),
    ITALIAN("it", "Italian"),
    PORTUGUESE("pt", "Portuguese"),
    JAPANESE("jp", "Japanese"),
    KOREAN("ko", "Korean"),
    RUSSIAN("ru", "Russian"),
    CHINES_SIMPLE("cns", "Chinese Simplified"),
    CHINES_TRADITION("cnt", "Chinese Traditional");

    private final String code;
    private final String text;

    CardLanguage(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String[] toList() {
        List<String> res = new ArrayList<>();
        for (CardLanguage l : values()) {
            res.add(l.toString());
        }
        return res.toArray(new String[0]);
    }

    public static CardLanguage valueByText(String text) {
        for (CardLanguage type : values()) {
            if (type.text.equals(text)) {
                return type;
            }
        }
        return CardLanguage.ENGLISH;
    }

    public static CardLanguage valueByCode(String code) {
        for (CardLanguage type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return CardLanguage.ENGLISH;
    }
}
