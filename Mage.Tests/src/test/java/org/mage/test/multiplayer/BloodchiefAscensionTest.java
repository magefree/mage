
package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BloodchiefAscensionTest extends CardTestMultiPlayerBase {

    @Test
    public void testBloodchiefAscensionAllPlayers() {
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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball", playerA);
        setChoice(playerA, "X=2");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Fireball", playerD);
        setChoice(playerD, "X=2");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Fireball", playerC);
        setChoice(playerC, "X=2");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Fireball", playerB);
        setChoice(playerB, "X=2");

        // Player order: A -> D -> C -> B
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
     *
     * 800.4g. If a player leaves the game during their turn, that turn
     * continues to its completion without an active player. If the active
     * player would receive priority, instead the next player in turn order
     * receives priority, or the top object on the stack resolves, or the phase
     * or step ends, whichever is appropriate.
     */
    @Test
    public void testBloodchiefAscension() {
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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball", playerA);
        setChoice(playerA, "X=2");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Fireball", playerD);
        setChoice(playerD, "X=2");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Fireball", playerC);
        setChoice(playerC, "X=2");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Fireball", playerB);
        setChoice(playerB, "X=20");

        // Player order: A -> D -> C -> B
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
