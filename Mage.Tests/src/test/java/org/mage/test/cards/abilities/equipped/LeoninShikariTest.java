package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class LeoninShikariTest extends CardTestPlayerBase {

    /**
     * Test you can equip during combat
     */
    @Test
    public void testEquipInstant() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // You may activate equip abilities any time you could cast an instant.
        addCard(Zone.BATTLEFIELD, playerA, "Leonin Shikari");        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");        
        // Equipped creature gets +1/+1.        
        addCard(Zone.BATTLEFIELD, playerA, "Leonin Scimitar");

        
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);        
        addCard(Zone.HAND, playerB, "Boomerang");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Equip {1}", "Silvercoat Lion");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Boomerang", "Leonin Scimitar", "Equip");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Leonin Scimitar");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerB, "Boomerang", 1);

        assertPermanentCount(playerA, "Leonin Scimitar", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA.getId());
        Assert.assertTrue("Silvercoat Lion may not have any attachments", silvercoatLion.getAttachments().isEmpty());
        
        Permanent leoninScimitar = getPermanent("Leonin Scimitar", playerA.getId());
        Assert.assertTrue(leoninScimitar.getAttachedTo() == null);
    }

}
