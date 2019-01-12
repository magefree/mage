package org.mage.plugins.card.dl.sources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JayDi85
 */
public class ScryfallImageSupportTokens {

    private static final Map<String, String> supportedSets = new HashMap<>();

    private static final Map<String, String> supportedCards = new HashMap<String, String>() {
        {
            // xmage token -> direct or api link:
            // examples:
            //   direct example: https://img.scryfall.com/cards/large/en/trix/6.jpg
            //   api example: https://api.scryfall.com/cards/trix/6/en?format=image
            //   api example: https://api.scryfall.com/cards/trix/6?format=image
            // api format is primary
            //
            // code form for one token:
            //   set/token_name
            //
            // code form for same name tokens (alternative images):
            //   set/token_name/1
            //   set/token_name/2

            // RIX
            put("RIX/City's Blessing", "https://api.scryfall.com/cards/trix/6/en?format=image"); // TODO: missing from tokens data
            put("RIX/Elemental/1", "https://api.scryfall.com/cards/trix/1/en?format=image");
            put("RIX/Elemental/2", "https://api.scryfall.com/cards/trix/2/en?format=image");
            put("RIX/Golem", "https://api.scryfall.com/cards/trix/4/en?format=image");
            put("RIX/Emblem Huatli, Radiant Champion", "https://api.scryfall.com/cards/trix/5/en?format=image");
            put("RIX/Saproling", "https://api.scryfall.com/cards/trix/3/en?format=image");

            // generate supported sets
            supportedSets.clear();
            for (String cardName : this.keySet()) {
                String[] s = cardName.split("\\/");
                if (s.length > 1) {
                    supportedSets.putIfAbsent(s[0], s[0]);
                }
            }
        }
    };

    public static Map<String, String> getSupportedSets() {
        return supportedSets;
    }

    public static String findTokenLink(String setCode, String tokenName, Integer tokenNumber) {
        String search = setCode + "/" + tokenName + (!tokenNumber.equals(0) ? "/" + tokenNumber : "");
        return supportedCards.getOrDefault(search, null);
    }
}
