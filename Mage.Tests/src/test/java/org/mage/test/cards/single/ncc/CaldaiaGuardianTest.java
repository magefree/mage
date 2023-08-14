package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CaldaiaGuardianTest extends CardTestPlayerBase {
    private static final String guardian = "Caldaia Guardian";
    @Test
    public void croakingCounterPartCountsForTrigger() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Breeding Pool", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        addCard(Zone.BATTLEFIELD, playerA, guardian);

        addCard(Zone.HAND, playerA, "Croaking Counterpart");
        addCard(Zone.HAND, playerA, "Murder", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Croaking Counterpart");
        addTarget(playerA, guardian);

        // This kills the 4/3 Guardian, which should cause two triggers:
        addTarget(playerA, guardian+"[no copy]");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Murder");
        // Choose trigger order:
        setChoice(playerA, "Whenever");

        // Kill the 1/1, which should also trigger:
        addTarget(playerA, guardian);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Citizen Token", 6);
    }
}