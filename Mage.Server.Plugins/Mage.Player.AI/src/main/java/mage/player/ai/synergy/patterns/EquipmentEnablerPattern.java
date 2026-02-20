package mage.player.ai.synergy.patterns;

import mage.cards.Card;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.synergy.SynergyDetectionResult;
import mage.player.ai.synergy.SynergyDetectionResult.SynergyState;
import mage.player.ai.synergy.SynergyPattern;
import mage.player.ai.synergy.SynergyType;
import mage.players.Player;

import java.util.*;

/**
 * AI: Detects equipment enabler synergies.
 * Equipment enablers reduce or eliminate equip costs, making expensive equipment
 * like Colossus Hammer much more powerful.
 *
 * Enablers:
 * - Puresteel Paladin (Metalcraft: equip {0})
 * - Sigarda's Aid (equip at instant speed, free on ETB)
 * - Brass Squire (tap to equip for free)
 * - Ardenn, Intrepid Archaeologist (free attach on combat)
 * - Stonehewer Giant (searches and attaches)
 * - Hammer of Nazahn (auto-attaches equipment)
 * - Kazuul's Toll Collector (equip at instant speed during combat)
 *
 * High-value payoffs (equipment with expensive equip costs):
 * - Colossus Hammer (equip 8, +10/+10)
 * - Argentum Armor (equip 6, destroys permanent on attack)
 * - Worldslayer (equip 5, destroys all other permanents)
 * - Assault Suit (equip 3, can't be sacrificed)
 * - Helm of the Host (equip 5, creates token copies)
 *
 * @author duxbuse
 */
public class EquipmentEnablerPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "equipment-enabler";
    private static final String SYNERGY_NAME = "Equipment Enabler";

    // Score bonuses
    private static final int BONUS_ENABLER_ONLY = 100;              // Have enabler but no expensive equipment
    private static final int BONUS_ENABLER_WITH_PAYOFF = 300;       // Have enabler + expensive equipment somewhere
    private static final int BONUS_ENABLER_ON_BATTLEFIELD = 500;    // Enabler in play + expensive equipment anywhere
    private static final int BONUS_ACTIVE_SYNERGY = 800;            // Enabler in play + expensive equipment in play
    private static final int BONUS_PER_EXTRA_EQUIPMENT = 100;       // Bonus per additional expensive equipment

    // Equip cost threshold for "expensive" equipment
    private static final int EXPENSIVE_EQUIP_COST = 3;

    private final Set<String> enablers;
    private final Set<String> highValuePayoffs;

    public EquipmentEnablerPattern() {
        // Initialize enablers
        enablers = new HashSet<>();
        enablers.add("Puresteel Paladin");
        enablers.add("Sigarda's Aid");
        enablers.add("Brass Squire");
        enablers.add("Ardenn, Intrepid Archaeologist");
        enablers.add("Stonehewer Giant");
        enablers.add("Hammer of Nazahn");
        enablers.add("Kazuul's Toll Collector");
        enablers.add("Magnetic Theft");
        enablers.add("Fighter Class");

        // Initialize high-value payoffs (equipment with high equip costs)
        highValuePayoffs = new HashSet<>();
        highValuePayoffs.add("Colossus Hammer");       // Equip 8
        highValuePayoffs.add("Argentum Armor");        // Equip 6
        highValuePayoffs.add("Worldslayer");           // Equip 5
        highValuePayoffs.add("Helm of the Host");      // Equip 5
        highValuePayoffs.add("Assault Suit");          // Equip 3
        highValuePayoffs.add("Batterskull");           // Equip 5
        highValuePayoffs.add("Kaldra Compleat");       // Equip 7
        highValuePayoffs.add("Embercleave");           // Equip 3
        highValuePayoffs.add("Shadowspear");           // Equip 2 but very impactful
        highValuePayoffs.add("Sword of Feast and Famine");
        highValuePayoffs.add("Sword of Fire and Ice");
        highValuePayoffs.add("Sword of Light and Shadow");
        highValuePayoffs.add("Sword of War and Peace");
        highValuePayoffs.add("Sword of Hearth and Home");
        highValuePayoffs.add("Sword of Forge and Frontier");
        highValuePayoffs.add("Sword of Truth and Justice");
        highValuePayoffs.add("Sword of Sinew and Steel");
        highValuePayoffs.add("Sword of Body and Mind");
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
        return SynergyType.EQUIPMENT_ENABLER;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(enablers);
    }

    @Override
    public Set<String> getPayoffs() {
        return Collections.unmodifiableSet(highValuePayoffs);
    }

    @Override
    public Set<String> getAllPieces() {
        Set<String> all = new HashSet<>(enablers);
        all.addAll(highValuePayoffs);
        return all;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.EQUIPMENT_ENABLER);
        }

        Set<String> enablersFound = new HashSet<>();
        Set<String> payoffsFound = new HashSet<>();
        Set<String> enablersOnBattlefield = new HashSet<>();
        Set<String> payoffsOnBattlefield = new HashSet<>();
        int expensiveEquipmentCount = 0;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (enablers.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
            }
            if (highValuePayoffs.contains(name)) {
                payoffsFound.add(name);
                payoffsOnBattlefield.add(name);
                expensiveEquipmentCount++;
            }
            // Also check for any equipment with expensive equip cost
            if (permanent.hasSubtype(SubType.EQUIPMENT, game)) {
                if (isExpensiveEquipment(game, permanent)) {
                    payoffsFound.add(name);
                    payoffsOnBattlefield.add(name);
                    expensiveEquipmentCount++;
                }
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (enablers.contains(name)) {
                enablersFound.add(name);
            }
            if (highValuePayoffs.contains(name)) {
                payoffsFound.add(name);
                expensiveEquipmentCount++;
            }
            if (card.hasSubtype(SubType.EQUIPMENT, game)) {
                if (isExpensiveEquipmentCard(game, card)) {
                    payoffsFound.add(name);
                    expensiveEquipmentCount++;
                }
            }
        }

        // Check library and graveyard for potential
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (enablers.contains(name)) {
                enablersFound.add(name);
            }
            if (highValuePayoffs.contains(name)) {
                payoffsFound.add(name);
            }
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (enablers.contains(name)) {
                enablersFound.add(name);
            }
            if (highValuePayoffs.contains(name)) {
                payoffsFound.add(name);
            }
        }

        // Determine state
        SynergyState state;
        int scoreBonus = 0;

        if (enablersFound.isEmpty() && payoffsFound.isEmpty()) {
            state = SynergyState.NOT_DETECTED;
        } else if (enablersFound.isEmpty()) {
            state = SynergyState.PAYOFF_ONLY;
        } else if (payoffsFound.isEmpty()) {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_ENABLER_ONLY;
        } else if (!enablersOnBattlefield.isEmpty() && !payoffsOnBattlefield.isEmpty()) {
            state = SynergyState.ACTIVE;
            scoreBonus = BONUS_ACTIVE_SYNERGY + (expensiveEquipmentCount - 1) * BONUS_PER_EXTRA_EQUIPMENT;
        } else if (!enablersOnBattlefield.isEmpty()) {
            state = SynergyState.READY;
            scoreBonus = BONUS_ENABLER_ON_BATTLEFIELD + (expensiveEquipmentCount * BONUS_PER_EXTRA_EQUIPMENT / 2);
        } else {
            state = SynergyState.PARTIAL;
            scoreBonus = BONUS_ENABLER_WITH_PAYOFF;
        }

        String notes = String.format("Found %d enablers, %d equipment payoffs",
                enablersFound.size(), payoffsFound.size());

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.EQUIPMENT_ENABLER)
                .state(state)
                .enablersFound(enablersFound)
                .payoffsFound(payoffsFound)
                .enablersOnBattlefield(enablersOnBattlefield)
                .payoffsOnBattlefield(payoffsOnBattlefield)
                .scoreBonus(scoreBonus)
                .notes(notes)
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
     * Check if a permanent is expensive equipment (equip cost >= threshold).
     */
    private boolean isExpensiveEquipment(Game game, Permanent permanent) {
        if (!permanent.hasSubtype(SubType.EQUIPMENT, game)) {
            return false;
        }
        // Check if it's in our known high-value list
        if (highValuePayoffs.contains(permanent.getName())) {
            return true;
        }
        // Otherwise check actual equip cost from abilities
        // For simplicity, we'll rely on the hardcoded list for now
        return false;
    }

    /**
     * Check if a card is expensive equipment (equip cost >= threshold).
     */
    private boolean isExpensiveEquipmentCard(Game game, Card card) {
        if (!card.hasSubtype(SubType.EQUIPMENT, game)) {
            return false;
        }
        // Check if it's in our known high-value list
        if (highValuePayoffs.contains(card.getName())) {
            return true;
        }
        return false;
    }
}
