package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class VorinclexMonstrousRaiderTest extends CardTestPlayerBase {

    // If you would put one or more counters on a permanent or player, put twice that many of each of
    // those kinds of counters on that permanent or player instead.
    // If an opponent would put one or more counters on a permanent or player, they put half that
    // many of each of those kinds of counters on that permanent or player instead, rounded down.
    private static final String vorinclex = "Vorinclex, Monstrous Raider";
    private static final String boon = "Dragonscale Boon";
    private static final String bear = "Grizzly Bears";
    private static final String rats = "Ichor Rats";
    private static final String planeswalker = "Chandra, Fire Artisan"; // 4 loyalty

    @Test
    public void testIDoubleCountersOnMyStuff() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 4);
    }

    @Test
    public void testIDoubleCountersOnTheirStuff() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 4);
    }

    @Test
    public void testIDoubleCountersOnMyself() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.HAND, playerA, rats);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rats);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 2);
        assertCounterCount(playerB, CounterType.POISON, 2);
    }

    @Test
    public void testIDoubleCountersOnPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.HAND, playerA, planeswalker); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, planeswalker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, planeswalker, CounterType.LOYALTY, 4 * 2);
    }

    @Test
    public void testTheyHalveCountersOnTheirStuff() {
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerB, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 1);
    }

    @Test
    public void testTheyHalveCountersOnMyStuff() {
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerB, boon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, boon, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(bear, CounterType.P1P1, 1);
    }

    @Test
    public void testTheyHalveCountersOnMyself() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, vorinclex);
        addCard(Zone.HAND, playerA, rats);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rats);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 0);
        assertCounterCount(playerB, CounterType.POISON, 0);
    }
}
