package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * Tests for AI synergy detection and protection.
 * The AI should recognize powerful card synergies (not infinite combos)
 * and protect key enabler pieces from bad trades.
 *
 * Key synergies tested:
 * - Equipment enablers (Puresteel Paladin + Colossus Hammer)
 *
 * @author duxbuse
 */
public class AISynergyDetectionTest extends CardTestPlayerBaseWithAIHelps {

    // ==================== Equipment Enabler Synergy Tests ====================

    /**
     * Test: AI should protect Puresteel Paladin when Colossus Hammer is in play.
     *
     * Scenario: AI controls Puresteel Paladin (2/2) and Colossus Hammer (equipped).
     * Opponent attacks with a 3/3.
     *
     * Expected: AI should NOT block with Puresteel Paladin because it's a synergy enabler.
     * The Puresteel Paladin allows equipping for 0 mana with Metalcraft, making the
     * Colossus Hammer much more powerful.
     */
    @Test
    public void test_SynergyProtection_PuresteelPaladin_DoNotTrade() {
        // Puresteel Paladin: 2/2, Metalcraft - Equipment you control have equip {0}
        addCard(Zone.BATTLEFIELD, playerB, "Puresteel Paladin", 1);
        // Colossus Hammer: Equipment, Equipped creature gets +10/+10 and loses flying
        // Equip 8 (normally expensive, but free with Puresteel Paladin's Metalcraft)
        addCard(Zone.BATTLEFIELD, playerB, "Colossus Hammer", 1);
        // Additional artifacts for Metalcraft (need 3 artifacts)
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring", 2);

        // A creature to equip
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1); // 0/2 flying

        // Opponent's attacker
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser", 1); // 3/3

        attack(1, playerA, "Centaur Courser");

        // AI should NOT block with Puresteel Paladin - it's a synergy enabler
        // The 2/2 would trade with the 3/3 (Paladin dies), breaking the equipment synergy
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("synergy enabler should not block", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Paladin should survive (didn't block)
        assertPermanentCount(playerB, "Puresteel Paladin", 1);
        // AI takes 3 damage instead of trading the Paladin
        assertLife(playerB, 20 - 3);
    }

    /**
     * Test: AI should block with non-synergy creatures before synergy enablers.
     *
     * Scenario: AI has Puresteel Paladin and a regular creature.
     * Opponent attacks with something the regular creature can block safely.
     *
     * Expected: AI blocks with the non-synergy creature.
     */
    @Test
    public void test_SynergyProtection_BlockWithNonSynergyCreature() {
        // Synergy enabler
        addCard(Zone.BATTLEFIELD, playerB, "Puresteel Paladin", 1); // 2/2
        // Equipment payoff
        addCard(Zone.BATTLEFIELD, playerB, "Colossus Hammer", 1);
        // Additional artifacts for Metalcraft
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring", 2);

        // Non-synergy creature that can block safely
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser", 1); // 3/3

        // Opponent's attacker (can be killed by the 3/3)
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        attack(1, playerA, "Balduvian Bears");

        // AI should block with Centaur Courser (non-synergy creature that survives)
        // NOT with Puresteel Paladin (synergy enabler that would trade)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("block with non-synergy creature", 1, playerB, "Centaur Courser");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Both AI creatures survive
        assertPermanentCount(playerB, "Puresteel Paladin", 1);
        assertPermanentCount(playerB, "Centaur Courser", 1);
        // Attacker dies
        assertGraveyardCount(playerA, "Balduvian Bears", 1);
    }

    /**
     * Test: Without synergy payoffs, the enabler is less valuable.
     *
     * Scenario: AI has Puresteel Paladin but NO equipment.
     * Opponent attacks with a 3/3.
     *
     * Expected: AI may trade or take damage based on normal evaluation
     * (enabler without payoff is worth less protection).
     */
    @Test
    public void test_SynergyProtection_EnablerWithoutPayoff_LessProtection() {
        // Puresteel Paladin without any equipment - synergy not active
        addCard(Zone.BATTLEFIELD, playerB, "Puresteel Paladin", 1); // 2/2
        // No equipment cards at all

        // Opponent's attacker
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser", 1); // 3/3

        attack(1, playerA, "Centaur Courser");

        // Without equipment, Puresteel Paladin is just a 2/2 creature
        // AI should NOT block (2/2 vs 3/3 is a bad trade)
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("no synergy - normal evaluation", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Paladin should survive (didn't block bad trade)
        assertPermanentCount(playerB, "Puresteel Paladin", 1);
        // AI takes damage
        assertLife(playerB, 20 - 3);
    }

    /**
     * Test: Sigarda's Aid is also an equipment enabler.
     *
     * Scenario: AI has Sigarda's Aid and Colossus Hammer.
     * AI also has a creature to sacrifice.
     *
     * Expected: AI should protect the synergy pieces.
     */
    @Test
    public void test_SynergyProtection_SigardasAid_EquipmentEnabler() {
        // Sigarda's Aid: Equipment enters attached, can equip at instant speed
        addCard(Zone.BATTLEFIELD, playerB, "Sigarda's Aid", 1);
        // Equipment payoff
        addCard(Zone.BATTLEFIELD, playerB, "Colossus Hammer", 1);

        // Creature to potentially sacrifice
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1
        // Another creature
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        // Opponent's attacker
        addCard(Zone.BATTLEFIELD, playerA, "Centaur Courser", 1); // 3/3

        attack(1, playerA, "Centaur Courser");

        // AI should NOT chump block unnecessarily
        // The 1/1 and 2/2 both lose to the 3/3
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("preserve creatures", 1, playerB, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Both creatures survive
        assertPermanentCount(playerB, "Arbor Elf", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
    }

    /**
     * Test: With active synergy and lethal threat, AI may need to chump block.
     *
     * Scenario: AI is at 3 life with Puresteel Paladin + Colossus Hammer.
     * Opponent attacks with a 6/4 (no trample).
     *
     * Expected: AI must block to survive, even if it means losing the synergy enabler.
     */
    @Test
    public void test_SynergyProtection_MustBlockToSurvive() {
        addCustomEffect_TargetDamage(playerA, 17);

        // Synergy setup
        addCard(Zone.BATTLEFIELD, playerB, "Puresteel Paladin", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Colossus Hammer", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring", 2);

        // Opponent's big attacker (no trample, so chump blocking prevents all damage)
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 1); // 6/4

        // Reduce AI to low life where it must block
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 17", playerB);

        attack(1, playerA, "Craw Wurm");

        // At 3 life, AI MUST chump block or die to 6 damage
        // Synergy protection is overridden by survival
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        // Puresteel Paladin should chump block
        checkBlockers("must block to survive", 1, playerB, "Puresteel Paladin");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Paladin dies chump blocking
        assertGraveyardCount(playerB, "Puresteel Paladin", 1);
        // AI survives at 3 life (no trample means 0 damage gets through)
        assertLife(playerB, 3);
    }

    /**
     * Test: AI should protect equipment payoffs when enabler is in play.
     *
     * Scenario: AI has Puresteel Paladin and a creature equipped with Colossus Hammer.
     * The equipped creature should be valued highly.
     */
    @Test
    public void test_SynergyProtection_EquippedCreatureValued() {
        // Synergy enabler
        addCard(Zone.BATTLEFIELD, playerB, "Puresteel Paladin", 1); // 2/2
        // Equipment
        addCard(Zone.BATTLEFIELD, playerB, "Colossus Hammer", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring", 2); // For Metalcraft

        // Creature to equip - Ornithopter becomes 10/12 with Colossus Hammer!
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1); // 0/2 flying

        // Small attacker
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1

        attack(1, playerA, "Arbor Elf");

        // AI should block with Puresteel Paladin (2/2 survives blocking 1/1)
        // Not with Ornithopter which should get the Hammer
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);
        checkBlockers("block with paladin", 1, playerB, "Puresteel Paladin");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Paladin survives blocking the 1/1
        assertPermanentCount(playerB, "Puresteel Paladin", 1);
        assertDamageReceived(playerB, "Puresteel Paladin", 1);
        // Attacker dies
        assertGraveyardCount(playerA, "Arbor Elf", 1);
    }
}
