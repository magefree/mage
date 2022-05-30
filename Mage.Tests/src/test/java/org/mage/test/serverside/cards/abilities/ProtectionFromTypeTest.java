package org.mage.test.serverside.cards.abilities;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class ProtectionFromTypeTest extends CardTestPlayerBase {

    @Test
    public void testProtectionFromArtifacts() {
        useRedDefault();
        addCard(Zone.BATTLEFIELD, playerA, "Trigon of Corruption");

        addCard(Zone.BATTLEFIELD, playerB, "Tel-Jilad Fallen");

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove", false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no one should be destroyed
        assertPermanentCount(playerB, "Tel-Jilad Fallen", 1);
    }

    @Test
    public void testNoProtection() {
        useRedDefault();
        // Trigon of Corruption enters the battlefield with three charge counters on it.
        addCard(Zone.BATTLEFIELD, playerA, "Trigon of Corruption");

        addCard(Zone.BATTLEFIELD, playerB, "Coral Merfolk");

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Remove a charge counter from {this}, {T}: Put a -1/-1 counter on target creature.", "Coral Merfolk");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        // Coral Merfolk should be destroyed
        assertPermanentCount(playerB, "Coral Merfolk", 0);
    }
}
