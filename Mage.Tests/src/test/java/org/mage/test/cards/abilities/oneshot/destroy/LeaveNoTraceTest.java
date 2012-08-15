package org.mage.test.cards.abilities.oneshot.destroy;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Loki
 */
public class LeaveNoTraceTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Leave No Trace");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Asceticism"); // Green
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Awakening Zone"); // Green
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Back from the Brink"); // Blue

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Leave No Trace", "Asceticism");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Asceticism", 0);
        assertPermanentCount(playerB, "Awakening Zone", 0);
        assertPermanentCount(playerB, "Back from the Brink", 1);
    }
}
