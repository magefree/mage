package org.mage.test.cards.single.som;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DissipationFieldTest extends CardTestPlayerBase {

    private static final String dissipation = "Dissipation Field";
    // Whenever a permanent deals damage to you, return it to its owner’s hand.

    private static final String rod = "Rod of Ruin";

    @Test
    public void testDamageTrigger() {
        addCard(Zone.BATTLEFIELD, playerB, dissipation);
        addCard(Zone.BATTLEFIELD, playerA, rod);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}:", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, rod, 0);
        assertHandCount(playerA, rod, 1);

    }

}
