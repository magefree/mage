package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

public class BanishmentTest extends CardTestMultiPlayerBase {

    @Test
    public void testBanishment() throws GameException {
        //addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);
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
}
