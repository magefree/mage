package org.mage.test.serverside.cards.abilities;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class ProtectionFromTypeTest extends CardTestPlayerBase {

    @Test
    public void testProtectionFromArtifacts() {
        useRedDefault();
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Trigon of Corruption");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Tel-Jilad Fallen");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{2},Remove a Charge counter from {this}, {T}: put a -1/-1 counter on target creature. ", "Tel-Jilad Fallen");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // no one should be destroyed
        assertPermanentCount(playerB, "Tel-Jilad Fallen", 1);
    }

    @Test
    public void testNoProtection() {
        useRedDefault();
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Trigon of Corruption");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Coral Merfolk");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{2},Remove a Charge counter from {this}, {T}: Put a -1/-1 counter on target creature. ", "Coral Merfolk");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // Coral Merfolk should be destroyed
        assertPermanentCount(playerB, "Coral Merfolk", 0);
    }
}
