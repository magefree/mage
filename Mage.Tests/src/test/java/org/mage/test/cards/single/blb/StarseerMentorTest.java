package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class StarseerMentorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.StarseerMentor Starseer Mentor} {3}{W}{B}
     * Creature — Bat Warlock
     * Flying, vigilance
     * At the beginning of your end step, if you gained or lost life this turn, target opponent loses 3 life unless they sacrifice a nonland permanent or discard a card.
     * 3/5
     */
    private static final String mentor = "Starseer Mentor";

    @Test
    public void test_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20);
    }

    @Test
    public void test_LifeGain_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        // playerB has no way to pay for the cost, so no choice.

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_LifeLost_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Serpent Warrior"); // etb loses 3 life
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serpent Warrior");

        addTarget(playerA, playerB); // target for the trigger.
        // playerB has no way to pay for the cost, so no choice.

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Trigger_CanDiscard_AndDiscard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerB, "Abandon Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, true); // choose to pay the cost to not lose life
        setChoice(playerB, "Abandon Hope"); // Choose to discard

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Abandon Hope", 1);
    }

    @Test
    public void test_Trigger_CanDiscard_AndDontDiscard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerB, "Abandon Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, false); // choose to not pay the cost to not lose life

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerB, "Abandon Hope", 1);
    }

    @Test
    public void test_Trigger_CanSacrifice_AndSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, true); // choose to pay the cost to not lose life
        setChoice(playerB, "Elite Vanguard"); // Choose to sacrifice

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
    }

    @Test
    public void test_Trigger_CanSacrifice_AndDontSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, false); // choose to not pay the cost to not lose life

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerB, "Elite Vanguard", 1);
    }


    @Test
    public void test_Trigger_CanSacrificeOrDiscard_AndSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.HAND, playerB, "Abandon Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, true); // choose to pay the cost to not lose life
        setChoice(playerB, true); // choose to pay the sacrifice cost
        setChoice(playerB, "Elite Vanguard"); // Choose to sacrifice

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
        assertHandCount(playerB, "Abandon Hope", 1);
    }

    @Test
    public void test_Trigger_CanSacrificeOrDiscard_AndDiscard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.HAND, playerB, "Abandon Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, true); // choose to pay the cost to not lose life
        setChoice(playerB, false); // choose to not pay the sacrifice cost (so will discard)
        setChoice(playerB, "Abandon Hope"); // Choose to discard

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Elite Vanguard", 1);
        assertGraveyardCount(playerB, "Abandon Hope", 1);
    }

    @Test
    public void test_Trigger_CanSacrificeOrDiscard_AndDontPay() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, mentor);
        addCard(Zone.HAND, playerA, "Angel of Mercy"); // etb gains 5 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.HAND, playerB, "Abandon Hope");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Mercy");

        addTarget(playerA, playerB); // target for the trigger.
        setChoice(playerB, false); // choose to not pay the cost to not lose life

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerB, "Elite Vanguard", 1);
        assertHandCount(playerB, "Abandon Hope", 1);
    }
}
