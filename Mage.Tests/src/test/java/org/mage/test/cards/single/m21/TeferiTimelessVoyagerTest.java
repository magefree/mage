package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TeferiTimelessVoyagerTest extends CardTestPlayerBase {

    @Test
    public void test_Minus8_CantPhaseInUntilEndOfYourNextTurn() {
        // −8: Each creature target opponent controls phases out. Until the end of your next turn, they can't phase in.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Timeless Voyager"); // loyalty 4
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        addCounters(1, PhaseStep.UPKEEP, playerA, "Teferi, Timeless Voyager", CounterType.LOYALTY, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: Each creature", playerB);

        // turn 2 untap - can't phase in, turn 3 - your next turn, turn 4 untap - phases in
        checkPermanentCount("opponent turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 0);
        checkPermanentCount("your next turn", 3, PhaseStep.END_TURN, playerB, "Grizzly Bears", 0);
        checkPermanentCount("after your next turn", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }
}
