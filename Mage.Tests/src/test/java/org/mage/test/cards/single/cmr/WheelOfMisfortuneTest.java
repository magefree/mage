package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * Wheel of Misfortune {2}{R}: each player secretly chooses a number 0 or greater. It deals damage equal to the
 * highest number to each player who chose that number; each player who didn't choose the lowest number discards
 * their hand, then draws seven cards.
 * <p>
 * The AI (playerB, under AI control while the Wheel resolves) keeps a good hand with 0 and buys a new one with a
 * small number.
 */
public class WheelOfMisfortuneTest extends CardTestPlayerBaseWithAIHelps {

    private void castWheel(int number) {
        addCard(Zone.HAND, playerA, "Wheel of Misfortune", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wheel of Misfortune");
        setChoice(playerA, "X=" + number);
        // playerB is under AI control from its first priority (the Wheel on the stack) to the end of the step,
        // so it makes its own choice while the Wheel resolves
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);
    }

    @Test
    public void test_AI_KeepsAGoodHand() {
        addCard(Zone.HAND, playerB, "Grizzly Bears", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        castWheel(3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // B chose 0: lowest, so it keeps its hand; A chose 3: highest, so it takes 3 and gets seven new cards
        assertHandCount(playerB, "Grizzly Bears", 4);
        assertLife(playerB, 20);
        assertLife(playerA, 20 - 3);
        assertHandCount(playerA, 7);
    }

    @Test
    public void test_AI_WithNothingInHand_BuysANewOneCheaply() {
        castWheel(0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // B chose 1..3 against A's 0: not lowest, so seven new cards; highest, so that much damage
        assertHandCount(playerB, 7);
        int life = currentGame.getPlayer(playerB.getId()).getLife();
        Assert.assertTrue("AI picks a small number, took " + (20 - life), life >= 17 && life <= 19);
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_AI_WithNothingCastable_BuysANewOne() {
        addCard(Zone.HAND, playerB, "Craterhoof Behemoth", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        castWheel(0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // three cards it can't cast for turns: B chose 1..3, discarded them and drew seven
        assertHandCount(playerB, 7);
        assertGraveyardCount(playerB, "Craterhoof Behemoth", 3);
        int life = currentGame.getPlayer(playerB.getId()).getLife();
        Assert.assertTrue("AI picks a small number, took " + (20 - life), life >= 17 && life <= 19);
    }

    @Test
    public void test_AI_WithAThinLibrary_KeepsItsHand() {
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Swamp", 5);
        castWheel(1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // drawing seven from five would lose the game: B chose 0
        assertHandCount(playerB, 0);
        assertLife(playerB, 20);
        assertLife(playerA, 20 - 1);
    }

    @Test
    public void test_AI_AtLowLife_NeverChoosesItsDeath() {
        setLife(playerB, 2);
        castWheel(0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // it still wants a new hand, but only 1 of damage is safe
        assertHandCount(playerB, 7);
        assertLife(playerB, 1);
        Assert.assertFalse(currentGame.getPlayer(playerB.getId()).hasLost());
    }

    @Test
    public void test_AI_AtOneLife_KeepsItsHand() {
        setLife(playerB, 1);
        castWheel(0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // any number above 0 could be the highest and kill it: B chose 0, tying A for lowest
        assertHandCount(playerB, 0);
        assertLife(playerB, 1);
        assertLife(playerA, 20);
    }
}
