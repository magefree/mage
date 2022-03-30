package org.mage.test.cards.single.pc2;

import mage.constants.PhaseStep;
import mage.constants.Planes;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

public class FracturedPowerstoneTest extends CardTestPlayerBaseWithAIHelps {
    
    @Test
    public void test_FracturedPowerstone_Single() {
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);
        addCard(Zone.BATTLEFIELD, playerA, "Fractured Powerstone", 1);

        // first chaos
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        //second chaos (fractured powerstone)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 2);
        assertTappedCount("Fractured Powerstone", true, 1);
    }
    
    @Test
    public void test_FracturedPowerstone_NoCost() {
        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        addPlane(playerA, Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Fractured Powerstone", 1);

        // first chaos
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        //second chaos (fractured powerstone)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // third chaos (with additional cost)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Roll the planar");
        setDieRollResult(playerA, 1); // make chaos

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Eldrazi Token", 3);
        assertTappedCount("Mountain", true, 1); // cost for second planar die
    }
}
