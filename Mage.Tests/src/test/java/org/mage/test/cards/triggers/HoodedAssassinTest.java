
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Checks that the comes onto battlefield triggered modal ability works correctly.
 *
 * @author LevelX2
 */

public class HoodedAssassinTest extends CardTestPlayerBase {

    /*
     Hooded Assassin   1/2
     When Hooded Assassin enters the battlefield, choose one -
     * Put a +1/+1 counter on Hooded Assassin.
     * Destroy target creature that was dealt damage this turn.
    */

    @Test
    public void testFirstTriggeredAbility() {
        addCard(Zone.HAND, playerA, "Hooded Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hooded Assassin");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hooded Assassin", 1);
        assertPowerToughness(playerA, "Hooded Assassin", 2, 3);

    }
}