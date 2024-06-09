package org.mage.test.cards.single.roe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ChampionsDrakeTest extends CardTestPlayerBase {

    private static final String drake = "Champion's Drake"; // 1/1 Flying
    // Champion’s Drake gets +3/+3 as long as you control a creature with three or more level counters on it.

    private static final String wavewatch = "Halimar Wavewatch"; // 0/3, Level up {2}

    @Test
    public void testCondition() {
        addCard(Zone.BATTLEFIELD, playerA, drake, 1);
        addCard(Zone.BATTLEFIELD, playerA, wavewatch, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        checkPermanentCounters("0 level counters", 1, PhaseStep.UPKEEP, playerA, wavewatch, CounterType.LEVEL, 0);
        checkPT("0 level counters", 1, PhaseStep.UPKEEP, playerA, drake, 1, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Level up");

        checkPermanentCounters("1 level counter", 1, PhaseStep.BEGIN_COMBAT, playerA, wavewatch, CounterType.LEVEL, 1);
        checkPT("1 level counter", 1, PhaseStep.BEGIN_COMBAT, playerA, drake, 1, 1);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Level up");

        checkPermanentCounters("2 level counters", 1, PhaseStep.END_TURN, playerA, wavewatch, CounterType.LEVEL, 2);
        checkPT("2 level counters", 1, PhaseStep.END_TURN, playerA, drake, 1, 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Level up");

        checkPermanentCounters("3 level counters", 3, PhaseStep.BEGIN_COMBAT, playerA, wavewatch, CounterType.LEVEL, 3);
        checkPT("3 level counters", 3, PhaseStep.BEGIN_COMBAT, playerA, drake, 4, 4);

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Level up");

        checkPermanentCounters("4 level counters", 3, PhaseStep.END_TURN, playerA, wavewatch, CounterType.LEVEL, 4);
        checkPT("4 level counters", 3, PhaseStep.END_TURN, playerA, drake, 4, 4);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, wavewatch, 0, 6);

    } 

}
