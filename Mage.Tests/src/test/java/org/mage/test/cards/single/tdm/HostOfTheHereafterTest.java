package org.mage.test.cards.single.tdm;


import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HostOfTheHereafterTest extends CardTestPlayerBase {

    public static final String HOST = "Host of the Hereafter";
    public static final String BAYOU = "Bayou";
    public static final String CUB = "Bear Cub";
    public static final String DOWNFALL = "Hero's Downfall";
    public static final String FEED = "Feed the Serpent";

    @Test
    public void testEntersWithCounters() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, HOST);
        addCard(Zone.BATTLEFIELD, playerA, BAYOU, 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HOST);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, HOST, 1);
        assertCounterCount(playerA, HOST, CounterType.P1P1, 2);
    }

    @Test
    public void testDiesMoveCounters() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, HOST);
        addCard(Zone.HAND, playerA, DOWNFALL);
        addCard(Zone.BATTLEFIELD, playerA, BAYOU, 7);
        addCard(Zone.BATTLEFIELD, playerA, CUB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HOST);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DOWNFALL, HOST);
        addTarget(playerA, CUB);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, CUB, 1);
        assertCounterCount(playerA, CUB, CounterType.P1P1, 2);
    }

    @Test
    public void exileNoCounters() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, HOST);
        addCard(Zone.HAND, playerA, FEED);
        addCard(Zone.BATTLEFIELD, playerA, BAYOU, 8);
        addCard(Zone.BATTLEFIELD, playerA, CUB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HOST);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FEED, HOST);


        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, CUB, 1);
        assertCounterCount(playerA, CUB, CounterType.P1P1, 0);
    }

    @Test
    public void testOtherCounters() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, HOST);
        addCard(Zone.HAND, playerA, DOWNFALL);
        addCard(Zone.BATTLEFIELD, playerA, BAYOU, 4);
        addCard(Zone.BATTLEFIELD, playerA, CUB);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, CUB, CounterType.FLYING, 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, CUB, CounterType.LIFELINK, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, DOWNFALL, CUB);
        addTarget(playerA, HOST);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, HOST, 1);
        assertCounterCount(playerA, HOST, CounterType.FLYING, 1);
        assertCounterCount(playerA, HOST, CounterType.LIFELINK, 1);
    }

}