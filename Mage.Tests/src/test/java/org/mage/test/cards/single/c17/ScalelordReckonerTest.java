package org.mage.test.cards.single.c17;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ScalelordReckonerTest extends CardTestPlayerBase {

    private static final String scalelord = "Scalelord Reckoner"; // 4/4 Flying
    // Whenever a Dragon you control becomes the target of a spell or ability an opponent controls,
    // destroy target nonland permanent that player controls.

    @Test
    public void testScalelordReckoner() {

        String machete = "Trusty Machete"; // a nonland permanent to destroy
        String shock = "Shock"; // a spell to target with

        addCard(Zone.BATTLEFIELD, playerA, scalelord);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, machete, 1);
        addCard(Zone.HAND, playerB, shock, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, shock, scalelord);
        addTarget(playerA, machete);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerA, scalelord, 2);
        assertGraveyardCount(playerB, machete, 1);
        
    }
}
