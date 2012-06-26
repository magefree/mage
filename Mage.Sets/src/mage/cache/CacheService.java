package mage.cache;

import mage.Constants;
import mage.cards.Card;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author noxx
 */
public class CacheService {

    private static final Logger log = Logger.getLogger(CacheService.class);
    
    private static final String NAMES_CACHE_OBJECT_NAME = "card_names";
    private static final String NAMES_KEY = "card_names_key";
    private static final String CARD_COUNT_KEY = "card_count_key";
    private static final String CREATURE_TYPES_CACHE_OBJECT_NAME = "creature_types";
    private static final String CREATURE_TYPES_KEY = "creature_types_key";
    private static final String NONLAND_NAMES_CACHE_OBJECT_NAME = "nonland_names";
    private static final String NONLAND_NAMES_KEY = "nonland_names_key";

    private static final int CACHE_VERSION = 1;

    public static Set<String> loadCardNames(List<Card> cards) {
        Cache cache = CacheDataHelper.getCachedObject(NAMES_CACHE_OBJECT_NAME);
        Set<String> names = new TreeSet<String>();
        if (!CacheDataHelper.validateCache(cache, CACHE_VERSION, cards.size(), CARD_COUNT_KEY)) {
            for (Card card : cards) {
                names.add(card.getName());
            }
            cache = new Cache(NAMES_CACHE_OBJECT_NAME, CACHE_VERSION);
            cache.getCacheObjects().put(NAMES_KEY, names);
            cache.getCacheObjects().put(CARD_COUNT_KEY, cards.size());
            CacheDataHelper.cacheObject(cache, NAMES_CACHE_OBJECT_NAME);
        } else {
            Set<String> cachedNames = (Set<String>) cache.getCacheObjects().get(NAMES_KEY);
            names.addAll(cachedNames);
            log.debug("Loaded card names from cache.");
        }

        return names;
    }

    public static Set<String> loadCreatureTypes(List<Card> cards) {
        Set<String> creatureTypes = new TreeSet<String>();
        Cache cache = CacheDataHelper.getCachedObject(CREATURE_TYPES_CACHE_OBJECT_NAME);
        if (!CacheDataHelper.validateCache(cache, CACHE_VERSION, cards.size(), CARD_COUNT_KEY)) {
            for (Card card : cards) {
                if (card.getCardType().contains(Constants.CardType.CREATURE)) {
                    for (String type : card.getSubtype()) {
                        creatureTypes.add(type);
                        if (type.equals("")) {
                            throw new IllegalStateException("Card with empty subtype: " + card.getName());
                        }
                    }
                }
            }
            cache = new Cache(CREATURE_TYPES_CACHE_OBJECT_NAME, CACHE_VERSION);
            cache.getCacheObjects().put(CREATURE_TYPES_KEY, creatureTypes);
            cache.getCacheObjects().put(CARD_COUNT_KEY, cards.size());
            CacheDataHelper.cacheObject(cache, CREATURE_TYPES_CACHE_OBJECT_NAME);
        } else {
            Set<String> cachedCreatureTypes = (Set<String>) cache.getCacheObjects().get(CREATURE_TYPES_KEY);
            creatureTypes.addAll(cachedCreatureTypes);
            log.debug("Loaded creature types from cache.");
        }

        return creatureTypes;
    }

    public static Set<String> loadNonLandNames(List<Card> cards) {
        Set<String> nonLandNames = new TreeSet<String>();
        Cache cache = CacheDataHelper.getCachedObject(NONLAND_NAMES_CACHE_OBJECT_NAME);
        if (!CacheDataHelper.validateCache(cache, CACHE_VERSION, cards.size(), CARD_COUNT_KEY)) {
            for (Card card : cards) {
                if (!card.getCardType().contains(Constants.CardType.LAND)) nonLandNames.add(card.getName());
            }
            cache = new Cache(NONLAND_NAMES_CACHE_OBJECT_NAME, CACHE_VERSION);
            cache.getCacheObjects().put(NONLAND_NAMES_KEY, nonLandNames);
            cache.getCacheObjects().put(CARD_COUNT_KEY, cards.size());
            CacheDataHelper.cacheObject(cache, NONLAND_NAMES_CACHE_OBJECT_NAME);
        } else {
            Set<String> cachedNonLandNames = (Set<String>) cache.getCacheObjects().get(NONLAND_NAMES_KEY);
            nonLandNames.addAll(cachedNonLandNames);
            log.debug("Loaded non land names from cache.");
        }

        return nonLandNames;
    }
}
