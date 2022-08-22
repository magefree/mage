package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class BanishmentTest extends CardTestCommander4Players {

    @Test
    public void testBanishment() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Banishment", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Steel Overseer", 4);
        addCard(Zone.BATTLEFIELD, playerC, "Memnite", 5);
        addCard(Zone.BATTLEFIELD, playerC, "Steel Overseer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banishment");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Banishment", 1);
        assertPermanentCount(playerB, "Memnite", 0);
        assertPermanentCount(playerB, "Steel Overseer", 4);
        assertPermanentCount(playerC, "Memnite", 0);
        assertPermanentCount(playerC, "Steel Overseer", 1);
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
    }
}
