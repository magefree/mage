package org.mage.test.serverside.cards.abilities;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class ProtectionFromColorTest extends CardTestPlayerBase {

    @Test
    public void testAgainstAbilityInTheStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");

        // tapped White Knight with Protection from Black
        addCard(Zone.BATTLEFIELD, playerB, "White Knight", 1, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Destroy target tapped creature. ", "White Knight");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // no one should be destroyed
        assertPermanentCount(playerB, "White Knight", 1);
    }

    @Test
    public void testAgainstAbilityInTheStackNoProtection() {
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");

        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Destroy target tapped creature. ", "Runeclaw Bear");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // One should have beendestroyed by Royal Assassin
        assertPermanentCount(playerB, "Runeclaw Bear", 0);
    }
}
