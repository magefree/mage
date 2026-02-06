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
 * AI: Detects sacrifice outlet (Aristocrats) synergies.
 * Free sacrifice outlets combined with death triggers create powerful value engines.
 * Protect both the sacrifice outlets and the death trigger payoffs.
 *
 * Enablers (Sacrifice Outlets - FREE):
 * - Creature Outlets: Viscera Seer, Carrion Feeder, Yahenni, Woe Strider
 * - Artifact Outlets: Ashnod's Altar, Phyrexian Altar, Altar of Dementia
 * - Enchantment Outlets: Goblin Bombardment
 *
 * Payoffs (Death Triggers):
 * - Drain Effects: Blood Artist, Zulaport Cutthroat, Cruel Celebrant
 * - Damage Effects: Judith, Mayhem Devil
 *
 * Fodder (Token Generators/Recursive Creatures):
 * - Bitterblossom, Gravecrawler, Bloodghast, Reassembling Skeleton
 *
 * @author duxbuse
 */
public class SacrificeOutletPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "sacrifice-outlet";
    private static final String SYNERGY_NAME = "Sacrifice Outlet (Aristocrats)";

    // Score bonuses
    private static final int BONUS_OUTLET_ONLY = 100;
    private static final int BONUS_OUTLET_WITH_TRIGGER = 500;
    private static final int BONUS_WITH_FODDER = 200;
    private static final int BONUS_PER_EXTRA_TRIGGER = 200;
    private static final int BONUS_ACTIVE_FULL_ENGINE = 800;

    private final Set<String> sacrificeOutlets;
    private final Set<String> deathTriggers;
    private final Set<String> fodder;
    private final Set<String> allPayoffs;

    public SacrificeOutletPattern() {
        // Free sacrifice outlets
        sacrificeOutlets = new HashSet<>();

        // Creature outlets
        sacrificeOutlets.add("Viscera Seer");
        sacrificeOutlets.add("Carrion Feeder");
        sacrificeOutlets.add("Yahenni, Undying Partisan");
        sacrificeOutlets.add("Woe Strider");
        sacrificeOutlets.add("Nantuko Husk");
        sacrificeOutlets.add("Bloodthrone Vampire");
        sacrificeOutlets.add("Yawgmoth, Thran Physician");
        sacrificeOutlets.add("Grimgrin, Corpse-Born");
        sacrificeOutlets.add("Ghoulcaller Gisa");
        sacrificeOutlets.add("Cartel Aristocrat");
        sacrificeOutlets.add("Falkenrath Aristocrat");
        sacrificeOutlets.add("Spawning Pit");

        // Artifact outlets
        sacrificeOutlets.add("Ashnod's Altar");
        sacrificeOutlets.add("Phyrexian Altar");
        sacrificeOutlets.add("Altar of Dementia");
        sacrificeOutlets.add("Blasting Station");
        sacrificeOutlets.add("Grinding Station");
        sacrificeOutlets.add("Phyrexian Tower");
        sacrificeOutlets.add("High Market");

        // Enchantment outlets
        sacrificeOutlets.add("Goblin Bombardment");
        sacrificeOutlets.add("Fanatical Devotion");

        // Death trigger payoffs
        deathTriggers = new HashSet<>();

        // Drain effects
        deathTriggers.add("Blood Artist");
        deathTriggers.add("Zulaport Cutthroat");
        deathTriggers.add("Cruel Celebrant");
        deathTriggers.add("Bastion of Remembrance");
        deathTriggers.add("Vindictive Vampire");
        deathTriggers.add("Falkenrath Noble");
        deathTriggers.add("Syr Konrad, the Grim");

        // Damage/value effects
        deathTriggers.add("Judith, the Scourge Diva");
        deathTriggers.add("Mayhem Devil");
        deathTriggers.add("Poison-Tip Archer");
        deathTriggers.add("Pitiless Plunderer");
        deathTriggers.add("Pawn of Ulamog");
        deathTriggers.add("Midnight Reaper");
        deathTriggers.add("Grim Haruspex");
        deathTriggers.add("Dark Prophecy");
        deathTriggers.add("Skullclamp");

        // Token generators and recursive creatures (fodder)
        fodder = new HashSet<>();

        // Token generators
        fodder.add("Bitterblossom");
        fodder.add("Dreadhorde Invasion");
        fodder.add("Ophiomancer");
        fodder.add("Awakening Zone");
        fodder.add("From Beyond");
        fodder.add("Assemble the Legion");
        fodder.add("Luminarch Ascension");
        fodder.add("Breeding Pit");

        // Recursive creatures
        fodder.add("Gravecrawler");
        fodder.add("Bloodghast");
        fodder.add("Reassembling Skeleton");
        fodder.add("Nether Traitor");
        fodder.add("Gutterbones");
        fodder.add("Tenacious Dead");
        fodder.add("Bloodsoaked Champion");

        // Creatures with built-in recursion
        fodder.add("Butcher Ghoul");
        fodder.add("Geralf's Messenger");
        fodder.add("Young Wolf");
        fodder.add("Strangleroot Geist");
        fodder.add("Kitchen Finks");
        fodder.add("Murderous Redcap");

        // All payoffs include both death triggers and fodder
        allPayoffs = new HashSet<>();
        allPayoffs.addAll(deathTriggers);
        allPayoffs.addAll(fodder);
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
        return SynergyType.SACRIFICE_OUTLET;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(sacrificeOutlets);
    }

    @Override
    public Set<String> getPayoffs() {
        return Collections.unmodifiableSet(allPayoffs);
    }

    @Override
    public Set<String> getAllPieces() {
        Set<String> all = new HashSet<>(sacrificeOutlets);
        all.addAll(allPayoffs);
        return all;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.SACRIFICE_OUTLET);
        }

        Set<String> outletsFound = new HashSet<>();
        Set<String> triggersFound = new HashSet<>();
        Set<String> fodderFound = new HashSet<>();
        Set<String> outletsOnBattlefield = new HashSet<>();
        Set<String> triggersOnBattlefield = new HashSet<>();
        Set<String> fodderOnBattlefield = new HashSet<>();

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (sacrificeOutlets.contains(name)) {
                outletsFound.add(name);
                outletsOnBattlefield.add(name);
            }
            if (deathTriggers.contains(name)) {
                triggersFound.add(name);
                triggersOnBattlefield.add(name);
            }
            if (fodder.contains(name)) {
                fodderFound.add(name);
                fodderOnBattlefield.add(name);
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (sacrificeOutlets.contains(name)) {
                outletsFound.add(name);
            }
            if (deathTriggers.contains(name)) {
                triggersFound.add(name);
            }
            if (fodder.contains(name)) {
                fodderFound.add(name);
            }
        }

        // Check library and graveyard for potential
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (sacrificeOutlets.contains(name)) {
                outletsFound.add(name);
            }
            if (deathTriggers.contains(name)) {
                triggersFound.add(name);
            }
            if (fodder.contains(name)) {
                fodderFound.add(name);
            }
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (sacrificeOutlets.contains(name)) {
                outletsFound.add(name);
            }
            if (deathTriggers.contains(name)) {
                triggersFound.add(name);
            }
            if (fodder.contains(name)) {
                fodderFound.add(name);
            }
        }

        // Combine payoffs
        Set<String> payoffsFound = new HashSet<>();
        payoffsFound.addAll(triggersFound);
        payoffsFound.addAll(fodderFound);

        Set<String> payoffsOnBattlefield = new HashSet<>();
        payoffsOnBattlefield.addAll(triggersOnBattlefield);
        payoffsOnBattlefield.addAll(fodderOnBattlefield);

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        if (outletsFound.isEmpty()) {
            if (triggersFound.isEmpty() && fodderFound.isEmpty()) {
                return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.SACRIFICE_OUTLET);
            }
            state = SynergyState.PAYOFF_ONLY;
        } else if (triggersFound.isEmpty() && fodderFound.isEmpty()) {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_OUTLET_ONLY;
        } else {
            // Have both outlet and payoffs
            boolean outletInPlay = !outletsOnBattlefield.isEmpty();
            boolean triggerInPlay = !triggersOnBattlefield.isEmpty();
            boolean fodderInPlay = !fodderOnBattlefield.isEmpty();

            if (outletInPlay && triggerInPlay) {
                // Full engine active
                state = SynergyState.ACTIVE;
                scoreBonus = BONUS_OUTLET_WITH_TRIGGER;

                // Bonus per additional death trigger
                int extraTriggers = triggersOnBattlefield.size() - 1;
                if (extraTriggers > 0) {
                    scoreBonus += extraTriggers * BONUS_PER_EXTRA_TRIGGER;
                }

                // Bonus for having fodder
                if (fodderInPlay) {
                    scoreBonus += BONUS_WITH_FODDER;
                }

                // Full engine bonus
                if (fodderInPlay && triggersOnBattlefield.size() >= 2) {
                    scoreBonus = Math.max(scoreBonus, BONUS_ACTIVE_FULL_ENGINE);
                }

                notes.append(String.format("Outlet: %s + Triggers: %d + Fodder: %d",
                        outletsOnBattlefield, triggersOnBattlefield.size(), fodderOnBattlefield.size()));
            } else if (outletInPlay) {
                state = SynergyState.READY;
                scoreBonus = BONUS_OUTLET_WITH_TRIGGER / 2;
                notes.append("Outlet in play, waiting for triggers");
            } else {
                state = SynergyState.PARTIAL;
                scoreBonus = BONUS_OUTLET_ONLY + 50;
                notes.append("Have pieces, need to deploy");
            }
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.SACRIFICE_OUTLET)
                .state(state)
                .enablersFound(outletsFound)
                .payoffsFound(payoffsFound)
                .enablersOnBattlefield(outletsOnBattlefield)
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
     * Check if a card is a death trigger payoff.
     */
    public boolean isDeathTrigger(String cardName) {
        return deathTriggers.contains(cardName);
    }

    /**
     * Check if a card is fodder (recursive/token generator).
     */
    public boolean isFodder(String cardName) {
        return fodder.contains(cardName);
    }

    /**
     * Check if a card is a sacrifice outlet.
     */
    public boolean isSacrificeOutlet(String cardName) {
        return sacrificeOutlets.contains(cardName);
    }
}
