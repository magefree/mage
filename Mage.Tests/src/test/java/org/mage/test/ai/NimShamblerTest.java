package org.mage.test.ai;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 */
public class NimShamblerTest extends CardTestBase {

    /**
     * Reproduces the bug when AI sacrifices its creatures for no reason.
     */
    @Test
    public void testNoCreatureWasSacrificed() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Nim Shambler");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blood Cultist");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ob Nixilis, the Fallen");

        execute();

        Permanent nimShambler = getPermanent("Nim Shambler", playerA.getId());
        Assert.assertNotNull(nimShambler);

        Permanent bloodCultist = getPermanent("Blood Cultist", playerA.getId());
        Assert.assertNotNull(bloodCultist);

    }
}
