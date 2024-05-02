package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheWiseMothmanTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheWiseMothman The Wise Mothman}  {1}{B}{G}{U}
     * Legendary Creature — Insect Mutant
     * Flying
     * Whenever The Wise Mothman enters the battlefield or attacks, each player gets a rad counter.
     * Whenever one or more nonland cards are milled, put a +1/+1 counter on each of up to X target creatures, where X is the number of nonland cards milled this way.
     * 3/3
     */
    private static final String mothman = "The Wise Mothman";

    @Test
    public void test_Trigger_3NonLand_1Land() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, mothman);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Taiga", 1);
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel", 1);
        addCard(Zone.LIBRARY, playerB, "Memnite", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        addTarget(playerA, mothman + "^Grizzly Bears"); // up to three targets => choosing 2

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Taiga", 1);
        assertGraveyardCount(playerA, "Baneslayer Angel", 1);
        assertGraveyardCount(playerB, "Memnite", 2);
        assertCounterCount(playerA, mothman, CounterType.P1P1, 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Elite Vanguard", CounterType.P1P1, 0);
    }

    @Test
    public void test_NoTrigger_AllLands() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, mothman);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        // no trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerB, 2);
        assertCounterCount(playerA, mothman, CounterType.P1P1, 0);
    }
}
