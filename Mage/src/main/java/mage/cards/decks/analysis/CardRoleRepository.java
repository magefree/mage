package mage.cards.decks.analysis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.cards.Card;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * Lazy lookup for generated card roles.
 * <p>
 * The generated index is an offline Scryfall-derived hint. The text/ability classifier
 * in {@link DeckProfileService} remains the fallback for custom or unmapped cards.
 */
public final class CardRoleRepository {

    private static final Logger logger = Logger.getLogger(CardRoleRepository.class);
    private static final String RESOURCE_PATH = "card-roles/scryfall-card-roles-runtime.json.gz";

    private static volatile CardRoleRepository instance;

    private final Map<String, Set<DeckRole>> rolesByPrintKey;
    private final Map<String, Set<DeckRole>> rolesByName;
    private final Map<String, Set<DeckRole>> rolesByOracleId;
    private final Map<String, Set<String>> mechanicsByPrintKey;
    private final Map<String, Set<String>> mechanicsByName;
    private final Map<String, Set<String>> mechanicsByOracleId;

    private CardRoleRepository(Map<String, Set<DeckRole>> rolesByPrintKey,
                               Map<String, Set<DeckRole>> rolesByName,
                               Map<String, Set<DeckRole>> rolesByOracleId,
                               Map<String, Set<String>> mechanicsByPrintKey,
                               Map<String, Set<String>> mechanicsByName,
                               Map<String, Set<String>> mechanicsByOracleId) {
        this.rolesByPrintKey = rolesByPrintKey;
        this.rolesByName = rolesByName;
        this.rolesByOracleId = rolesByOracleId;
        this.mechanicsByPrintKey = mechanicsByPrintKey;
        this.mechanicsByName = mechanicsByName;
        this.mechanicsByOracleId = mechanicsByOracleId;
    }

    public static CardRoleRepository getInstance() {
        CardRoleRepository current = instance;
        if (current == null) {
            synchronized (CardRoleRepository.class) {
                current = instance;
                if (current == null) {
                    current = load();
                    instance = current;
                }
            }
        }
        return current;
    }

    public Set<DeckRole> lookup(Card card) {
        if (card == null) {
            return Collections.emptySet();
        }

        Set<DeckRole> exact = rolesByPrintKey.get(printKey(card));
        if (exact != null) {
            return exact;
        }

        Set<DeckRole> byName = rolesByName.get(card.getName());
        return byName != null ? byName : Collections.emptySet();
    }

    public Set<DeckRole> lookupByOracleId(String oracleId) {
        if (oracleId == null || oracleId.isEmpty()) {
            return Collections.emptySet();
        }
        Set<DeckRole> roles = rolesByOracleId.get(oracleId);
        return roles != null ? roles : Collections.emptySet();
    }

    public Set<String> lookupMechanics(Card card) {
        if (card == null) {
            return Collections.emptySet();
        }

        Set<String> exact = mechanicsByPrintKey.get(printKey(card));
        if (exact != null) {
            return exact;
        }

        Set<String> byName = mechanicsByName.get(card.getName());
        return byName != null ? byName : Collections.emptySet();
    }

    public Set<String> lookupMechanicsByOracleId(String oracleId) {
        if (oracleId == null || oracleId.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> mechanics = mechanicsByOracleId.get(oracleId);
        return mechanics != null ? mechanics : Collections.emptySet();
    }

    private static String printKey(Card card) {
        return String.format(
                Locale.ROOT,
                "%s:%s:%s",
                card.getExpansionSetCode().toUpperCase(Locale.ROOT),
                card.getCardNumber(),
                card.getName()
        );
    }

    private static CardRoleRepository load() {
        try (InputStream input = CardRoleRepository.class.getClassLoader().getResourceAsStream(RESOURCE_PATH)) {
            if (input == null) {
                logger.warn("Card role repository resource not found: " + RESOURCE_PATH);
                return empty();
            }
            try (GZIPInputStream gzip = new GZIPInputStream(input);
                 InputStreamReader reader = new InputStreamReader(gzip, StandardCharsets.UTF_8)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                return new CardRoleRepository(
                        parseRoleMap(root.getAsJsonObject("rolesByPrintKey")),
                        parseRoleMap(root.getAsJsonObject("rolesByName")),
                        parseRoleMap(root.getAsJsonObject("rolesByOracleId")),
                        parseStringMap(root.getAsJsonObject("mechanicsByPrintKey")),
                        parseStringMap(root.getAsJsonObject("mechanicsByName")),
                        parseStringMap(root.getAsJsonObject("mechanicsByOracleId"))
                );
            }
        } catch (IOException | RuntimeException e) {
            logger.warn("Failed to load card role repository resource: " + RESOURCE_PATH, e);
            return empty();
        }
    }

    private static CardRoleRepository empty() {
        return new CardRoleRepository(
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptyMap()
        );
    }

    private static Map<String, Set<DeckRole>> parseRoleMap(JsonObject object) {
        if (object == null) {
            return Collections.emptyMap();
        }
        Map<String, Set<DeckRole>> result = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Set<DeckRole> roles = parseRoles(entry.getValue().getAsJsonArray());
            if (!roles.isEmpty()) {
                result.put(entry.getKey(), roles);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private static Set<DeckRole> parseRoles(JsonArray array) {
        EnumSet<DeckRole> roles = EnumSet.noneOf(DeckRole.class);
        for (JsonElement element : array) {
            try {
                roles.add(DeckRole.valueOf(element.getAsString()));
            } catch (IllegalArgumentException e) {
                logger.warn("Ignoring unknown generated card role: " + element.getAsString());
            }
        }
        return Collections.unmodifiableSet(roles);
    }

    private static Map<String, Set<String>> parseStringMap(JsonObject object) {
        if (object == null) {
            return Collections.emptyMap();
        }
        Map<String, Set<String>> result = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Set<String> values = parseStrings(entry.getValue().getAsJsonArray());
            if (!values.isEmpty()) {
                result.put(entry.getKey(), values);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private static Set<String> parseStrings(JsonArray array) {
        Set<String> values = new java.util.TreeSet<>();
        for (JsonElement element : array) {
            String value = element.getAsString();
            if (value != null && !value.isEmpty()) {
                values.add(value);
            }
        }
        return Collections.unmodifiableSet(values);
    }
}
