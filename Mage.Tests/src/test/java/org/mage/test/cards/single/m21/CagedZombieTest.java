package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CagedZombieTest extends CardTestPlayerBase {

    @Test
    public void loseLife(){

        // {1}{B}, {T}: Each opponent loses 2 life. Activate this ability only if a creature died this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Caged Zombie");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terror");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror", "Grizzly Bears");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }
}
