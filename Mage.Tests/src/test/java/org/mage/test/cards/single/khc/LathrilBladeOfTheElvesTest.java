package org.mage.test.cards.single.khc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LathrilBladeOfTheElvesTest extends CardTestPlayerBase {

    @Test
    public void cannotActivateWithNineOtherElves() {
        addCard(Zone.BATTLEFIELD, playerA, "Lathril, Blade of the Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 9);

        checkPlayableAbility("nine other Elves", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Tap ten", false);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void canActivateWithTenOtherElves() {
        addCard(Zone.BATTLEFIELD, playerA, "Lathril, Blade of the Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Tap ten");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 10);
    }
}
