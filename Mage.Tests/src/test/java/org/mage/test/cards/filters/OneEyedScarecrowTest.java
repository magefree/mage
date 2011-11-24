package org.mage.test.cards.filters;

import junit.framework.Assert;
import mage.Constants;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayrat
 */
public class OneEyedScarecrowTest extends CardTestBase {

    @Test
    public void testBoost() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "One-Eyed Scarecrow");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Screeching Bat");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Runeclaw Bear");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Screeching Bat");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Runeclaw Bear");

        execute();

        Permanent scarecrow = getPermanent("One-Eyed Scarecrow", playerA.getId());
        Assert.assertNotNull(scarecrow);
        Assert.assertEquals(2, scarecrow.getPower().getValue());
        Assert.assertEquals(3, scarecrow.getToughness().getValue());

        // still 2/2 - flying, but not under opponent's control
        Permanent screechingBat = getPermanent("Screeching Bat", playerA.getId());
        Assert.assertNotNull(screechingBat);
        Assert.assertEquals(2, screechingBat.getPower().getValue());
        Assert.assertEquals(2, screechingBat.getToughness().getValue());

        // 2/2
        Permanent runeclawBear = getPermanent("Runeclaw Bear", playerA.getId());
        Assert.assertNotNull(runeclawBear);
        Assert.assertEquals(2, runeclawBear.getPower().getValue());
        Assert.assertEquals(2, runeclawBear.getToughness().getValue());

        // 1/2
        Permanent screechingBatOpp = getPermanent("Screeching Bat", playerB.getId());
        Assert.assertNotNull(screechingBatOpp);
        Assert.assertEquals(1, screechingBatOpp.getPower().getValue());
        Assert.assertEquals(2, screechingBatOpp.getToughness().getValue());

        // still 2/2 - not flying
        Permanent runeclawBearOpp = getPermanent("Runeclaw Bear", playerB.getId());
        Assert.assertNotNull(runeclawBearOpp);
        Assert.assertEquals(2, runeclawBearOpp.getPower().getValue());
        Assert.assertEquals(2, runeclawBearOpp.getToughness().getValue());
    }

    @Test
    public void testMultiEffects() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "One-Eyed Scarecrow", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Screeching Bat");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Runeclaw Bear");

        execute();

        // -1/2
        Permanent screechingBatOpp = getPermanent("Screeching Bat", playerB.getId());
        Assert.assertNotNull(screechingBatOpp);
        Assert.assertEquals(-1, screechingBatOpp.getPower().getValue());
        Assert.assertEquals(2, screechingBatOpp.getToughness().getValue());

        // still 2/2 - not flying
        Permanent runeclawBearOpp = getPermanent("Runeclaw Bear", playerB.getId());
        Assert.assertNotNull(runeclawBearOpp);
        Assert.assertEquals(2, runeclawBearOpp.getPower().getValue());
        Assert.assertEquals(2, runeclawBearOpp.getToughness().getValue());
    }

}
