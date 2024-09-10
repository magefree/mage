package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LaezelVlaakithsChampionTest extends CardTestPlayerBase {

    // If you would put one or more counters on a creature or planeswalker you control or on yourself,
    // put that many plus one of each of those kinds of counters on that permanent or player instead.

    private static final String laezel = "Lae'zel, Vlaakith's Champion";
    private static final String boon = "Dragonscale Boon";
    private static final String bear = "Grizzly Bears";
    private static final String rats = "Ichor Rats";
    private static final String planeswalker = "Chandra, Fire Artisan"; // 4 loyalty

    @Test
    public void testExtraCounterOnMyStuff() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, laezel);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 2 + 1);
    }

    @Test
    public void testNoExtraCounterOnTheirStuff() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, laezel);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 2);
    }

    @Test
    public void testExtraCounterOnMyselfNotThem() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, laezel);
        addCard(Zone.HAND, playerA, rats);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rats);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 1 + 1);
        assertCounterCount(playerB, CounterType.POISON, 1);
    }

    @Test
    public void testExtraCounterOnMyPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, laezel);
        addCard(Zone.HAND, playerA, planeswalker); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, planeswalker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, planeswalker, CounterType.LOYALTY, 4 + 1);
    }

    @Test
    public void testTheyPutCountersOnTheirStuff() {
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, laezel);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerB, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 2);
    }

    @Test
    public void testTheyPutCountersOnMyStuff() {
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, laezel);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerB, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 2);
    }

    @Test
    public void testTheyPutCountersOnMe() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, laezel);
        addCard(Zone.HAND, playerA, rats);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rats);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 1);
        assertCounterCount(playerB, CounterType.POISON, 1);
    }
}
