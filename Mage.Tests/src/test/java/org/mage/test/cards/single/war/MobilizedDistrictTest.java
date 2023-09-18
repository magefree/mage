package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheELk801
 */
public class MobilizedDistrictTest extends CardTestPlayerBase {

    @Test
    public void testActivate() {
        addCard(Zone.BATTLEFIELD, playerA, "Mobilized District");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Mobilized District", 3, 3);
    }

    @Test
    public void testActivate2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mobilized District");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");
        addCard(Zone.BATTLEFIELD, playerA, "Rhys the Redeemed");
        addCard(Zone.HAND, playerA, "Wrath of God");

        // Activating costs {2}, have enough to activate exactly twice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}");

        // Remove legends
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        // Activating costs {4} now
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}");

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Mobilized District", 3, 3);
        assertTappedCount("Mobilized District", true, 1);
        assertTappedCount("Plains", true, 3);
    }
}
