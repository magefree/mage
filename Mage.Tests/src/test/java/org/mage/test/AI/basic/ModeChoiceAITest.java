package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4PlayersWithAIHelps;

public class ModeChoiceAITest extends CardTestCommander4PlayersWithAIHelps {

    // Make sure that AI mode selection does not freeze when there are zero available choices
    // https://github.com/magefree/mage/pull/14901
    @Test
    public void test_AI_SkipInvalidModes() {
        addCard(Zone.BATTLEFIELD, playerA, "Parapet Thrasher");
        addCard(Zone.BATTLEFIELD, playerA, "Brimstone Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Dragon");

        attack(1, playerA, "Parapet Thrasher", playerB);
        attack(1, playerA, "Brimstone Dragon", playerC);
        attack(1, playerA, "Volcanic Dragon", playerD);

        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertLife(playerB, 16);
        assertLife(playerC, 10);
        assertLife(playerD, 12);
    }

}
