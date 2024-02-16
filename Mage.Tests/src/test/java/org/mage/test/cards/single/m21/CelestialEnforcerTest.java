package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CelestialEnforcerTest extends CardTestPlayerBase {

    @Test
    public void controlFlyingCreature(){
        // {1}{W}, {T}: Tap target creature. Activate this ability only if you control a creature with flying.
        addCard(Zone.BATTLEFIELD, playerA, "Celestial Enforcer");
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}", "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Grizzly Bears", true);
    }
}
