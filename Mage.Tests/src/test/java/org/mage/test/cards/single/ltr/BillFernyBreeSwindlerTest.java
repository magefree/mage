package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BillFernyBreeSwindlerTest extends CardTestPlayerBase {
    private static final String bill = "Bill Ferny, Bree Swindler";
    private static final String horse = "Armored Warhorse";
    @Test
    public void giveAHorseTest() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, bill);
        addCard(Zone.BATTLEFIELD, playerA, horse);
        addCard(Zone.BATTLEFIELD, playerA, "Horseshoe Crab"); // not a horse

        addCard(Zone.BATTLEFIELD, playerB, "Iron Golem"); // has to block

        attack(1, playerA, bill);
        setModeChoice(playerA, "2");
        addTarget(playerA, playerB);
        addTarget(playerA, horse);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        showBattlefield("after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        execute();

        assertPermanentCount(playerB, horse, 1);
        assertPermanentCount(playerA, bill, 1);
        assertPermanentCount(playerA, "Treasure Token", 3);
    }
}