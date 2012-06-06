package org.mage.test.cards.abilities.flicker;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class CloudshiftTest extends CardTestPlayerBase {

    /**
     * Tests that casting Cloudshift makes targeting spell fizzling
     */
    @Test
    public void testSpellFizzle() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Constants.Zone.HAND, playerA, "Cloudshift");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // should be alive because of Cloudshift
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }
}
