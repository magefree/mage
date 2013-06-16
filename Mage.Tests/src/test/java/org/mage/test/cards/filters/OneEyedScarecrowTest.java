package org.mage.test.cards.filters;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayrat
 */
public class OneEyedScarecrowTest extends CardTestPlayerBase {

    @Test
    public void testBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "One-Eyed Scarecrow");
        addCard(Zone.BATTLEFIELD, playerA, "Screeching Bat");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear");
        addCard(Zone.BATTLEFIELD, playerB, "Screeching Bat");
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.BATTLEFIELD, playerA, "One-Eyed Scarecrow", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Screeching Bat");
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
