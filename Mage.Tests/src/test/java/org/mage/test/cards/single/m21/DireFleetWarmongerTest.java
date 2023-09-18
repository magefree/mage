package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DireFleetWarmongerTest extends CardTestPlayerBase {


    @Test
    public void sacCreatureToBoost() {
        // At the beginning of combat on your turn, you may sacrifice another creature.
        // If you do, Dire Fleet Warmonger gets +2/+2 and gains trample until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Dire Fleet Warmonger");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        setChoice(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Dire Fleet Warmonger", 5, 5);
    }
}
