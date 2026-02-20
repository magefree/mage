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
 * AI: Detects artifact synergies.
 * Cards that trigger on artifact ETB or reduce artifact costs.
 *
 * Enablers (Artifact Payoffs):
 * - ETB Triggers: Grinding Station, Sai, Mirrodin Besieged
 * - Cost Reducers: Foundry Inspector, Etherium Sculptor, Jhoira's Familiar
 * - Affinity: Emry, Thought Monitor
 *
 * Payoffs: High artifact count in deck.
 *
 * @author duxbuse
 */
public class ArtifactSynergyPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "artifact-synergy";
    private static final String SYNERGY_NAME = "Artifact Synergy";

    // Score bonuses
    private static final int BONUS_PAYOFF_ONLY = 100;
    private static final int BONUS_WITH_10_ARTIFACTS = 400;
    private static final int BONUS_WITH_20_ARTIFACTS = 600;
    private static final int BONUS_COST_REDUCER = 300;
    private static final int BONUS_PER_ADDITIONAL_PAYOFF = 200;

    // Minimum artifacts needed
    private static final int MIN_ARTIFACTS = 10;

    private final Set<String> etbTriggers;
    private final Set<String> costReducers;
    private final Set<String> affinityCards;
    private final Set<String> artifactPayoffs;
    private final Set<String> allEnablers;

    public ArtifactSynergyPattern() {
        // ETB triggers (trigger on artifact entering)
        etbTriggers = new HashSet<>();
        etbTriggers.add("Grinding Station");
        etbTriggers.add("Mirrodin Besieged");
        etbTriggers.add("Sai, Master Thopterist");
        etbTriggers.add("Efficient Construction");
        etbTriggers.add("Genesis Chamber");
        etbTriggers.add("Vedalken Archmage");
        etbTriggers.add("Monastery Mentor"); // Historic triggers
        etbTriggers.add("Teshar, Ancestor's Apostle");
        etbTriggers.add("Riddlesmith");
        etbTriggers.add("Reckless Fireweaver");
        etbTriggers.add("Glaze Fiend");

        // Cost reducers
        costReducers = new HashSet<>();
        costReducers.add("Foundry Inspector");
        costReducers.add("Etherium Sculptor");
        costReducers.add("Jhoira's Familiar");
        costReducers.add("Cloud Key");
        costReducers.add("Helm of Awakening");
        costReducers.add("Semblance Anvil");
        costReducers.add("Ugin, the Ineffable");

        // Affinity and artifact cost reduction
        affinityCards = new HashSet<>();
        affinityCards.add("Emry, Lurker of the Loch");
        affinityCards.add("Thought Monitor");
        affinityCards.add("Myr Enforcer");
        affinityCards.add("Frogmite");
        affinityCards.add("Sojourner's Companion");
        affinityCards.add("Metalwork Colossus");
        affinityCards.add("Mycosynth Golem");

        // Artifact payoffs
        artifactPayoffs = new HashSet<>();

        // Value generators
        artifactPayoffs.add("Urza, Lord High Artificer");
        artifactPayoffs.add("Master of Etherium");
        artifactPayoffs.add("Tezzeret, Agent of Bolas");
        artifactPayoffs.add("Tezzeret the Seeker");
        artifactPayoffs.add("Karn, Scion of Urza");
        artifactPayoffs.add("Cranial Plating");
        artifactPayoffs.add("Nettlecyst");
        artifactPayoffs.add("All That Glitters");
        artifactPayoffs.add("Ensoul Artifact");

        // Artifact synergy creatures
        artifactPayoffs.add("Myr Retriever");
        artifactPayoffs.add("Scrap Trawler");
        artifactPayoffs.add("Workshop Assistant");
        artifactPayoffs.add("Junk Diver");
        artifactPayoffs.add("Krark-Clan Ironworks");
        artifactPayoffs.add("Spine of Ish Sah");
        artifactPayoffs.add("Ichor Wellspring");
        artifactPayoffs.add("Mycosynth Wellspring");

        // Combine all enablers
        allEnablers = new HashSet<>();
        allEnablers.addAll(etbTriggers);
        allEnablers.addAll(costReducers);
        allEnablers.addAll(affinityCards);
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
        return SynergyType.ARTIFACT_SYNERGY;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(allEnablers);
    }

    @Override
    public Set<String> getPayoffs() {
        return Collections.unmodifiableSet(artifactPayoffs);
    }

    @Override
    public Set<String> getAllPieces() {
        Set<String> all = new HashSet<>(allEnablers);
        all.addAll(artifactPayoffs);
        return all;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.ARTIFACT_SYNERGY);
        }

        Set<String> enablersFound = new HashSet<>();
        Set<String> payoffsFound = new HashSet<>();
        Set<String> enablersOnBattlefield = new HashSet<>();
        Set<String> payoffsOnBattlefield = new HashSet<>();
        Set<String> costReducersInPlay = new HashSet<>();
        int artifactCountTotal = 0;
        int artifactCountOnBattlefield = 0;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            if (etbTriggers.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
            }
            if (costReducers.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
                costReducersInPlay.add(name);
            }
            if (affinityCards.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
            }
            if (artifactPayoffs.contains(name)) {
                payoffsFound.add(name);
                payoffsOnBattlefield.add(name);
            }

            // Count artifacts
            if (permanent.isArtifact(game)) {
                artifactCountTotal++;
                artifactCountOnBattlefield++;
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (artifactPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (card.isArtifact(game)) {
                artifactCountTotal++;
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (artifactPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (card.isArtifact(game)) {
                artifactCountTotal++;
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (artifactPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (card.isArtifact(game)) {
                artifactCountTotal++;
            }
        }

        if (enablersFound.isEmpty() && payoffsFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.ARTIFACT_SYNERGY);
        }

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        boolean hasEnablerInPlay = !enablersOnBattlefield.isEmpty();
        boolean hasEnoughArtifacts = artifactCountTotal >= MIN_ARTIFACTS;

        if (hasEnablerInPlay && hasEnoughArtifacts) {
            state = SynergyState.ACTIVE;

            // Base bonus based on artifact count
            if (artifactCountTotal >= 20) {
                scoreBonus = BONUS_WITH_20_ARTIFACTS;
            } else {
                scoreBonus = BONUS_WITH_10_ARTIFACTS;
            }

            // Bonus for cost reducers
            if (!costReducersInPlay.isEmpty()) {
                scoreBonus += BONUS_COST_REDUCER;
            }

            // Bonus for multiple enablers
            int enablerCount = enablersOnBattlefield.size();
            if (enablerCount > 1) {
                scoreBonus += (enablerCount - 1) * BONUS_PER_ADDITIONAL_PAYOFF;
            }

            notes.append(String.format("Enablers: %d, Artifacts: %d (battlefield: %d)",
                    enablerCount, artifactCountTotal, artifactCountOnBattlefield));
        } else if (hasEnablerInPlay) {
            state = SynergyState.READY;
            scoreBonus = BONUS_PAYOFF_ONLY + 100;
            notes.append("Enabler in play, building artifact count");
        } else if (hasEnoughArtifacts) {
            state = SynergyState.PARTIAL;
            scoreBonus = BONUS_PAYOFF_ONLY;
            notes.append("Have artifacts, need enabler");
        } else if (!enablersFound.isEmpty()) {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_PAYOFF_ONLY;
        } else {
            state = SynergyState.PAYOFF_ONLY;
        }

        // Add artifact counts to payoffs
        Set<String> artifactsAsPayoffs = new HashSet<>(payoffsFound);
        artifactsAsPayoffs.add(artifactCountTotal + " artifacts in deck");

        Set<String> artifactsOnBattlefieldSet = new HashSet<>(payoffsOnBattlefield);
        if (artifactCountOnBattlefield > 0) {
            artifactsOnBattlefieldSet.add(artifactCountOnBattlefield + " artifacts");
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.ARTIFACT_SYNERGY)
                .state(state)
                .enablersFound(enablersFound)
                .payoffsFound(artifactsAsPayoffs)
                .enablersOnBattlefield(enablersOnBattlefield)
                .payoffsOnBattlefield(artifactsOnBattlefieldSet)
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
     * Check if a card is an ETB trigger.
     */
    public boolean isEtbTrigger(String cardName) {
        return etbTriggers.contains(cardName);
    }

    /**
     * Check if a card is a cost reducer.
     */
    public boolean isCostReducer(String cardName) {
        return costReducers.contains(cardName);
    }

    /**
     * Check if a card has affinity.
     */
    public boolean isAffinityCard(String cardName) {
        return affinityCards.contains(cardName);
    }
}
