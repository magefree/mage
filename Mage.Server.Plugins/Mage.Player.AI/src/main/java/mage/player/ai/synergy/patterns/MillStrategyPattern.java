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
 * AI: Detects mill strategy synergies (milling opponents as a win condition).
 * This is distinct from self-mill/graveyard strategies - the goal here is to
 * empty the opponent's library to win the game.
 *
 * Enablers (Mill Cards):
 * - Repeatable: Hedron Crab, Ruin Crab, Mesmeric Orb, Altar of the Brood
 * - One-shot: Glimpse the Unthinkable, Archive Trap, Mind Funeral
 * - Big mill: Traumatize, Maddening Cacophony, Fleet Swallower
 *
 * Amplifiers (Mill Doublers):
 * - Bruvac the Grandiloquent (doubles mill)
 * - Fraying Sanity (doubles mill on end step)
 * - Sphinx's Tutelage (mill on draw)
 *
 * Payoffs (Benefit from opponent's graveyard):
 * - Consuming Aberration (P/T = cards in opponent graveyards)
 * - Nemesis of Reason (attack trigger mills 10)
 * - Mindcrank + Duskmantle Guildmage (combo)
 *
 * @author duxbuse
 */
public class MillStrategyPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "mill-strategy";
    private static final String SYNERGY_NAME = "Mill Strategy";

    // Score bonuses
    private static final int BONUS_MILL_ONLY = 150;
    private static final int BONUS_MILL_WITH_AMPLIFIER = 500;
    private static final int BONUS_MILL_WITH_PAYOFF = 400;
    private static final int BONUS_MILL_DENSITY_HIGH = 600;
    private static final int BONUS_PER_ADDITIONAL_MILL_PIECE = 100;
    private static final int BONUS_BRUVAC_ACTIVE = 400;

    // Minimum mill cards to consider it a mill deck
    private static final int MIN_MILL_CARDS = 3;

    private final Set<String> repeatableMillers;
    private final Set<String> oneShotMillers;
    private final Set<String> bigMillers;
    private final Set<String> millAmplifiers;
    private final Set<String> millPayoffs;
    private final Set<String> millComboCards;
    private final Set<String> allMillCards;
    private final Set<String> allEnablers;

    public MillStrategyPattern() {
        // Repeatable mill effects (creatures/permanents that mill repeatedly)
        repeatableMillers = new HashSet<>();
        repeatableMillers.add("Hedron Crab");
        repeatableMillers.add("Ruin Crab");
        repeatableMillers.add("Mesmeric Orb");
        repeatableMillers.add("Altar of the Brood");
        repeatableMillers.add("Sphinx's Tutelage");
        repeatableMillers.add("Psychic Corrosion");
        repeatableMillers.add("Drowned Secrets");
        repeatableMillers.add("Teferi's Tutelage");
        repeatableMillers.add("Court of Cunning");
        repeatableMillers.add("Persistent Petitioners");
        repeatableMillers.add("Mindcrank");
        repeatableMillers.add("Keening Stone");
        repeatableMillers.add("Syr Konrad, the Grim");
        repeatableMillers.add("Undead Alchemist");

        // One-shot mill spells
        oneShotMillers = new HashSet<>();
        oneShotMillers.add("Glimpse the Unthinkable");
        oneShotMillers.add("Archive Trap");
        oneShotMillers.add("Mind Funeral");
        oneShotMillers.add("Breaking // Entering");
        oneShotMillers.add("Thought Scour");
        oneShotMillers.add("Mind Sculpt");
        oneShotMillers.add("Tome Scour");
        oneShotMillers.add("Didn't Say Please");
        oneShotMillers.add("Fractured Sanity");
        oneShotMillers.add("Visions of Beyond");
        oneShotMillers.add("Memory Erosion");
        oneShotMillers.add("Sanity Grinding");

        // Big mill effects
        bigMillers = new HashSet<>();
        bigMillers.add("Traumatize");
        bigMillers.add("Maddening Cacophony");
        bigMillers.add("Fleet Swallower");
        bigMillers.add("Tasha's Hideous Laughter");
        bigMillers.add("Cut Your Losses");
        bigMillers.add("Mind Grind");
        bigMillers.add("Startled Awake");
        bigMillers.add("Nemesis of Reason");
        bigMillers.add("Consuming Aberration");
        bigMillers.add("Mirko Vosk, Mind Drinker");
        bigMillers.add("Szadek, Lord of Secrets");
        bigMillers.add("Phenax, God of Deception");
        bigMillers.add("Zellix, Sanity Flayer");

        // Mill amplifiers (double or enhance mill)
        millAmplifiers = new HashSet<>();
        millAmplifiers.add("Bruvac the Grandiloquent");
        millAmplifiers.add("Fraying Sanity");
        millAmplifiers.add("Maddening Cacophony"); // Kicked version mills half
        millAmplifiers.add("Trepanation Blade");
        millAmplifiers.add("Sword of Body and Mind");

        // Mill payoffs (benefit from opponent's graveyard)
        millPayoffs = new HashSet<>();
        millPayoffs.add("Consuming Aberration");
        millPayoffs.add("Wight of Precinct Six");
        millPayoffs.add("Jace's Phantasm");
        millPayoffs.add("Vantress Gargoyle");
        millPayoffs.add("Oona, Queen of the Fae");
        millPayoffs.add("Mindshrieker");
        millPayoffs.add("Nighthowler"); // Can target opponent's graveyard
        millPayoffs.add("Duskmantle Guildmage");
        millPayoffs.add("Bloodchief Ascension");

        // Mill combo pieces
        millComboCards = new HashSet<>();
        millComboCards.add("Mindcrank");
        millComboCards.add("Duskmantle Guildmage");
        millComboCards.add("Bloodchief Ascension");
        millComboCards.add("Syr Konrad, the Grim");
        millComboCards.add("Painter's Servant");
        millComboCards.add("Grindstone");

        // Combine all mill cards
        allMillCards = new HashSet<>();
        allMillCards.addAll(repeatableMillers);
        allMillCards.addAll(oneShotMillers);
        allMillCards.addAll(bigMillers);

        // All enablers include mill cards and amplifiers
        allEnablers = new HashSet<>();
        allEnablers.addAll(allMillCards);
        allEnablers.addAll(millAmplifiers);
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
        return SynergyType.MILL_STRATEGY;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(allEnablers);
    }

    @Override
    public Set<String> getPayoffs() {
        Set<String> payoffs = new HashSet<>(millPayoffs);
        payoffs.addAll(millComboCards);
        return Collections.unmodifiableSet(payoffs);
    }

    @Override
    public Set<String> getAllPieces() {
        Set<String> all = new HashSet<>(allEnablers);
        all.addAll(millPayoffs);
        all.addAll(millComboCards);
        return all;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.MILL_STRATEGY);
        }

        Set<String> millCardsFound = new HashSet<>();
        Set<String> amplifiersFound = new HashSet<>();
        Set<String> payoffsFound = new HashSet<>();
        Set<String> millCardsOnBattlefield = new HashSet<>();
        Set<String> amplifiersOnBattlefield = new HashSet<>();
        Set<String> payoffsOnBattlefield = new HashSet<>();

        int millCardCount = 0;
        boolean hasBruvac = false;
        boolean hasFrayingSanity = false;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            if (allMillCards.contains(name)) {
                millCardsFound.add(name);
                millCardsOnBattlefield.add(name);
                millCardCount++;
            }
            if (millAmplifiers.contains(name)) {
                amplifiersFound.add(name);
                amplifiersOnBattlefield.add(name);
                if (name.equals("Bruvac the Grandiloquent")) {
                    hasBruvac = true;
                }
                if (name.equals("Fraying Sanity")) {
                    hasFrayingSanity = true;
                }
            }
            if (millPayoffs.contains(name) || millComboCards.contains(name)) {
                payoffsFound.add(name);
                payoffsOnBattlefield.add(name);
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (allMillCards.contains(name)) {
                millCardsFound.add(name);
                millCardCount++;
            }
            if (millAmplifiers.contains(name)) {
                amplifiersFound.add(name);
            }
            if (millPayoffs.contains(name) || millComboCards.contains(name)) {
                payoffsFound.add(name);
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (allMillCards.contains(name)) {
                millCardsFound.add(name);
                millCardCount++;
            }
            if (millAmplifiers.contains(name)) {
                amplifiersFound.add(name);
            }
            if (millPayoffs.contains(name) || millComboCards.contains(name)) {
                payoffsFound.add(name);
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allMillCards.contains(name)) {
                millCardsFound.add(name);
            }
            if (millAmplifiers.contains(name)) {
                amplifiersFound.add(name);
            }
            if (millPayoffs.contains(name) || millComboCards.contains(name)) {
                payoffsFound.add(name);
            }
        }

        // Need minimum mill cards to be considered a mill deck
        if (millCardCount < MIN_MILL_CARDS && millCardsFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.MILL_STRATEGY);
        }

        // Combine enablers
        Set<String> enablersFound = new HashSet<>(millCardsFound);
        enablersFound.addAll(amplifiersFound);

        Set<String> enablersOnBattlefield = new HashSet<>(millCardsOnBattlefield);
        enablersOnBattlefield.addAll(amplifiersOnBattlefield);

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        boolean hasMillInPlay = !millCardsOnBattlefield.isEmpty();
        boolean hasAmplifierInPlay = !amplifiersOnBattlefield.isEmpty();
        boolean hasPayoffInPlay = !payoffsOnBattlefield.isEmpty();
        boolean isHighDensityMill = millCardCount >= 8;

        if (hasMillInPlay) {
            state = SynergyState.ACTIVE;

            // Base bonus for having mill in play
            if (isHighDensityMill) {
                scoreBonus = BONUS_MILL_DENSITY_HIGH;
            } else {
                scoreBonus = BONUS_MILL_ONLY;
            }

            // Bonus for amplifier (Bruvac doubles everything!)
            if (hasAmplifierInPlay) {
                scoreBonus += BONUS_MILL_WITH_AMPLIFIER;
                if (hasBruvac) {
                    scoreBonus += BONUS_BRUVAC_ACTIVE;
                }
            }

            // Bonus for payoffs
            if (hasPayoffInPlay) {
                scoreBonus += BONUS_MILL_WITH_PAYOFF;
            }

            // Bonus for multiple mill pieces in play
            int piecesInPlay = millCardsOnBattlefield.size();
            if (piecesInPlay > 1) {
                scoreBonus += (piecesInPlay - 1) * BONUS_PER_ADDITIONAL_MILL_PIECE;
            }

            notes.append(String.format("Mill cards: %d (in play: %d), Amplifiers: %d, Payoffs: %d",
                    millCardCount, piecesInPlay, amplifiersOnBattlefield.size(), payoffsOnBattlefield.size()));
        } else if (!millCardsFound.isEmpty()) {
            if (millCardCount >= MIN_MILL_CARDS) {
                state = SynergyState.READY;
                scoreBonus = BONUS_MILL_ONLY;
                notes.append("Mill deck detected, waiting to deploy");
            } else {
                state = SynergyState.PARTIAL;
                scoreBonus = BONUS_MILL_ONLY / 2;
                notes.append("Some mill cards found");
            }
        } else {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_MILL_ONLY / 2;
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.MILL_STRATEGY)
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
     * Check if a card is a repeatable miller.
     */
    public boolean isRepeatableMiller(String cardName) {
        return repeatableMillers.contains(cardName);
    }

    /**
     * Check if a card is a mill amplifier (like Bruvac).
     */
    public boolean isMillAmplifier(String cardName) {
        return millAmplifiers.contains(cardName);
    }

    /**
     * Check if a card is a mill payoff.
     */
    public boolean isMillPayoff(String cardName) {
        return millPayoffs.contains(cardName);
    }

    /**
     * Check if a card is part of a mill combo.
     */
    public boolean isMillCombo(String cardName) {
        return millComboCards.contains(cardName);
    }
}
