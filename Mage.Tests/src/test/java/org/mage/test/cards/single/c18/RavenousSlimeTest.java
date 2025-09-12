
package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author notgreat
 */
public class RavenousSlimeTest extends CardTestPlayerBase {

    @Test
    public void testRavenousSlime() {
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Ravenous Slime");
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear");
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Runeclaw Bear");
        checkPT("Ravenous Slime ate Runeclaw Bear", 1, PhaseStep.BEGIN_COMBAT, playerA, "Ravenous Slime", 3, 3);

        attack(1, playerA, "Ravenous Slime");
        block(1, playerB, "Centaur Courser", "Ravenous Slime");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Runeclaw Bear", 1);
        assertExileCount(playerB, "Centaur Courser", 1);
        assertGraveyardCount(playerA, "Ravenous Slime", 1);
    }
}
