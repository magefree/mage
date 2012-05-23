package org.mage.test.cards.abilities.equipped;

import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class GolemSkinGauntletsTest extends CardTestPlayerBase {

    /**
     * Tests that creature will get +1/0 for each equipment
     */
    @Test
    public void testBoostOnEquip() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Heavy Arbalest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Golem-Skin Gauntlets");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {4}", "Elite Vanguard");
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Elite Vanguard");
        
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertTrue(eliteVanguard.getAttachments().size() == 2);
        Assert.assertEquals(4, eliteVanguard.getPower().getValue());
        Assert.assertEquals(1, eliteVanguard.getToughness().getValue());
    }

}
