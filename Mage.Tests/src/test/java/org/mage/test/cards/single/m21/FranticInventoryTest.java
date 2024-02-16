package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FranticInventoryTest extends CardTestPlayerBase {

    @Test
    public void drawCards() {
        addCard(Zone.HAND, playerA, "Frantic Inventory");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.GRAVEYARD, playerA, "Frantic Inventory");
       castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Frantic Inventory");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertHandCount(playerA, 2);
    }
}
