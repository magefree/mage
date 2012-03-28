package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CounterlashTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Constants.Zone.HAND, playerB, "Counterlash");
        addCard(Constants.Zone.HAND, playerB, "Beacon of Immortality");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Counterlash", "Lightning Bolt");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 40);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);

    }

    
}
