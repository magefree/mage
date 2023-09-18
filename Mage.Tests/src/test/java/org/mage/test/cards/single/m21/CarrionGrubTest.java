package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CarrionGrubTest extends CardTestPlayerBase {

    @Test
    public void etbTrigger() {
        // Carrion Grub gets +X/+0, where X is the greatest power among creature cards in your graveyard.
        // When Carrion Grub enters the battlefield, mill four cards.
        addCard(Zone.HAND, playerA, "Carrion Grub");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Carrion Grub");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA , 4);

    }

    @Test
    public void boostPower() {
        // Carrion Grub gets +X/+0, where X is the greatest power among creature cards in your graveyard.
        // When Carrion Grub enters the battlefield, mill four cards.
        addCard(Zone.BATTLEFIELD, playerA, "Carrion Grub");
        addCard(Zone.GRAVEYARD, playerA, "Serra Angel");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Carrion Grub", 4, 5);

    }
}
