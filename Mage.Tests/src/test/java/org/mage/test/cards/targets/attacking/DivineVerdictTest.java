package org.mage.test.cards.targets.attacking;

import mage.Constants;
import mage.Constants.PhaseStep;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class DivineVerdictTest extends CardTestPlayerBase {

    @Test
    public void testAfterAttack() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.HAND, playerA, "Divine Verdict");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains");

        attack(2, playerB, "Sejiri Merfolk");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Divine Verdict", "Sejiri Merfolk");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Sejiri Merfolk", 1);
        assertLife(playerA, 18);
        assertLife(playerB, 22);
    }


}
