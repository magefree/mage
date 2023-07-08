package org.mage.test.cards.single.m20;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RisenReefTest extends CardTestPlayerBase {
    private static final String risenReef = "Risen Reef";
    @Test
    public void croakingCounterPartCountsForTrigger() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Breeding Pool", 10);

        addCard(Zone.BATTLEFIELD, playerA, risenReef);
        addCard(Zone.HAND, playerA, "Croaking Counterpart");
        addCard(Zone.HAND, playerA, "Air Elemental");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Croaking Counterpart");
        addTarget(playerA, risenReef);
        setChoice(playerA, "Yes");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Air Elemental");
        // We get two triggers, so we have to choose which one to put on the stack first (they're identical):
        setChoice(playerA, "Whenever");
        // Put both lands onto the battlefield:
        setChoice(playerA, "Yes", 2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mountain", 3);
    }
}