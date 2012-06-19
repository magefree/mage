package org.mage.test.cards.abilities.oneshot.regenerate;

import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class NecrobiteRegenerateTest extends CardTestPlayerBase {

    @Test
    public void testRegenerateAndDeathtouch() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Constants.Zone.HAND, playerB, "Necrobite");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Craw Wurm", "Elite Vanguard");
        castSpell(2, Constants.PhaseStep.DECLARE_BLOCKERS, playerB, "Necrobite", "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // blocker dies because of deathtouch
        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertGraveyardCount(playerA, "Craw Wurm", 1);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerB.getId());
        Assert.assertNotNull(eliteVanguard);

        // regenerate causes to tap
        Assert.assertTrue(eliteVanguard.isTapped());
    }
}
