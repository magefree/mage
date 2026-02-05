package mage.player.ai.combo;

import mage.player.ai.combo.patterns.FoodChainPattern;
import mage.player.ai.combo.patterns.KikiJikiPattern;
import mage.player.ai.combo.patterns.ReanimatorPattern;
import mage.player.ai.combo.patterns.StormComboPattern;
import mage.player.ai.combo.patterns.ThassasOraclePattern;
import mage.player.ai.combo.patterns.TwoCardComboPattern;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI: Registry that loads and manages all combo patterns.
 * Loads two-card combos from infinite-combos.txt and registers
 * complex combo patterns.
 *
 * @author Claude
 */
public final class ComboRegistry {

    private static final Logger logger = Logger.getLogger(ComboRegistry.class);

    private static final String INFINITE_COMBOS_RESOURCE = "/brackets/infinite-combos.txt";

    // Singleton instance
    private static volatile ComboRegistry instance;
    private static final Object lock = new Object();

    // All registered patterns
    private final List<ComboPattern> patterns;

    // Quick lookup by combo piece name
    private final Map<String, Set<ComboPattern>> patternsByCard;

    // Stats
    private int twoCardCombosLoaded = 0;
    private int complexPatternsLoaded = 0;

    private ComboRegistry() {
        this.patterns = new ArrayList<>();
        this.patternsByCard = new ConcurrentHashMap<>();
        initialize();
    }

    public static ComboRegistry getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ComboRegistry();
                }
            }
        }
        return instance;
    }

    private void initialize() {
        // Load two-card combos from resource file
        loadTwoCardCombos();

        // Register complex combo patterns
        registerComplexPatterns();

        // Sort patterns by priority (highest first)
        patterns.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));

        logger.info(String.format("ComboRegistry initialized: %d two-card combos, %d complex patterns, %d total",
                twoCardCombosLoaded, complexPatternsLoaded, patterns.size()));
    }

    private void loadTwoCardCombos() {
        try (InputStream is = getClass().getResourceAsStream(INFINITE_COMBOS_RESOURCE)) {
            if (is == null) {
                logger.warn("Could not find infinite-combos.txt resource at " + INFINITE_COMBOS_RESOURCE);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    // Skip comments and empty lines
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    // Parse format: Card1@Card2
                    String[] parts = line.split("@", 2);
                    if (parts.length == 2) {
                        String card1 = parts[0].trim();
                        String card2 = parts[1].trim();

                        if (!card1.isEmpty() && !card2.isEmpty()) {
                            TwoCardComboPattern pattern = new TwoCardComboPattern(card1, card2);
                            registerPattern(pattern);
                            twoCardCombosLoaded++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error loading two-card combos: " + e.getMessage(), e);
        }
    }

    private void registerComplexPatterns() {
        // Storm combo pattern
        registerPattern(new StormComboPattern());
        complexPatternsLoaded++;

        // Thassa's Oracle / Lab Man pattern
        registerPattern(new ThassasOraclePattern());
        complexPatternsLoaded++;

        // Kiki-Jiki / Splinter Twin pattern
        registerPattern(new KikiJikiPattern());
        complexPatternsLoaded++;

        // Reanimator pattern
        registerPattern(new ReanimatorPattern());
        complexPatternsLoaded++;

        // Food Chain pattern
        registerPattern(new FoodChainPattern());
        complexPatternsLoaded++;
    }

    private void registerPattern(ComboPattern pattern) {
        patterns.add(pattern);

        // Index by card name for quick lookup
        for (String cardName : pattern.getComboPieces()) {
            patternsByCard
                    .computeIfAbsent(cardName.toLowerCase(Locale.ROOT), k -> new HashSet<>())
                    .add(pattern);
        }
    }

    /**
     * Get all registered combo patterns
     *
     * @return unmodifiable list of patterns sorted by priority
     */
    public List<ComboPattern> getAllPatterns() {
        return Collections.unmodifiableList(patterns);
    }

    /**
     * Get patterns that include a specific card
     *
     * @param cardName name of the card to look up
     * @return set of patterns containing this card
     */
    public Set<ComboPattern> getPatternsForCard(String cardName) {
        Set<ComboPattern> result = patternsByCard.get(cardName.toLowerCase(Locale.ROOT));
        return result != null ? Collections.unmodifiableSet(result) : Collections.emptySet();
    }

    /**
     * Get patterns that match any of the given card names
     *
     * @param cardNames set of card names to check
     * @return set of patterns containing any of these cards
     */
    public Set<ComboPattern> getPatternsForCards(Set<String> cardNames) {
        Set<ComboPattern> result = new HashSet<>();
        for (String cardName : cardNames) {
            result.addAll(getPatternsForCard(cardName));
        }
        return result;
    }

    /**
     * Check if a card is part of any known combo
     *
     * @param cardName name of the card to check
     * @return true if the card is in at least one combo pattern
     */
    public boolean isComboCard(String cardName) {
        return patternsByCard.containsKey(cardName.toLowerCase(Locale.ROOT));
    }

    /**
     * Get the total number of registered patterns
     *
     * @return pattern count
     */
    public int getPatternCount() {
        return patterns.size();
    }

    /**
     * Get stats about loaded combos
     *
     * @return map with counts of different combo types
     */
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("twoCardCombos", twoCardCombosLoaded);
        stats.put("complexPatterns", complexPatternsLoaded);
        stats.put("totalPatterns", patterns.size());
        stats.put("uniqueCards", patternsByCard.size());
        return stats;
    }
}
