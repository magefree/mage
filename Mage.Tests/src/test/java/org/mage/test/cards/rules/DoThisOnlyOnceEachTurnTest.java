package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar
 */
public class DoThisOnlyOnceEachTurnTest extends CardTestPlayerBase {

    /**
     * Whitesun's Passage restores 5 life
     * <br>
     * Nykthos Paragon    {4}{W}{W}
     * Enchantment Creature - Human Soldier
     * 4/6
     * Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.
     */
    @Test
    public void testAbilityCantTriggerSameTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos Paragon");
        addCard(Zone.HAND, playerA, "Whitesun's Passage", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        setChoice(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("empty stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Nykthos Paragon", 9, 11);
    }

    /**
     * With two instances of a "Do this only once each turn" ability on the stack,
     * if the first to resolve is used the second one should fizzle.
     */
    @Test
    public void testAbilityFizzlesSameTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos Paragon");
        addCard(Zone.HAND, playerA, "Whitesun's Passage", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkLife("both heals resolved", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 30);
        checkStackSize("both triggers on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Nykthos Paragon", 9, 11); // would be 14/16 if ability used twice
    }

    /**
     * Ability should only be prevented for the turn it is used, and should work fine on future turns.
     */
    @Test
    public void testAbilityTriggersNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos Paragon");
        addCard(Zone.HAND, playerA, "Whitesun's Passage", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("second turn trigger", 2, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Nykthos Paragon", 14, 16);
    }

    /**
     * If ability is refused, ability should still be able to trigger that turn.
     */
    @Test
    public void testAbilityRefusedThenTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos Paragon");
        addCard(Zone.HAND, playerA, "Whitesun's Passage", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPT("no counters added", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nykthos Paragon", 4, 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whitesun's Passage");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Nykthos Paragon", 9, 11);
    }

    /**
     * Ability should be marked as used before effects resolve. See #11106
     */
    @Test
    public void testOnduSpiritdancer() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ondu Spiritdancer");
        addCard(Zone.HAND, playerA, "Ajani's Welcome", 1); // generic enchantment

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani's Welcome");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true); // resolve Ajani's Welcome
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true); // resolve initial Ondu Spiritdancer trigger
        checkStackSize("no second trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
