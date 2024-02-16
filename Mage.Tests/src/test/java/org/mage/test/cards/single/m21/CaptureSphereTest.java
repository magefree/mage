package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CaptureSphereTest extends CardTestPlayerBase {

    @Test
    public void testDontUntap(){
        // Flash
        // Enchant creature
        // When Capture Sphere enters the battlefield, tap enchanted creature.
        // Enchanted creature doesn't untap during its controller's untap step.

        addCard(Zone.HAND, playerA, "Capture Sphere");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA,"Capture Sphere", "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Grizzly Bears", true);
    }
}
