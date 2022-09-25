package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BurnBrightTest extends CardTestPlayerBase {

    @Test
    public void boostControlledCreatures(){
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Scryb Sprites");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        // {2}{R}
        // Creatures you control get +2/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Burn Bright");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Bright");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Raging Goblin", 3, 1);
        assertPowerToughness(playerA, "Scryb Sprites", 3, 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }
}
