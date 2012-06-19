package org.mage.test.cards.filters;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class WhipflareTest extends CardTestPlayerBase {

    @Test
    public void testDealDamage() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Constants.Zone.HAND, playerA, "Whipflare");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Myr Enforcer", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Myr Enforcer", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Whipflare");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPermanentCount(playerA, "Myr Enforcer", 2);
        assertPermanentCount(playerB, "Elite Vanguard", 0);
        assertPermanentCount(playerA, "Myr Enforcer", 2);
    }


}
