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

        addCard(Zone.BATTLEFIELD, playerA, guardian);
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Altar");
        addCard(Zone.HAND, playerA, "Croaking Counterpart");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Croaking Counterpart");
        addTarget(playerA, guardian);

        // This sacs the 4/3 Guardian, making two triggers:
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice a creature:");
        addTarget(playerA, guardian+" [no copy]");
        setChoice(playerA, "Whenever"); // Choose trigger order

        // This sacs the 1/1 token, which should make another trigger:
        activateAbility(1, PhaseStep.END_TURN, playerA, "Sacrifice a creature:");
        addTarget(playerA, guardian);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Citizen", 6);
    }
}