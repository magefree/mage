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
        addCard(Zone.HAND, playerA, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Croaking Counterpart");
        addTarget(playerA, guardian);

        // This kills the 4/3 Guardian, making two triggers:
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade");
        addTarget(playerA, guardian+"[no copy]");
//        setChoice(playerA, "Whenever"); // Choose trigger order

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Citizen Token", 4);
    }
}