package org.mage.test.cards.filters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class WhipflareTest extends CardTestPlayerBase {

    @Test
    public void testDealDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Whipflare");

        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Myr Enforcer", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Myr Enforcer", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whipflare");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elite Vanguard", 0);
        assertPermanentCount(playerA, "Myr Enforcer", 2);
        assertPermanentCount(playerB, "Elite Vanguard", 0);
        assertPermanentCount(playerA, "Myr Enforcer", 2);
    }


}
