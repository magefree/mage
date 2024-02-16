package org.mage.test.cards.targets.attacking;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class DivineVerdictTest extends CardTestPlayerBase {

    @Test
    public void testAfterAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Divine Verdict");

        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        attack(2, playerB, "Sejiri Merfolk");
        checkPlayableAbility("Can't cast after combat", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Divine", false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Sejiri Merfolk", 1);
        assertLife(playerA, 18);
        assertLife(playerB, 22);
    }
}
