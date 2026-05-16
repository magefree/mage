package mage.cards.decks.analysis;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds card-level synergy evidence from deck and command-zone profiles.
 */
public final class DeckSynergyPackageService {

    private static final Map<DeckSynergy, List<DeckRole>> COMPONENTS = buildComponents();

    private DeckSynergyPackageService() {
    }

    public static Map<DeckSynergy, DeckSynergyPackage> buildPackages(DeckProfile mainDeck, DeckProfile commandZone) {
        Map<DeckSynergy, DeckSynergyPackage> packages = new EnumMap<>(DeckSynergy.class);
        for (Map.Entry<DeckSynergy, List<DeckRole>> entry : COMPONENTS.entrySet()) {
            packages.put(entry.getKey(), new DeckSynergyPackage(
                    entry.getKey(),
                    entry.getValue(),
                    selectRoleCards(mainDeck, entry.getValue()),
                    selectRoleCards(commandZone, entry.getValue())
            ));
        }
        return Collections.unmodifiableMap(packages);
    }

    private static Map<DeckRole, Map<String, Integer>> selectRoleCards(DeckProfile profile, List<DeckRole> roles) {
        Map<DeckRole, Map<String, Integer>> cards = new EnumMap<>(DeckRole.class);
        for (DeckRole role : roles) {
            Map<String, Integer> roleCards = profile.getRoleCardCounts(role);
            if (!roleCards.isEmpty()) {
                cards.put(role, new LinkedHashMap<>(roleCards));
            }
        }
        return cards;
    }

    private static Map<DeckSynergy, List<DeckRole>> buildComponents() {
        Map<DeckSynergy, List<DeckRole>> components = new EnumMap<>(DeckSynergy.class);
        components.put(DeckSynergy.SACRIFICE, Arrays.asList(
                DeckRole.SACRIFICE_OUTLET,
                DeckRole.SACRIFICE_FODDER,
                DeckRole.SACRIFICE_FODDER_PROVIDER,
                DeckRole.DEATH_PAYOFF,
                DeckRole.GRAVEYARD_RECURSION
        ));
        components.put(DeckSynergy.GRAVEYARD, Arrays.asList(
                DeckRole.DISCARD_OR_SELF_MILL,
                DeckRole.GRAVEYARD_RECURSION,
                DeckRole.DEATH_PAYOFF
        ));
        components.put(DeckSynergy.SPELLS_MATTER, Arrays.asList(
                DeckRole.CARD_DRAW,
                DeckRole.REMOVAL,
                DeckRole.COUNTER_OR_PROTECTION,
                DeckRole.COMBAT_TRICK
        ));
        components.put(DeckSynergy.GO_WIDE, Arrays.asList(
                DeckRole.TOKEN_MAKER,
                DeckRole.SACRIFICE_FODDER_PROVIDER,
                DeckRole.COUNTER_OR_PROTECTION,
                DeckRole.COMBAT_TRICK
        ));
        components.put(DeckSynergy.LIFE_GAIN, Arrays.asList(
                DeckRole.LIFE_GAIN,
                DeckRole.LIFE_GAIN_PAYOFF
        ));
        components.put(DeckSynergy.PLUS_ONE_COUNTERS, Arrays.asList(
                DeckRole.PLUS_ONE_COUNTER_MAKER,
                DeckRole.PLUS_ONE_COUNTER_PAYOFF,
                DeckRole.COUNTER_OR_PROTECTION
        ));
        components.put(DeckSynergy.X_SPELLS, Arrays.asList(
                DeckRole.X_SPELL,
                DeckRole.X_SPELL_PAYOFF,
                DeckRole.RAMP,
                DeckRole.CARD_DRAW
        ));
        return Collections.unmodifiableMap(components);
    }
}
