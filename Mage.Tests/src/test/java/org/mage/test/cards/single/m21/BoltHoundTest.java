package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BoltHoundTest extends CardTestPlayerBase {

    @Test
    public void testBoostOthers(){
        // Haste
        // Whenever Bolt Hound attacks, other creatures you control get +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Bolt Hound");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");

        attack(1, playerA, "Bolt Hound");
        attack(1, playerA, "Raging Goblin");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 16);
        assertPowerToughness(playerA, "Bolt Hound", 2,2);
        assertPowerToughness(playerA, "Raging Goblin", 2, 1);

    }
}
