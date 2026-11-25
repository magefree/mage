
package org.mage.test.cards.bugs;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SpiderSlayerTest extends CardTestPlayerBase {

    @Test
    public void testGraveyardAbility() {
        addCard(Zone.GRAVEYARD, playerA,
                "Spider-Slayer, Hatred Honed");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "{6}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA,
                "Spider-Slayer, Hatred Honed", 0);

        assertExileCount(playerA,
                "Spider-Slayer, Hatred Honed", 1);

        assertTokenCount(playerA, "Robot Token", 2);

        assertTapped("Robot Token", true);
    }
}
