package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class BloodchiefAscensionTest extends CardTestMultiPlayerBase {

    @Test
    public void test_BloodchiefAscension_AllAlive() {
        // Enchantment
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodchief Ascension");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Fireball");
        addCard(Zone.BATTLEFIELD, playerD, "Mountain", 3);
        addCard(Zone.HAND, playerD, "Fireball");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 3);
        addCard(Zone.HAND, playerC, "Fireball");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Fireball");

        // Player order: A -> D -> C -> B
        runCode("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // make sure range used
            Assert.assertTrue(playerA.hasPlayerInRange(playerA.getId()));
            Assert.assertTrue(playerA.hasPlayerInRange(playerD.getId()));
            Assert.assertFalse(playerA.hasPlayerInRange(playerC.getId()));
            Assert.assertTrue(playerA.hasPlayerInRange(playerB.getId()));
        });

        // turn 1
        // damage itself, no quest counters gain
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball", playerA);
        setChoice(playerA, "X=2");
        setChoice(playerA, true); // put quest counter on end of turn 2

        // turn 2
        checkPermanentCounters("after turn 1", 2, PhaseStep.UPKEEP, playerA, "Bloodchief Ascension", CounterType.QUEST, 0);
        // damage opponent, +1 quest counter
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Fireball", playerD);
        setChoice(playerD, "X=2");

        // turn 3
        checkPermanentCounters("after turn 2", 3, PhaseStep.UPKEEP, playerA, "Bloodchief Ascension", CounterType.QUEST, 0 + 1);
        // damage out of range player, no quest counter
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Fireball", playerC);
        setChoice(playerC, "X=2");

        // turn 4
        checkPermanentCounters("after turn 3", 4, PhaseStep.UPKEEP, playerA, "Bloodchief Ascension", CounterType.QUEST, 0 + 1 + 0);
        // damage opponent, +1 quest counter
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Fireball", playerB);
        setChoice(playerB, "X=2");
        setChoice(playerA, true); // put quest counter on end of turn 4

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 18);
        assertLife(playerC, 18);
        assertLife(playerD, 18);

        assertGraveyardCount(playerA, "Fireball", 1);
        assertGraveyardCount(playerB, "Fireball", 1);
        assertGraveyardCount(playerC, "Fireball", 1);
        assertGraveyardCount(playerD, "Fireball", 1);

        assertCounterCount("Bloodchief Ascension", CounterType.QUEST, 2); // 1 opponent out of range

    }

    /**
     * One of my opponents in a multiplayer game had a Bloodchief Ascension in
     * play. I took lethal damage on my turn, but they didn't get a counter on
     * Bloodchief Ascension at my end step. I think they should, even though I
     * had left the game from dying, because of:
     * <p>
     * 800.4g. If a player leaves the game during their turn, that turn
     * continues to its completion without an active player. If the active
     * player would receive priority, instead the next player in turn order
     * receives priority, or the top object on the stack resolves, or the phase
     * or step ends, whichever is appropriate.
     */
    @Test
    public void test_BloodchiefAscension_DieBeforeEndTurn() {
        // Enchantment
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodchief Ascension");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Fireball");
        addCard(Zone.BATTLEFIELD, playerD, "Mountain", 3);
        addCard(Zone.HAND, playerD, "Fireball");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 3);
        addCard(Zone.HAND, playerC, "Fireball");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 21);
        addCard(Zone.HAND, playerB, "Fireball");

        // Player order: A -> D -> C -> B
        runCode("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // make sure range used
            Assert.assertTrue(playerA.hasPlayerInRange(playerA.getId()));
            Assert.assertTrue(playerA.hasPlayerInRange(playerD.getId()));
            Assert.assertFalse(playerA.hasPlayerInRange(playerC.getId()));
            Assert.assertTrue(playerA.hasPlayerInRange(playerB.getId()));

        });

        // turn 1
        // damage itself, no quest counters gain
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball", playerA);
        setChoice(playerA, "X=2");
        setChoice(playerA, true); // put quest counter on end of turn 2

        // turn 2
        checkPermanentCounters("after turn 1", 2, PhaseStep.UPKEEP, playerA, "Bloodchief Ascension", CounterType.QUEST, 0);
        // damage opponent, +1 quest counter
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Fireball", playerD);
        setChoice(playerD, "X=2");

        // turn 3
        checkPermanentCounters("after turn 2", 3, PhaseStep.UPKEEP, playerA, "Bloodchief Ascension", CounterType.QUEST, 0 + 1);
        // damage out of range player, no quest counter
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Fireball", playerC);
        setChoice(playerC, "X=2");

        // turn 4
        checkPermanentCounters("after turn 3", 4, PhaseStep.UPKEEP, playerA, "Bloodchief Ascension", CounterType.QUEST, 0 + 1 + 0);
        // opponent kills itself, turn continue to the end and must give +1 counters
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Fireball", playerB);
        setChoice(playerB, "X=20");
        setChoice(playerA, true); // put quest counter on end of turn 4

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 0);
        assertLife(playerC, 18);
        assertLife(playerD, 18);

        Assert.assertTrue("playerB has lost", playerB.hasLost());

        assertGraveyardCount(playerA, "Fireball", 1);
        assertGraveyardCount(playerC, "Fireball", 1);
        assertGraveyardCount(playerD, "Fireball", 1);

        assertCounterCount("Bloodchief Ascension", CounterType.QUEST, 2); // 1 opponent out of range

    }

    /**
     * Bloodchief Ascension effect is not working corretly on multiplayer.
     * Whenever one player activated Jace's Archivist, the damage of the
     * discarded cards was going on a single player, not on the players who
     * discarded the cards.
     */
    @Test
    public void testJacesArchivist() {
        // Enchantment
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Auramancer");
        addCard(Zone.HAND, playerA, "Auramancer", 2);

        addCard(Zone.BATTLEFIELD, playerD, "Bloodchief Ascension");
        addCounters(2, PhaseStep.UPKEEP, playerD, "Bloodchief Ascension", CounterType.QUEST, 3);
        addCard(Zone.BATTLEFIELD, playerD, "Island", 1);
        // {U}, {T}: Each player discards their hand, then draws cards equal to the greatest number of cards a player discarded this way.
        addCard(Zone.BATTLEFIELD, playerD, "Jace's Archivist", 1); // {1}{U}{U}
        addCard(Zone.LIBRARY, playerD, "Demolish");
        addCard(Zone.HAND, playerD, "Demolish", 1);

        addCard(Zone.HAND, playerC, "Cobblebrute", 4);
        addCard(Zone.HAND, playerB, "Bellows Lizard", 5);

        // Player order: A -> D -> C -> B
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerD, "{U}, {T}: Each player discards");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Auramancer", 3);
        assertGraveyardCount(playerD, "Demolish", 2);
        assertGraveyardCount(playerC, "Cobblebrute", 4);

        assertHandCount(playerA, 4);
        assertHandCount(playerD, 4);
        assertHandCount(playerC, 4);
        assertHandCount(playerB, "Bellows Lizard", 5);

        assertLife(playerA, 14);
        assertLife(playerD, 34);
        assertLife(playerC, 12);
        assertLife(playerB, 20);

    }
}
