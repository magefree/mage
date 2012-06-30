package org.mage.test.cards.continuous;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class GoblinBushwhackerTest extends CardTestPlayerBase {

    /**
     * Tests doesn't work in library and in hand
     */
    @Test
    public void testDoesntWorkFromHand() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Goblin Bushwhacker");
        addCard(Constants.Zone.LIBRARY, playerA, "Goblin Bushwhacker");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Goblin Bushwhacker");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

}