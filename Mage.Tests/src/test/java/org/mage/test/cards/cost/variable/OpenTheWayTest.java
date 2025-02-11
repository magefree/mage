package org.mage.test.cards.cost.variable;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OpenTheWayTest extends CardTestPlayerBase {

    @Test
    public void testCardWithValidValue(){
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.HAND, playerA, "Open the Way");
        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Open the Way");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Forest", 12);

    }

    @Test
    public void testCardWithInvalidVAlue(){
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.HAND, playerA, "Open the Way");
        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Open the Way");
        setChoice(playerA, "X=5");

        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Forest", 10);

    }
}
