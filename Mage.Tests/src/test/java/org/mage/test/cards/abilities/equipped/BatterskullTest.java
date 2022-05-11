

package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Batterskull          {5}
 * Artifact - Equipment
 * Living weapon (When this Equipment enters the battlefield, put a 0/0 black Germ creature token onto the battlefield, then attach this to it.)
 * Equipped creature gets +4/+4 and has vigilance and lifelink.
 * {3}: Return Batterskull to its owner's hand.
 * Equip {5}
 * 
 * 
 * @author LevelX2
 */
public class BatterskullTest extends CardTestPlayerBase {
    
    /**
     * Tests that Batterskull gets attached to the Germ creature token
     */
    @Test
    public void testEquippedToGerm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Batterskull");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Batterskull");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Batterskull", 1);
        assertPermanentCount(playerA, "Phyrexian Germ Token", 1);
        assertPowerToughness(playerA, "Phyrexian Germ Token", 4, 4);
    }
}
