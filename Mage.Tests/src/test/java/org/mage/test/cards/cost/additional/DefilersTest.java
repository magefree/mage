package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DefilersTest extends CardTestPlayerBase {
    private static final String greenDefiler = "Defiler of Vigor";
    private static final String redDefiler = "Defiler of Instinct";
    private static final String bear = "Grizzly Bears";
    private static final String tusker = "Kalonian Tusker";
    private static final String goblin = "Scarwood Goblins";

    @Test
    public void testRegularAccept() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, greenDefiler);
        addCard(Zone.HAND, playerA, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, bear, 1);
        assertCounterCount(playerA, greenDefiler, CounterType.P1P1, 1);
        assertTappedCount("Forest", true, 1);
    }

    @Test
    public void testRegularDecline() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, greenDefiler);
        addCard(Zone.HAND, playerA, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, bear, 1);
        assertCounterCount(playerA, greenDefiler, CounterType.P1P1, 1);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void testDoubleReduceOne() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, greenDefiler, 2);
        addCard(Zone.HAND, playerA, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear);
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2 - 2);
        assertPermanentCount(playerA, bear, 1);
        assertCounterCount(playerA, greenDefiler, CounterType.P1P1, 2);
        assertTappedCount("Forest", true, 1);
    }

    @Test
    public void testDoubleReduceBoth() {
        addCard(Zone.BATTLEFIELD, playerA, greenDefiler, 2);
        addCard(Zone.HAND, playerA, tusker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tusker);
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, "");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2 - 2);
        assertPermanentCount(playerA, tusker, 1);
        assertCounterCount(playerA, greenDefiler, CounterType.P1P1, 2);
    }

    @Test
    public void testTwoColors() {
        addCard(Zone.BATTLEFIELD, playerA, greenDefiler);
        addCard(Zone.BATTLEFIELD, playerA, redDefiler);
        addCard(Zone.HAND, playerA, goblin);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblin);
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, "");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2 - 2);
        assertLife(playerB, 20 - 1);
        assertPermanentCount(playerA, goblin, 1);
        assertCounterCount(playerA, greenDefiler, CounterType.P1P1, 1);
    }
}
