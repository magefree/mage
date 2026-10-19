package mage.util;

import mage.cards.decks.CardNameUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Snapshot of Arena client availability, supplemented by newer Arena printings.
 * RandomCardUtil also admits creatures from released full Arena sets.
 * See Utils/arena-creature-pool.md for provenance and limits of Arena parity.
 * This catalog does not depend on the game's format or players' collections.
 */
public final class ArenaCreaturePool {

    private static final Pattern MANA_VALUE = Pattern.compile("[0-9]+");
    private final Map<Integer, Set<String>> namesByManaValue;
    private final Set<String> allNames;
    private final Map<String, String> canonicalNames;

    ArenaCreaturePool(Reader input) throws IOException {
        Map<Integer, Set<String>> names = new TreeMap<>();
        Set<String> unique = new TreeSet<>();
        Map<String, String> canonical = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(input)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] fields = line.split("\t", -1);
                if (fields.length != 2 || !MANA_VALUE.matcher(fields[0]).matches() || fields[1].isEmpty()) {
                    throw new IOException("Invalid Arena creature entry at line " + lineNumber + ": " + line);
                }
                int manaValue;
                try {
                    manaValue = Integer.parseInt(fields[0]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid Arena creature mana value at line " + lineNumber + ": " + line, e);
                }
                String previous = canonical.putIfAbsent(normalizeName(fields[1]), fields[1]);
                if (previous != null) {
                    throw new IOException("Duplicate Arena creature identity at line " + lineNumber
                            + ": " + previous + " / " + fields[1]);
                }
                unique.add(fields[1]);
                names.computeIfAbsent(manaValue, key -> new TreeSet<>()).add(fields[1]);
            }
        }
        if (unique.isEmpty()) {
            throw new IOException("Empty Arena creature catalog");
        }
        names.replaceAll((key, value) -> Collections.unmodifiableSet(value));
        namesByManaValue = Collections.unmodifiableMap(names);
        allNames = Collections.unmodifiableSet(unique);
        canonicalNames = Collections.unmodifiableMap(canonical);
    }

    public static ArenaCreaturePool getInstance() {
        return Holder.INSTANCE;
    }

    public Set<String> getCardNames(int manaValue) {
        return namesByManaValue.getOrDefault(manaValue, Collections.emptySet());
    }

    public Set<String> getAllCardNames() {
        return allNames;
    }

    /** Resolve XMage's ASCII/case spelling without changing the catalog identity or mana value. */
    public String getCanonicalName(String implementedName, int manaValue) {
        String name = canonicalNames.get(normalizeName(implementedName));
        return name != null && getCardNames(manaValue).contains(name) ? name : null;
    }

    boolean containsCardName(String implementedName) {
        return canonicalNames.containsKey(normalizeName(implementedName));
    }

    static String normalizeName(String name) {
        return CardNameUtil.normalizeCardName(name).toLowerCase(Locale.ROOT);
    }

    private static class Holder {

        private static final ArenaCreaturePool INSTANCE = load();

        private static ArenaCreaturePool load() {
            InputStream input = ArenaCreaturePool.class.getResourceAsStream("/arena-creature-pool.tsv");
            if (input == null) {
                throw new IllegalStateException("Missing Arena creature catalog");
            }
            try {
                return new ArenaCreaturePool(new InputStreamReader(input, StandardCharsets.UTF_8));
            } catch (IOException e) {
                // Never silently substitute the unrestricted paper card pool.
                throw new IllegalStateException("Cannot load Arena creature catalog", e);
            }
        }
    }
}
