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
 * AI: Detects tribal lord synergies.
 * Lords give +1/+1 and abilities to creatures of specific types.
 * The more tribal creatures in play, the more valuable the lord becomes.
 *
 * Supported tribes:
 * - Merfolk: Lord of Atlantis, Master of the Pearl Trident, etc.
 * - Elf: Elvish Archdruid, Elvish Champion, etc.
 * - Zombie: Lord of the Undead, Cemetery Reaper, etc.
 * - Vampire: Captivating Vampire, Stromkirk Captain, etc.
 * - Goblin: Goblin Chieftain, Goblin King, etc.
 * - Knight: Knight Exemplar
 * - Human: Mayor of Avabruck
 * - Warrior: Chief of the Edge
 *
 * @author duxbuse
 */
public class TribalLordPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "tribal-lord";
    private static final String SYNERGY_NAME = "Tribal Lord";

    // Score bonuses
    private static final int BONUS_LORD_WITH_3_CREATURES = 400;
    private static final int BONUS_LORD_WITH_5_CREATURES = 600;
    private static final int BONUS_LORD_WITH_8_CREATURES = 800;
    private static final int BONUS_PER_ADDITIONAL_LORD = 200;

    // Minimum tribal creatures needed for synergy to activate
    private static final int MIN_TRIBAL_CREATURES = 3;

    // Map of lord names to the SubType they buff
    private final Map<String, SubType> lordToTribe;
    private final Set<String> allLords;

    public TribalLordPattern() {
        lordToTribe = new HashMap<>();

        // Merfolk Lords
        lordToTribe.put("Lord of Atlantis", SubType.MERFOLK);
        lordToTribe.put("Master of the Pearl Trident", SubType.MERFOLK);
        lordToTribe.put("Merrow Reejerey", SubType.MERFOLK);
        lordToTribe.put("Merfolk Sovereign", SubType.MERFOLK);
        lordToTribe.put("Kopala, Warden of Waves", SubType.MERFOLK);
        lordToTribe.put("Svyelun of Sea and Sky", SubType.MERFOLK);

        // Elf Lords
        lordToTribe.put("Elvish Archdruid", SubType.ELF);
        lordToTribe.put("Elvish Champion", SubType.ELF);
        lordToTribe.put("Eladamri, Lord of Leaves", SubType.ELF);
        lordToTribe.put("Joraga Warcaller", SubType.ELF);
        lordToTribe.put("Dwynen, Gilt-Leaf Daen", SubType.ELF);
        lordToTribe.put("Imperious Perfect", SubType.ELF);
        lordToTribe.put("Elvish Clancaller", SubType.ELF);

        // Zombie Lords
        lordToTribe.put("Lord of the Undead", SubType.ZOMBIE);
        lordToTribe.put("Lord of the Accursed", SubType.ZOMBIE);
        lordToTribe.put("Zombie Master", SubType.ZOMBIE);
        lordToTribe.put("Cemetery Reaper", SubType.ZOMBIE);
        lordToTribe.put("Diregraf Captain", SubType.ZOMBIE);
        lordToTribe.put("Death Baron", SubType.ZOMBIE);
        lordToTribe.put("Undead Warchief", SubType.ZOMBIE);

        // Vampire Lords
        lordToTribe.put("Captivating Vampire", SubType.VAMPIRE);
        lordToTribe.put("Stromkirk Captain", SubType.VAMPIRE);
        lordToTribe.put("Legion Lieutenant", SubType.VAMPIRE);
        lordToTribe.put("Vampire Nocturnus", SubType.VAMPIRE);
        lordToTribe.put("Bloodline Keeper", SubType.VAMPIRE);

        // Goblin Lords
        lordToTribe.put("Goblin Chieftain", SubType.GOBLIN);
        lordToTribe.put("Goblin King", SubType.GOBLIN);
        lordToTribe.put("Mad Auntie", SubType.GOBLIN);
        lordToTribe.put("Goblin Warchief", SubType.GOBLIN);
        lordToTribe.put("Goblin Trashmaster", SubType.GOBLIN);
        lordToTribe.put("Krenko, Mob Boss", SubType.GOBLIN);

        // Knight Lords
        lordToTribe.put("Knight Exemplar", SubType.KNIGHT);
        lordToTribe.put("Valiant Knight", SubType.KNIGHT);

        // Human Lords
        lordToTribe.put("Mayor of Avabruck", SubType.HUMAN);
        lordToTribe.put("Thalia's Lieutenant", SubType.HUMAN);
        lordToTribe.put("Champion of the Parish", SubType.HUMAN);

        // Warrior Lords
        lordToTribe.put("Chief of the Edge", SubType.WARRIOR);
        lordToTribe.put("Chief of the Scale", SubType.WARRIOR);
        lordToTribe.put("Bramblewood Paragon", SubType.WARRIOR);

        // Soldier Lords
        lordToTribe.put("Field Marshal", SubType.SOLDIER);
        lordToTribe.put("Daru Warchief", SubType.SOLDIER);
        lordToTribe.put("Captain of the Watch", SubType.SOLDIER);

        // Wizard Lords
        lordToTribe.put("Azami, Lady of Scrolls", SubType.WIZARD);
        lordToTribe.put("Supreme Inquisitor", SubType.WIZARD);

        // Sliver Lords (special - buff all slivers)
        lordToTribe.put("Sinew Sliver", SubType.SLIVER);
        lordToTribe.put("Predatory Sliver", SubType.SLIVER);
        lordToTribe.put("Muscle Sliver", SubType.SLIVER);
        lordToTribe.put("Sliver Legion", SubType.SLIVER);
        lordToTribe.put("Sliver Hivelord", SubType.SLIVER);

        allLords = Collections.unmodifiableSet(new HashSet<>(lordToTribe.keySet()));
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
        return SynergyType.TRIBAL_LORD;
    }

    @Override
    public Set<String> getEnablers() {
        return allLords;
    }

    @Override
    public Set<String> getPayoffs() {
        // Payoffs are dynamic - any creature of the matching tribe
        // Return empty set since we detect dynamically by SubType
        return Collections.emptySet();
    }

    @Override
    public Set<String> getAllPieces() {
        // Only lords are tracked as pieces since tribal creatures are dynamic
        return allLords;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.TRIBAL_LORD);
        }

        Set<String> lordsFound = new HashSet<>();
        Set<String> lordsOnBattlefield = new HashSet<>();
        Map<SubType, Integer> tribalCountsOnBattlefield = new HashMap<>();
        Map<SubType, Integer> tribalCountsTotal = new HashMap<>();

        // Check battlefield for lords and tribal creatures
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            // Check if it's a lord
            if (lordToTribe.containsKey(name)) {
                lordsFound.add(name);
                lordsOnBattlefield.add(name);
            }

            // Count creatures by tribe
            if (permanent.isCreature(game)) {
                for (SubType tribe : lordToTribe.values()) {
                    if (permanent.hasSubtype(tribe, game)) {
                        tribalCountsOnBattlefield.merge(tribe, 1, Integer::sum);
                    }
                }
            }
        }

        // Check hand for lords
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (lordToTribe.containsKey(name)) {
                lordsFound.add(name);
            }
            // Count creatures in hand by tribe
            if (card.isCreature(game)) {
                for (SubType tribe : lordToTribe.values()) {
                    if (card.hasSubtype(tribe, game)) {
                        tribalCountsTotal.merge(tribe, 1, Integer::sum);
                    }
                }
            }
        }

        // Check library for lords (for potential)
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (lordToTribe.containsKey(name)) {
                lordsFound.add(name);
            }
        }

        // Check graveyard for lords
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (lordToTribe.containsKey(name)) {
                lordsFound.add(name);
            }
        }

        // Add battlefield counts to total
        for (Map.Entry<SubType, Integer> entry : tribalCountsOnBattlefield.entrySet()) {
            tribalCountsTotal.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }

        if (lordsFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.TRIBAL_LORD);
        }

        // Calculate synergy based on lords and their matching tribes
        int scoreBonus = 0;
        int activeLordCount = 0;
        StringBuilder notes = new StringBuilder();
        Set<String> payoffsOnBattlefield = new HashSet<>();

        for (String lordName : lordsOnBattlefield) {
            SubType tribe = lordToTribe.get(lordName);
            int creatureCount = tribalCountsOnBattlefield.getOrDefault(tribe, 0);

            // Don't count the lord itself if it shares the tribe type
            // (most lords do share the type they buff)

            if (creatureCount >= MIN_TRIBAL_CREATURES) {
                activeLordCount++;

                if (creatureCount >= 8) {
                    scoreBonus += BONUS_LORD_WITH_8_CREATURES;
                } else if (creatureCount >= 5) {
                    scoreBonus += BONUS_LORD_WITH_5_CREATURES;
                } else {
                    scoreBonus += BONUS_LORD_WITH_3_CREATURES;
                }

                notes.append(String.format("%s + %d %s; ", lordName, creatureCount, tribe));

                // Track tribal creatures as payoffs
                payoffsOnBattlefield.add(tribe.toString() + " creatures");
            }
        }

        // Bonus for multiple lords
        if (activeLordCount > 1) {
            scoreBonus += (activeLordCount - 1) * BONUS_PER_ADDITIONAL_LORD;
        }

        // Determine state
        SynergyState state;
        if (lordsFound.isEmpty()) {
            state = SynergyState.NOT_DETECTED;
        } else if (lordsOnBattlefield.isEmpty()) {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = 100; // Small bonus for having lords in deck
        } else if (scoreBonus > 0) {
            state = SynergyState.ACTIVE;
        } else {
            state = SynergyState.READY;
            scoreBonus = 150; // Lord on battlefield but not enough tribal creatures yet
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.TRIBAL_LORD)
                .state(state)
                .enablersFound(lordsFound)
                .payoffsFound(payoffsOnBattlefield)
                .enablersOnBattlefield(lordsOnBattlefield)
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
     * Get the tribe a lord buffs.
     */
    public SubType getLordTribe(String lordName) {
        return lordToTribe.get(lordName);
    }
}
