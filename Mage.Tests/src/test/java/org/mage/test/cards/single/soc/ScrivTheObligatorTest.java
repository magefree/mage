package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

public class ScrivTheObligatorTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void testCreatesContractOnOpponentCreature() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Scriv, the Obligator");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scriv, the Obligator");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Contract", 1);
    }

    @Test
    public void testContractBoostsCreatureAttackingContractControllersOpponent() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Scriv, the Obligator");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scriv, the Obligator");
        addTarget(playerA, "Grizzly Bears");

        attack(2, playerD, "Grizzly Bears", playerC);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 16);
        assertLife(playerD, 20);
    }

    @Test
    public void testContractPunishesControllerWhenCreatureAttacksContractController() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Scriv, the Obligator");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scriv, the Obligator");
        addTarget(playerA, "Grizzly Bears");

        attack(4, playerB, "Grizzly Bears", playerA);

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 18);
        assertLife(playerC, 20);
    }
}
