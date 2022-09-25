package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

public class BanishmentInfluenceTest extends CardTestMultiPlayerBase {

    @Test
    public void testBanishment() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 4);
        addCard(Zone.HAND, playerA, "Banishment", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Steel Overseer", 4);
        addCard(Zone.BATTLEFIELD, playerC, "Memnite", 5);
        addCard(Zone.BATTLEFIELD, playerC, "Steel Overseer", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Memnite", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banishment");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Memnite", 4);
        assertPermanentCount(playerA, "Banishment", 1);
        assertPermanentCount(playerB, "Memnite", 0);
        assertPermanentCount(playerB, "Steel Overseer", 4);
        assertPermanentCount(playerC, "Memnite", 5);
        assertPermanentCount(playerC, "Steel Overseer", 1);
        assertPermanentCount(playerD, "Memnite", 0);
    }

    @Test
    public void testDestroyBanishment() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Banishment", 1);
        addCard(Zone.HAND, playerA, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Steel Overseer", 4);
        addCard(Zone.BATTLEFIELD, playerC, "Memnite", 5);
        addCard(Zone.BATTLEFIELD, playerC, "Steel Overseer", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Memnite", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banishment");
        addTarget(playerA, "Memnite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disenchant");
        addTarget(playerA, "Banishment");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Banishment", 0);
        assertPermanentCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Steel Overseer", 4);
        assertPermanentCount(playerC, "Memnite", 5);
        assertPermanentCount(playerC, "Steel Overseer", 1);
        assertPermanentCount(playerD, "Memnite", 5);
    }
}
