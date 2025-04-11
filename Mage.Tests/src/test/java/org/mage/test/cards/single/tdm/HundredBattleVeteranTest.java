package org.mage.test.cards.single.tdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class HundredBattleVeteranTest extends CardTestPlayerBase {

    private static final String VETERAN = "Hundred-Battle Veteran"; // 4/2
    private static final String CUB = "Bear Cub"; // 2/2

    @Test
    public void testBoostEffect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, VETERAN);
        addCard(Zone.BATTLEFIELD, playerA, CUB);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, CUB, CounterType.FINALITY, 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, CUB, CounterType.P1P1, 3);
        checkPT("no boost", 1, PhaseStep.BEGIN_COMBAT, playerA, VETERAN, 4, 2);
        addCounters(1, PhaseStep.POSTCOMBAT_MAIN, playerA, CUB, CounterType.LIFELINK, 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, CUB, 1);
        assertCounterCount(playerA, VETERAN, CounterType.FINALITY, 0);
        assertPowerToughness(playerA, VETERAN, 6, 6);
    }

    @Test
    public void testCastFromGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, VETERAN);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, VETERAN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, VETERAN, 1);
        assertCounterCount(playerA, VETERAN, CounterType.FINALITY, 1);
    }

}