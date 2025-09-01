package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheRedTerrorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheRedTerror The Red Terror} {3}{R}
     * Legendary Creature — Tyranid
     * Advanced Species — Whenever a red source you control deals damage to one or more permanents and/or players, put a +1/+1 counter on The Red Terror.
     * 4/3
     */
    private static final String terror = "The Red Terror";

    /**
     * 2/1, notably non-red
     */
    private static final String vanguard = "Elite Vanguard";
    /**
     * 2/1, notably red
     */
    private static final String piker = "Goblin Piker";
    /**
     * 2/1, notably red
     */
    private static final String earthElemental = "Earth Elemental";

    /**
     * {R} 3 damage to any target
     */
    private static final String bolt = "Lightning Bolt";
    /**
     * Rivals' Duel {3}{R}
     * Sorcery
     * Choose two target creatures that share no creature types. Those creatures fight each other.
     */
    private static final String duel = "Rivals' Duel";
    /**
     * {1}{R} 2 damage to each creature
     */
    private static final String pyroclasm = "Pyroclasm";
    /**
     * Fiery Confluence {2}{R}{R}
     * Sorcery
     * Choose three. You may choose the same mode more than once.
     * • Fiery Confluence deals 1 damage to each creature.
     * • Fiery Confluence deals 2 damage to each opponent.
     * • Destroy target artifact.
     */
    private static final String confluence = "Fiery Confluence";

    @Test
    public void testTriggerCombatSimple() {
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);

        attack(1, playerA, terror, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 1);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void testNoTriggerCombatNonRed() {
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);

        attack(1, playerA, vanguard, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 0);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void testTriggerCombatRed() {
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, earthElemental, 1);

        attack(1, playerA, earthElemental, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 1);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void testDoubleTriggerCombatRed() {
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerA, earthElemental, 1);

        attack(1, playerA, terror, playerB);
        attack(1, playerA, vanguard, playerB);
        attack(1, playerA, earthElemental, playerB);

        setChoice(playerA, "<i>Advanced Species</i>"); // 2 triggers need ordering.

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 2);
        assertLife(playerB, 20 - 4 - 2 - 4);
    }

    @Test
    public void testNoTriggerNonRedBlockedByRed() {
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerB, earthElemental, 1);

        attack(1, playerA, vanguard, playerB);
        block(1, playerB, earthElemental, vanguard);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 0);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, vanguard, 1);
        assertDamageReceived(playerB, earthElemental, 2);
    }

    @Test
    public void testTriggerRedBlockedByRed() {
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, earthElemental, 1);
        addCard(Zone.BATTLEFIELD, playerB, earthElemental, 1);

        attack(1, playerA, earthElemental, playerB);
        block(1, playerB, earthElemental, earthElemental);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 1);
        assertLife(playerB, 20);
        assertDamageReceived(playerA, earthElemental, 4);
        assertDamageReceived(playerB, earthElemental, 4);
    }

    @Test
    public void testTriggerPyroclasm() {
        addCard(Zone.HAND, playerA, pyroclasm, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, earthElemental, 1);
        addCard(Zone.BATTLEFIELD, playerA, vanguard, 1);
        addCard(Zone.BATTLEFIELD, playerB, earthElemental, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroclasm);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 1);
        assertDamageReceived(playerA, earthElemental, 2);
        assertDamageReceived(playerA, terror, 2);
        assertGraveyardCount(playerA, vanguard, 1);
        assertDamageReceived(playerB, earthElemental, 2);
    }

    @Test
    public void testTriggerBoltFace() {
        addCard(Zone.HAND, playerA, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testTriggerBoltCreature() {
        addCard(Zone.HAND, playerA, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, earthElemental, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, earthElemental);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 1);
        assertDamageReceived(playerA, earthElemental, 3);
    }

    @Test
    public void testDoubleTriggerDuelTwoRed() {
        addCard(Zone.HAND, playerA, duel, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);
        addCard(Zone.BATTLEFIELD, playerA, piker, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, duel, terror + "^" + piker);

        setChoice(playerA, "<i>Advanced Species</i>"); // 2 triggers need ordering.

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 2);
    }

    @Test
    public void testTripleTriggerFieryConfluence() {
        addCard(Zone.HAND, playerA, confluence, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, terror, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, confluence);
        setModeChoice(playerA, "2"); // Fiery Confluence deals 2 damage to each opponent.
        setModeChoice(playerA, "2"); // Fiery Confluence deals 2 damage to each opponent.
        setModeChoice(playerA, "2"); // Fiery Confluence deals 2 damage to each opponent.

        setChoice(playerA, "<i>Advanced Species</i>", 2); // 3 triggers need ordering.

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, terror, CounterType.P1P1, 3);
        assertLife(playerB, 20 - 2 * 3);
    }
}
