package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4PlayersWithAIHelps;

public class ForcedBlockMultiplayerAITest extends CardTestCommander4PlayersWithAIHelps {

    // https://github.com/magefree/mage/issues/15982
    @Test
    public void test_ForcedBlock_DifferentDefender() {
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Brontodon");
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Peema Aether-Seer");

        playerB.setAIPlayer(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Peema Aether-Seer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}{E}", "Arbor Elf");

        attack(1, playerA, "Ancient Brontodon", playerC);
        // bad trade for player B
        attack(1, playerA, "Craw Wurm", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // must still block
        assertGraveyardCount(playerB, "Arbor Elf", 1);
        assertLife(playerB, 20);
        assertLife(playerC, 20 - 9);
    }
}
