package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * @author LevelX2
 */
public class TargetsAreChosenTest extends CardTestPlayerBaseAI {

    /**
     * Check that the AI selects a target from the own artifacts and also an
     * artifact from the opponent artficats
     */
    @Test
    public void testRackAndRuin() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy two target artifacts.
        addCard(Zone.HAND, playerA, "Rack and Ruin"); // {2}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Mox Emerald", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rack and Ruin", 1);
        assertGraveyardCount(playerA, "Mox Emerald", 1);
        assertGraveyardCount(playerB, "Juggernaut", 1);
    }

    /**
     * Check that the AI does not cast Rack and Ruin if it would destroy the
     * only creature on the battlefield owned by the AI
     */
    @Test
    public void testRackAndRuin2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy two target artifacts.
        addCard(Zone.HAND, playerA, "Rack and Ruin"); // {2}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Mox Emerald");
        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rack and Ruin", 0);
    }

    /**
     * Check that the AI does cast Rack and Ruin if it would destroy two targets
     * of the opponent
     */
    @Test
    public void testRackAndRuin3() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy two target artifacts.
        addCard(Zone.HAND, playerA, "Rack and Ruin"); // {2}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Mox Emerald", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Composite Golem", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mox Emerald", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rack and Ruin", 1);
        assertGraveyardCount(playerB, "Mox Emerald", 2);

    }

    /**
     * Target only opponent creatures to tap
     */
    @Test
    public void testFrostBreath1() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        addCard(Zone.HAND, playerA, "Frost Breath"); // {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Composite Golem", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Frost Breath", 1);
        assertTapped("Silvercoat Lion", true);
        assertTapped("Pillarfield Ox", true);
        assertTapped("Juggernaut", false);
        assertTapped("Composite Golem", false);

    }

    /**
     * Target only opponent creatures also if more targets are possible
     */
    @Test
    public void testFrostBreath2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        addCard(Zone.HAND, playerA, "Frost Breath"); // {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Composite Golem", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Frost Breath", 1);
        assertTapped("Pillarfield Ox", true);
        assertTapped("Juggernaut", false);
        assertTapped("Composite Golem", false);

    }

    /**
     * Spell is not cast if only own creatures can be targeted
     */
    @Test
    public void testFrostBreath3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        addCard(Zone.HAND, playerA, "Frost Breath"); // {2}{U}

        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Composite Golem", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Frost Breath", 0);
        assertTapped("Juggernaut", false);
        assertTapped("Composite Golem", false);

    }

    /**
     *
     */
    @Test
    public void testNefashu() {
        // Whenever Nefashu attacks, up to five target creatures each get -1/-1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Nefashu");  // 5/3

        addCard(Zone.BATTLEFIELD, playerB, "Bellows Lizard", 5);
        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Malakir Cullblade.
        addCard(Zone.BATTLEFIELD, playerA, "Malakir Cullblade", 5);

        attackSkip(1, playerA);
        attack(3, playerA, "Nefashu");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Bellows Lizard", 5);
        assertPowerToughness(playerA, "Malakir Cullblade", 6, 6, Filter.ComparisonScope.All);
        assertTapped("Nefashu", true);

        assertLife(playerB, 15);
    }

    /**
     * Test that AI counters creature spell
     */
    @Test
    @Ignore  // counter spells don't seem to be cast by AI
    public void testRewind() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Counter target spell. Untap up to four lands.
        addCard(Zone.HAND, playerA, "Rewind");  // {2}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        // Renown 1 (When this creature deals combat damage to a player, if it isn't renowned, put a +1/+1 counter on it and it becomes renowned.)
        // {W}{W}: Tap target creature.
        addCard(Zone.HAND, playerB, "Kytheon's Irregulars", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Kytheon's Irregulars");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Kytheon's Irregulars", 0);
        assertGraveyardCount(playerB, "Kytheon's Irregulars", 1);
        assertGraveyardCount(playerA, "Rewind", 1);

        assertTappedCount("Island", true, 0);

    }

    /**
     * When I have Archfiend of Depravity on the field, the AI always sacks
     * creatures until it has ONE left, when the card states that you can keep
     * TWO at the end of each turn. This makes it un-fun playing the Archfiend
     * as it makes it a lot easier to win.
     */
    @Test
    public void testArchfiendOfDepravity() {
        // Flying
        // At the beginning of each opponent's end step, that player chooses up to two creatures he or she controls, then sacrifices the rest.
        addCard(Zone.BATTLEFIELD, playerB, "Archfiend of Depravity");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 3);
        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2, Filter.ComparisonScope.All);
    }

    /**
     * Target selection from EntersTheBattlefield is not varied in the AI
     * calculation, so value is only calculated for the one selected target set.
     *
     * E.g. If AI casts an Eyeblight Assassin and opponent has a 3/1 and a 2/2,
     * the AI should target the 3/1.
     *
     * Add test to check such situations
     */
}
