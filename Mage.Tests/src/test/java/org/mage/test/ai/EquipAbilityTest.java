package org.mage.test.ai;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Make sure AI uses equip ability once.
 *
 * @author ayratn
 */
public class EquipAbilityTest extends CardTestBase {

    @Test
    @Ignore
    public void testLevelUpAbilityUsage() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Steel Wall");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blade of the Bloodchief");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blazing Torch");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sacred Foundry", 1);

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent wall = getPermanent("Steel Wall", playerA.getId());
        Assert.assertNotNull(wall);

        Assert.assertEquals("Not equipped", 2, wall.getAttachments().size());

        int count = 0;
        int tapped = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(playerA.getId())) {
                if (permanent.getCardType().contains(Constants.CardType.LAND)) {
                    count++;
                    if (permanent.isTapped()) {
                        tapped++;
                    }
                }
            }
        }
        Assert.assertEquals(6, count);
        Assert.assertEquals(2, tapped);
    }
}
