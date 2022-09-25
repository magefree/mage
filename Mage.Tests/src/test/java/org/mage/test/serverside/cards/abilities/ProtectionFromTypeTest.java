package org.mage.test.serverside.cards.abilities;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class ProtectionFromTypeTest extends CardTestPlayerBase {

    @Before
    public void setUp() {
        // *** ComputerA ***
        // battlefield:ComputerA:Mountain:5
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // hand:ComputerA:Mountain:4
        addCard(Zone.HAND, playerA, "Mountain", 5);
        // library:ComputerA:clear:0
        removeAllCardsFromLibrary(playerA);
        // library:ComputerA:Mountain:10
        addCard(Zone.LIBRARY, playerA, "Mountain", 10);

        // *** ComputerB ***
        // battlefield:ComputerB:Plains:2
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // hand:ComputerB:Plains:2
        addCard(Zone.HAND, playerB, "Plains", 2);
        // library:ComputerB:clear:0
        removeAllCardsFromLibrary(playerB);
        // library:ComputerB:Plains:10
        addCard(Zone.LIBRARY, playerB, "Plains", 10);
        // Trigon of Corruption enters the battlefield with three charge counters on it.
        addCard(Zone.BATTLEFIELD, playerA, "Trigon of Corruption");
    }

    @Test
    public void testProtectionFromArtifacts() {
        addCard(Zone.BATTLEFIELD, playerB, "Tel-Jilad Fallen");

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove", false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no one should be destroyed
        assertPermanentCount(playerB, "Tel-Jilad Fallen", 1);
    }

    @Test
    public void testNoProtection() {
        addCard(Zone.BATTLEFIELD, playerB, "Coral Merfolk");

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Remove a charge counter from {this}, {T}: Put a -1/-1 counter on target creature.", "Coral Merfolk");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Coral Merfolk should be destroyed
        assertPermanentCount(playerB, "Coral Merfolk", 0);
    }
}
