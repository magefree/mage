package org.mage.test.cards.abilities.keywords;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sublime Archangel");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);

        attack(2, playerB, "Llanowar Elves");

        setStopAt(2, Constants.PhaseStep.END_COMBAT);
        execute();

        // 1/1 and +4/+4
        assertLife(playerA, 15);
    }

}
