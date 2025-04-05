package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PawpatchRecruitTest extends CardTestPlayerBase {

    private static final String paw = "Pawpatch Recruit";
    private static final String cub = "Bear Cub";
    private static final String panharm = "Panharmonicon";
    private static final String prowler = "Chrome Prowler";

    @Test
    public void testCopiedTriggerAbility() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, paw);
        addCard(Zone.BATTLEFIELD, playerB, cub);
        addCard(Zone.BATTLEFIELD, playerA, panharm);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, prowler);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prowler);
        setChoice(playerA, "When {this} enters");
        addTarget(playerA, paw);
        addTarget(playerA, cub);
        setChoice(playerB, "Whenever a creature");
        addTarget(playerB, paw);
        addTarget(playerB, cub);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTapped(paw, true);
        assertTapped(cub, true);
        assertCounterCount(playerB, paw, CounterType.P1P1, 1);
        assertCounterCount(playerB, cub, CounterType.P1P1, 1);
    }
}