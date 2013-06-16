package org.mage.test.cards.cost.variable;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class MikaeusTheLunarchTest extends CardTestPlayerBase {

    /**
     * Tests that Mikaeus, the Lunarch enters with X counters
     */
    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Mikaeus, the Lunarch");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mikaeus, the Lunarch");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mikaeus, the Lunarch", 1);
    }


}
