package mage.util;

import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Random creatures from the Arena catalog and released full Arena sets.
 * Each supported name has equal weight, regardless of the number of printings.
 * See Utils/arena-creature-pool.md for the limits of Arena parity.
 */
public final class RandomCardUtil {

    private RandomCardUtil() {
    }

    public static Card createRandomCreature(int manaValue) {
        if (manaValue < 0) {
            return null;
        }
        return createRandomCreature(ArenaPoolHolder.SELECTOR.getCards()
                .getOrDefault(manaValue, Collections.emptyList()));
    }

    static Card createRandomCreature(List<CardInfo> candidates) {
        if (candidates.isEmpty()) {
            return null;
        }
        int index = RandomUtil.nextInt(candidates.size());
        Card card = candidates.get(index).createCard();
        if (card != null) {
            return card;
        }
        // Only allocate a retry list on construction failure. Never remove
        // entries from the shared immutable pool.
        List<CardInfo> remaining = new ArrayList<>(candidates);
        remaining.remove(index);
        while (!remaining.isEmpty()) {
            card = remaining.remove(RandomUtil.nextInt(remaining.size())).createCard();
            if (card != null) {
                return card;
            }
        }
        return null;
    }

    /** Audit gaps in the legacy catalog; newly released full sets are additional. */
    public static Set<String> getUnimplementedArenaCreatureNames() {
        ArenaCreaturePool catalog = ArenaCreaturePool.getInstance();
        Set<String> missing = new TreeSet<>(catalog.getAllCardNames());
        ArenaPoolHolder.SELECTOR.getCards().values().forEach(cards -> cards.forEach(card -> {
            String name = catalog.getCanonicalName(card.getName(), card.getManaValue());
            if (name != null) {
                missing.remove(name);
            }
        }));
        return Collections.unmodifiableSet(missing);
    }

    private static class ArenaPoolHolder {

        // Card scanning initializes the repository before any game starts. Query
        // once, not once per resolution or AI branch (see issue #7014).
        private static final ArenaCreatureSelector SELECTOR = new ArenaCreatureSelector(
                CardRepository.instance.findCards(new CardCriteria().types(CardType.CREATURE)),
                ArenaCreaturePool.getInstance(), Sets::findSet, Instant::now);
    }
}
