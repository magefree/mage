package org.mage.test.ai;

import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Make sure AI uses regenerate ability once.
 *
 * @author ayratn
 */
public class RegenerateAbilityTest extends CardTestBase {

    @Test
    @Ignore
    public void testRegenerateUsage() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Quicksilver Gargantuan", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Thousand-legged Kami", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Slith Bloodletter");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Drowned Catacomb", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Crumbling Necropolis", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Wastes", 1);

        execute();

        Permanent slith = getPermanent("Slith Bloodletter", playerB.getId());
        Assert.assertNotNull("Should be alive because of Regenerate ability", slith);

        int count = 0;
        int tapped = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(playerB.getId())) {
                if (permanent.getCardType().contains(Constants.CardType.LAND)) {
                    count++;
                    if (permanent.isTapped()) {
                        tapped++;
                    }
                }
            }
        }
        Assert.assertEquals(7, count);
        Assert.assertEquals(2, tapped);

        // should block 7/7 (Quicksilver Gargantuan) and allow 6/6 (Thousand-legged Kami) to attack
        assertLife(playerB, 14);
    }
}
