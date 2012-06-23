package org.mage.test.serverside.cards.abilities;

import mage.Constants;
import mage.Constants.PhaseStep;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class ProtectionFromColorTest extends CardTestPlayerBase {

    @Test
    public void testAgainstAbilityInTheStack() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Royal Assassin");

        // tapped White Knight with Protection from Black
        addCard(Constants.Zone.BATTLEFIELD, playerB, "White Knight", 1, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Destroy target tapped creature. ", "White Knight");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no one should be destroyed
        assertPermanentCount(playerB, "White Knight", 1);
    }

    @Test
    public void testAgainstAbilityInTheStackNoProtection() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Royal Assassin");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Destroy target tapped creature. ", "Runeclaw Bear");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // One should have beendestroyed by Royal Assassin
        assertPermanentCount(playerB, "Runeclaw Bear", 0);
    }
}
