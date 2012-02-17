package org.mage.test.ai;

import junit.framework.Assert;
import mage.Constants;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Ignore;
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
    @Ignore
    public void testNoCreatureWasSacrificed() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Nim Shambler");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blood Cultist");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        execute();

        Permanent nimShambler = getPermanent("Nim Shambler", playerA.getId());
        Assert.assertNotNull(nimShambler);

        Permanent bloodCultist = getPermanent("Blood Cultist", playerA.getId());
        Assert.assertNotNull(bloodCultist);
        Assert.assertFalse(bloodCultist.isTapped()); // shouldn't be tapped
    }

    @Test
    @Ignore
    public void testAttackAndKillBlockerWithAdditionalDamage() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Nim Shambler");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blood Cultist");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ob Nixilis, the Fallen");

        execute();

        // should die in attack
        assertPermanentCount(playerA, "Nim Shambler", 0);
        // should die because of attack + 1 damage from Blood Cultist
        assertPermanentCount(playerA, "Ob Nixilis, the Fallen", 0);

        // Blood Cultist should kill Ob Nixilis, the Fallen and get +1\+1
        Permanent bloodCultist = getPermanent("Blood Cultist", playerA.getId());
        Assert.assertNotNull(bloodCultist);
        Assert.assertEquals(1, bloodCultist.getCounters().size());
        Assert.assertEquals(1, bloodCultist.getCounters().getCount(CounterType.P1P1));
    }
}
