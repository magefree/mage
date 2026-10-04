package org.mage.test.cards.single.brc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SanwellAvengerAceTest extends CardTestPlayerBase {

    @Test
    public void testCastVehicleWithNormalManaCost() {
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Sanwell, Avenger Ace");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.LIBRARY, playerA, "Cultivator's Caravan");
        addCard(Zone.LIBRARY, playerA, "Forest", 5);

        attack(1, playerA, "Sanwell, Avenger Ace", playerB);
        setChoice(playerA, "Cultivator's Caravan"); // Choose the Vehicle among the exiled cards

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Cultivator's Caravan", 1);
    }
}
