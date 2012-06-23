package org.mage.test.cards.cost.kicker;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class GatekeeperOfMalakirTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Constants.Zone.HAND, playerA, "Gatekeeper of Malakir");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Gatekeeper of Malakir");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Gatekeeper of Malakir", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertGraveyardCount(playerB, 1);
    }


}
