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

    @Test
    public void testDefeated() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 3 + 6);
        addCard(Zone.HAND, playerA, belenon);
        addCard(Zone.HAND, playerA, impact);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, impact, belenon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, belenon, 0);
        assertPermanentCount(playerA, warAnthem, 1);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertPowerToughness(playerA, "Knight Token", 2 + 1, 2 + 1);
    }

    @Test
    public void testDefeatedStifle() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2 + 6 + 1);
        addCard(Zone.HAND, playerA, kaladesh);
        addCard(Zone.HAND, playerA, impact);
        addCard(Zone.HAND, playerA, stifle);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kaladesh);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, impact, kaladesh);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, stifle, "stack ability");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, kaladesh, 0);
        assertGraveyardCount(playerA, kaladesh, 1);
        assertPermanentCount(playerA, "Thopter Token", 1);
    }
}
