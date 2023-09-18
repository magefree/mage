package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Equipped creature doesn't untap during its controller's untap step.
        // Equipped creature has "{T}: This creature deals 2 damage to any target."
        // Equip {4)        
        addCard(Zone.BATTLEFIELD, playerA, "Heavy Arbalest");
        addCard(Zone.BATTLEFIELD, playerA, "Golem-Skin Gauntlets");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {4}", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertTrue(eliteVanguard.getAttachments().size() == 2);
        Assert.assertEquals(4, eliteVanguard.getPower().getValue());
        Assert.assertEquals(1, eliteVanguard.getToughness().getValue());
    }

}
