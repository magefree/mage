package org.mage.test.cards.copy;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CryptoplasmTest extends CardTestPlayerBase {

    @Test
    public void testTransform() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Cryptoplasm", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm", 1);

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 2);
    }
}
