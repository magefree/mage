package org.mage.test.cards.battle;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;

/**
 * @author TheElk801
 */
public class BattleDuelTest extends BattleBaseTest {

    @Test
    public void testRegularCastAndTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void testAttackBattle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, true);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5 - 2);
    }

    @Test
    public void testAttackBattleBlock() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);
        block(1, playerB, bear, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertPermanentCount(playerA, bear, 0);
        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerB, bear, 0);
        assertGraveyardCount(playerB, bear, 1);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }

    @Test
    public void testCantAttackBattleYouProtect() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(2, playerB, bear, belenon);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, false);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }

    @Test
    public void testChangeControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, confiscate);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, confiscate, belenon);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerB, playerA, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }
}
