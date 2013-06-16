package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Loki
 */
public class LeaveNoTraceTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Leave No Trace");

        addCard(Zone.BATTLEFIELD, playerB, "Asceticism"); // Green
        addCard(Zone.BATTLEFIELD, playerB, "Awakening Zone"); // Green
        addCard(Zone.BATTLEFIELD, playerB, "Back from the Brink"); // Blue

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Leave No Trace", "Asceticism");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Asceticism", 0);
        assertPermanentCount(playerB, "Awakening Zone", 0);
        assertPermanentCount(playerB, "Back from the Brink", 1);
    }
}
