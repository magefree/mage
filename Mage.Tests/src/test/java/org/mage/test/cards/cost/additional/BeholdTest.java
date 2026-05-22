package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BeholdTest extends CardTestPlayerBase {

    @Test
    public void testBeholdFromHand() {
        addCard(Zone.HAND, playerA, "Champions of the Perfect");
        addCard(Zone.HAND, playerA, "LLanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Champions of the Perfect");
        setChoice(playerA, "Llanowar Elves");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fell");
        addTarget(playerA, "Champions of the Perfect");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Champions of the Perfect", 1);
        assertHandCount(playerA, "Llanowar Elves", 1);
        assertExileCount(playerA, "Llanowar Elves", 0);
        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

    @Test
    public void testBeholdFromBattlefield() {
        addCard(Zone.HAND, playerA, "Champions of the Perfect");
        addCard(Zone.BATTLEFIELD, playerA, "LLanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Champions of the Perfect");
        setChoice(playerA, "Llanowar Elves");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fell");
        addTarget(playerA, "Champions of the Perfect");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Champions of the Perfect", 1);
        assertHandCount(playerA, "Llanowar Elves", 1);
        assertExileCount(playerA, "Llanowar Elves", 0);
        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

}
