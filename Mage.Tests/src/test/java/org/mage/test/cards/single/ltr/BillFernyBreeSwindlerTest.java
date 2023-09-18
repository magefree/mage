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
        execute();

        assertPermanentCount(playerB, horse, 1);
        assertPermanentCount(playerA, bill, 1);
        assertPermanentCount(playerA, "Treasure Token", 3);
    }

    @Test
    public void phantasmalImageTest() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, bill);
        addCard(Zone.BATTLEFIELD, playerA, horse);
        addCard(Zone.BATTLEFIELD, playerA, "Horseshoe Crab"); // not a horse
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Image");

        addCard(Zone.BATTLEFIELD, playerB, "Iron Golem"); // has to block

        // Create a copy of the horse, but it gets sacrificed when it becomes a target:
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        setChoice(playerA, "Yes");
        setChoice(playerA, horse);

        attack(1, playerA, bill);
        setModeChoice(playerA, "2");
        addTarget(playerA, playerB);
        addTarget(playerA, horse+"[only copy]");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, horse, 0); // Opponent never got the horse
        assertPermanentCount(playerA, bill, 0); // Died to Iron Golem
        assertPermanentCount(playerA, "Treasure Token", 0); // No treasure tokens created
        assertPermanentCount(playerA, horse, 1); // Normal horse still remains
    }
}