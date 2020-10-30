package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HoodedBlightfangTest extends CardTestPlayerBase {

    @Test
    public void testBowOfNylea() {
        addCard(Zone.BATTLEFIELD, playerA, "Hooded Blightfang");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Bow of Nylea");

        attack(1, playerA, "Raging Goblin");
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1 - 1);
    }
}
