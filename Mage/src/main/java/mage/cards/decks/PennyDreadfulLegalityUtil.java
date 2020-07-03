package mage.cards.decks;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PennyDreadfulLegalityUtil {
    public static Map<String, Integer> getLegalCardList() {
        Map<String, Integer> pdAllowed = new HashMap<>();

        Properties properties = new Properties();
        try {
            properties.load(PennyDreadfulLegalityUtil.class.getResourceAsStream("/pennydreadful.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            pdAllowed.put((String) entry.getKey(), 1);
        }

        return pdAllowed;
    }
}
