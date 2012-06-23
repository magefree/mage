package org.mage.test.cards.control;

import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class BattlefieldTriggeredAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void testBeguilerofWillsAndPrimevalTitan() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Primeval Titan");
        addCard(Constants.Zone.LIBRARY, playerA, "Mountain", 10);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Beguiler of Wills");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord", 5);
        addCard(Constants.Zone.LIBRARY, playerB, "Mountain", 10);

        activateAbility(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Gain control", "Primeval Titan");
        attack(4, playerB, "Primeval Titan");

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 14);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Beguiler of Wills", 1);
        assertPermanentCount(playerB, "Arrogant Bloodlord", 5);
        assertPermanentCount(playerB, "Primeval Titan", 1);

        // lands weren't added to playerA
        assertPermanentCount(playerA, "Mountain", 0);
        // but to playerB instead
        int playerACount = 0;
        int playerBCount = 0;
        for (Permanent p : currentGame.getBattlefield().getAllActivePermanents()) {
            if (p.getCardType().contains(Constants.CardType.LAND)) {
                if (p.getControllerId().equals(playerB.getId())) {
                    playerBCount++;
                }
                if (p.getControllerId().equals(playerA.getId())) {
                    playerACount++;
                }
            }
        }

        Assert.assertEquals(0, playerACount);
        Assert.assertEquals(2, playerBCount);
    }

}
