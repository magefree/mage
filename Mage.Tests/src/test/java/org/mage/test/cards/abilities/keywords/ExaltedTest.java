package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class ExaltedTest extends CardTestPlayerBase {

    /**
     * Tests multiple exalted
     */
    @Test
    public void testBeingBlocked() {
        addCard(Zone.BATTLEFIELD, playerB, "Sublime Archangel");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);

        attack(2, playerB, "Llanowar Elves");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        // 1/1 and +4/+4
        assertLife(playerA, 15);
    }

}
