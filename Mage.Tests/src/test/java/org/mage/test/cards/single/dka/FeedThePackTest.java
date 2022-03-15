package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class FeedThePackTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Feed the Pack");
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Wolf Token", 4);
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

}
