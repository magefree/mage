package mage.player.ai.synergy.patterns;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.synergy.SynergyDetectionResult;
import mage.player.ai.synergy.SynergyDetectionResult.SynergyState;
import mage.player.ai.synergy.SynergyPattern;
import mage.player.ai.synergy.SynergyType;
import mage.players.Player;

import java.util.*;

/**
 * AI: Detects +1/+1 counter synergies.
 * Cards that double or add extra counters. Protect the doublers.
 *
 * Enablers (Counter Doublers/Adders):
 * - Add Extra: Hardened Scales, Winding Constrictor, Branching Evolution
 * - Double All: Doubling Season, Primal Vigor
 * - Counter Generators: Metallic Mimic, Mikaeus the Lunarch
 *
 * Payoffs: Creatures that enter with counters or benefit from counters.
 *
 * @author duxbuse
 */
public class CounterSynergyPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "counter-synergy";
    private static final String SYNERGY_NAME = "Counter Synergy";

    // Score bonuses
    private static final int BONUS_DOUBLER_ONLY = 100;
    private static final int BONUS_DOUBLER_WITH_PAYOFFS = 500;
    private static final int BONUS_DOUBLING_SEASON = 600;
    private static final int BONUS_PER_ADDITIONAL_DOUBLER = 300;
    private static final int BONUS_COUNTER_THEME_ACTIVE = 400;

    // Minimum counter-matters cards needed
    private static final int MIN_COUNTER_CARDS = 5;

    private final Set<String> counterDoublers;
    private final Set<String> counterGenerators;
    private final Set<String> counterPayoffs;
    private final Set<String> allEnablers;

    public CounterSynergyPattern() {
        // Counter doublers (add extra counters)
        counterDoublers = new HashSet<>();

        // Add one extra counter
        counterDoublers.add("Hardened Scales");
        counterDoublers.add("Winding Constrictor");
        counterDoublers.add("Branching Evolution");
        counterDoublers.add("Pir, Imaginative Rascal");
        counterDoublers.add("Vorinclex, Monstrous Raider");
        counterDoublers.add("Corpsejack Menace");
        counterDoublers.add("Kalonian Hydra");

        // Double all counters
        counterDoublers.add("Doubling Season");
        counterDoublers.add("Primal Vigor");

        // Counter generators (distribute counters)
        counterGenerators = new HashSet<>();
        counterGenerators.add("Metallic Mimic");
        counterGenerators.add("Mikaeus, the Lunarch");
        counterGenerators.add("Gavony Township");
        counterGenerators.add("Oran-Rief, the Vastwood");
        counterGenerators.add("Animation Module");
        counterGenerators.add("Cathars' Crusade");
        counterGenerators.add("Armorcraft Judge");
        counterGenerators.add("Rishkar, Peema Renegade");
        counterGenerators.add("Inspiring Call");
        counterGenerators.add("Abzan Falconer");
        counterGenerators.add("Abzan Battle Priest");
        counterGenerators.add("Mer-Ek Nightblade");

        // Counter payoffs (benefit from counters)
        counterPayoffs = new HashSet<>();

        // Creatures that grow or benefit from counters
        counterPayoffs.add("Managorger Hydra");
        counterPayoffs.add("Champion of Lambholt");
        counterPayoffs.add("Kalonian Hydra");
        counterPayoffs.add("Forgotten Ancient");
        counterPayoffs.add("Triskelion");
        counterPayoffs.add("Walking Ballista");
        counterPayoffs.add("Hangarback Walker");
        counterPayoffs.add("Stonecoil Serpent");
        counterPayoffs.add("Hooded Hydra");

        // Modular/Arcbound creatures
        counterPayoffs.add("Arcbound Ravager");
        counterPayoffs.add("Arcbound Worker");
        counterPayoffs.add("Arcbound Crusher");
        counterPayoffs.add("Steel Overseer");

        // Hydras (enter with counters)
        counterPayoffs.add("Hydroid Krasis");
        counterPayoffs.add("Primordial Hydra");
        counterPayoffs.add("Genesis Hydra");
        counterPayoffs.add("Voracious Hydra");
        counterPayoffs.add("Polukranos, World Eater");

        // Counter matters
        counterPayoffs.add("Tuskguard Captain");
        counterPayoffs.add("Elite Scaleguard");
        counterPayoffs.add("Avatar of the Resolute");
        counterPayoffs.add("Experiment One");
        counterPayoffs.add("Pelt Collector");

        // Combine all enablers
        allEnablers = new HashSet<>();
        allEnablers.addAll(counterDoublers);
        allEnablers.addAll(counterGenerators);
    }

    @Override
    public String getSynergyId() {
        return SYNERGY_ID;
    }

    @Override
    public String getName() {
        return SYNERGY_NAME;
    }

    @Override
    public SynergyType getType() {
        return SynergyType.COUNTER_SYNERGY;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(allEnablers);
    }

    @Override
    public Set<String> getPayoffs() {
        return Collections.unmodifiableSet(counterPayoffs);
    }

    @Override
    public Set<String> getAllPieces() {
        Set<String> all = new HashSet<>(allEnablers);
        all.addAll(counterPayoffs);
        return all;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.COUNTER_SYNERGY);
        }

        Set<String> enablersFound = new HashSet<>();
        Set<String> payoffsFound = new HashSet<>();
        Set<String> enablersOnBattlefield = new HashSet<>();
        Set<String> payoffsOnBattlefield = new HashSet<>();
        boolean hasDoublingSeasonInPlay = false;
        int counterCardsTotal = 0;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            if (counterDoublers.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
                counterCardsTotal++;
                if (name.equals("Doubling Season")) {
                    hasDoublingSeasonInPlay = true;
                }
            }
            if (counterGenerators.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
                counterCardsTotal++;
            }
            if (counterPayoffs.contains(name)) {
                payoffsFound.add(name);
                payoffsOnBattlefield.add(name);
                counterCardsTotal++;
            }

            // Count permanents with +1/+1 counters
            if (permanent.isCreature(game) && permanent.getCounters(game).getCount("p1p1") > 0) {
                counterCardsTotal++;
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
                counterCardsTotal++;
            }
            if (counterPayoffs.contains(name)) {
                payoffsFound.add(name);
                counterCardsTotal++;
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (counterPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (counterPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
        }

        if (enablersFound.isEmpty() && payoffsFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.COUNTER_SYNERGY);
        }

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        boolean hasEnablerInPlay = !enablersOnBattlefield.isEmpty();
        boolean hasPayoffInPlay = !payoffsOnBattlefield.isEmpty();
        boolean hasCounterTheme = counterCardsTotal >= MIN_COUNTER_CARDS;

        if (hasEnablerInPlay && hasCounterTheme) {
            state = SynergyState.ACTIVE;

            // Base bonus for active synergy
            scoreBonus = BONUS_DOUBLER_WITH_PAYOFFS;

            // Extra bonus for Doubling Season
            if (hasDoublingSeasonInPlay) {
                scoreBonus = Math.max(scoreBonus, BONUS_DOUBLING_SEASON);
            }

            // Bonus for multiple enablers
            int enablerCount = enablersOnBattlefield.size();
            if (enablerCount > 1) {
                scoreBonus += (enablerCount - 1) * BONUS_PER_ADDITIONAL_DOUBLER;
            }

            // Bonus for counter theme active
            if (hasPayoffInPlay) {
                scoreBonus += BONUS_COUNTER_THEME_ACTIVE;
            }

            notes.append(String.format("Enablers: %d, Payoffs: %d, Counter cards: %d",
                    enablerCount, payoffsOnBattlefield.size(), counterCardsTotal));
        } else if (hasEnablerInPlay) {
            state = SynergyState.READY;
            scoreBonus = BONUS_DOUBLER_ONLY + 150;
            notes.append("Counter doubler in play, building theme");
        } else if (!enablersFound.isEmpty() && !payoffsFound.isEmpty()) {
            state = SynergyState.PARTIAL;
            scoreBonus = BONUS_DOUBLER_ONLY + 50;
            notes.append("Have pieces, need to deploy");
        } else if (!enablersFound.isEmpty()) {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_DOUBLER_ONLY;
        } else {
            state = SynergyState.PAYOFF_ONLY;
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.COUNTER_SYNERGY)
                .state(state)
                .enablersFound(enablersFound)
                .payoffsFound(payoffsFound)
                .enablersOnBattlefield(enablersOnBattlefield)
                .payoffsOnBattlefield(payoffsOnBattlefield)
                .scoreBonus(scoreBonus)
                .notes(notes.toString())
                .build();
    }

    @Override
    public boolean isActive(Game game, UUID playerId) {
        SynergyDetectionResult result = detectSynergy(game, playerId);
        return result.getState() == SynergyState.ACTIVE;
    }

    @Override
    public int calculateSynergyBonus(Game game, UUID playerId) {
        SynergyDetectionResult result = detectSynergy(game, playerId);
        return result.getScoreBonus();
    }

    /**
     * Check if a card is a counter doubler.
     */
    public boolean isCounterDoubler(String cardName) {
        return counterDoublers.contains(cardName);
    }

    /**
     * Check if a card is a counter generator.
     */
    public boolean isCounterGenerator(String cardName) {
        return counterGenerators.contains(cardName);
    }

    /**
     * Check if a card is a counter payoff.
     */
    public boolean isCounterPayoff(String cardName) {
        return counterPayoffs.contains(cardName);
    }
}
