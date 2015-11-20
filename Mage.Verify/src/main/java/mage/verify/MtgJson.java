package mage.verify;

import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class MtgJson {

    private static class CardHolder {
        private static final Map<String, JsonCard> cards;
        static {
            try {
                cards = JsonCard.loadAll();
                addAliases(cards);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static JsonCard find(String name) {
        return findReference(CardHolder.cards, name);
    }

    private static <T> T findReference(Map<String, T> reference, String name) {
        T ref = reference.get(name);
        if (ref == null) {
            name = name.replaceFirst("\\bA[Ee]", "Æ");
            ref = reference.get(name);
        }
        if (ref == null) {
            name = name.replace("'", "\""); // for Kongming, "Sleeping Dragon" & Pang Tong, "Young Phoenix"
            ref = reference.get(name);
        }
        return ref;
    }

    private static <T> void addAliases(Map<String, T> reference) {
        Map<String, String> aliases = new HashMap<>();
        for (String name : reference.keySet()) {
            String unaccented = stripAccents(name);
            if (!name.equals(unaccented)) {
                aliases.put(name, unaccented);
            }
        }
        for (Map.Entry<String, String> mapping : aliases.entrySet()) {
            reference.put(mapping.getValue(), reference.get(mapping.getKey()));
        }
    }

    private static String stripAccents(String str) {
        String decomposed = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return decomposed.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

}
