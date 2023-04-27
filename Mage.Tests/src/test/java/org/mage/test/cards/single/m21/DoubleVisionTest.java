package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DoubleVisionTest extends CardTestPlayerBase {

    @Test
    public void testFirstInstant(){
        // Whenever you cast your first instant or sorcery spell each turn, copy that spell.
        // You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Double Vision");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        setChoice(playerA, true);
        addTarget(playerA, playerB);
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 16);
    }
}
