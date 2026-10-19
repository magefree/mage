package mage.util;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.CardType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Immutable candidate inventory with a cache that changes only at relevant
 * Arena launches. Set metadata and name matching are resolved once at startup.
 */
final class ArenaCreatureSelector {

    private final List<Candidate> candidates;
    private final Supplier<Instant> clock;
    private Map<Integer, List<CardInfo>> cachedCards;
    private Instant cachedAt;
    private Instant nextRelease;

    ArenaCreatureSelector(Collection<CardInfo> implemented, ArenaCreaturePool catalog,
                          Function<String, ExpansionSet> sets, Supplier<Instant> clock) {
        this.clock = clock;
        this.candidates = findCandidates(implemented, catalog, sets);
    }

    synchronized Map<Integer, List<CardInfo>> getCards() {
        Instant now = clock.get();
        if (cachedCards == null || now.isBefore(cachedAt) || !now.isBefore(nextRelease)) {
            refresh(now);
        }
        return cachedCards;
    }

    private void refresh(Instant now) {
        Map<Integer, List<CardInfo>> cards = new TreeMap<>();
        Instant next = Instant.MAX;
        for (Candidate candidate : candidates) {
            if (now.isBefore(candidate.availableFrom)) {
                if (candidate.availableFrom.isBefore(next)) {
                    next = candidate.availableFrom;
                }
            } else {
                cards.computeIfAbsent(candidate.card.getManaValue(), key -> new ArrayList<>()).add(candidate.card);
            }
        }
        cards.replaceAll((manaValue, choices) -> Collections.unmodifiableList(choices));
        cachedCards = Collections.unmodifiableMap(cards);
        cachedAt = now;
        nextRelease = next;
    }

    private static List<Candidate> findCandidates(Collection<CardInfo> implemented, ArenaCreaturePool catalog,
                                                  Function<String, ExpansionSet> sets) {
        Map<Integer, Map<String, Candidate>> unique = new TreeMap<>();
        for (CardInfo card : implemented) {
            if (!isOriginalCreatureFront(card)) {
                continue;
            }
            ExpansionSet set = sets.apply(card.getSetCode());
            // ATC contains official Arena tutorial cards despite its custom tag.
            if (set == null || (set.getSetType().isCustomSet() && !"ATC".equals(set.getCode()))) {
                continue;
            }
            String name = catalog.getCanonicalName(card.getName(), card.getManaValue());
            Instant availableFrom = Instant.MIN; // Catalog entries are already known to be on Arena.
            if (name == null) {
                Date release = set.getArenaReleaseDate();
                if (catalog.containsCardName(card.getName()) || release == null) {
                    continue;
                }
                name = card.getName();
                availableFrom = release.toInstant();
            }
            String identity = ArenaCreaturePool.normalizeName(name);
            Map<String, Candidate> names = unique.computeIfAbsent(card.getManaValue(), key -> new TreeMap<>());
            Candidate previous = names.get(identity);
            // A later reprint must neither delay an existing creature nor add
            // another random outcome or an unnecessary cache refresh.
            if (previous == null || availableFrom.isBefore(previous.availableFrom)) {
                names.put(identity, new Candidate(card, availableFrom));
            }
        }
        List<Candidate> result = new ArrayList<>();
        unique.values().forEach(names -> result.addAll(names.values()));
        return Collections.unmodifiableList(result);
    }

    private static boolean isOriginalCreatureFront(CardInfo card) {
        return !card.isNightCard() && !card.isSplitCardHalf()
                && card.getTypes().contains(CardType.CREATURE) && !card.getName().startsWith("A-");
    }

    private static final class Candidate {

        private final CardInfo card;
        private final Instant availableFrom;

        private Candidate(CardInfo card, Instant availableFrom) {
            this.card = card;
            this.availableFrom = availableFrom;
        }
    }
}
