package org.mage.test.cards.single.ons;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author cg5
 */
public class GratuitousViolenceTest extends CardTestPlayerBase {
    @Test
    public void testDoublesDamageFromCreatures() {
        // Enchantment: If a creature you control would deal damage to a creature
        // or player, it deals double that damage to that creature or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Gratuitous Violence");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Visionary"); // 1/1
        
        attack(1, playerA, "Elvish Visionary");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerB, 18);
    }
    
    @Test
    public void testIgnoresNonCreatures() {
        // Legendary Enchantment - Shrine: At the beginning of your upkeep, Honden of Infinite
        // Rage deals damage to any target equal to the number of Shrines you control.
        addCard(Zone.BATTLEFIELD, playerA, "Honden of Infinite Rage");
        addCard(Zone.BATTLEFIELD, playerA, "Gratuitous Violence");
        
        addTarget(playerA, playerB);
        
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        // Honden should deal 1 damage at upkeep (since playerA only
        // has one Shrine). GV should not double this.
        assertLife(playerB, 19);
    }
    
    @Test
    public void testIgnoresInstants() {
        addCard(Zone.BATTLEFIELD, playerA, "Gratuitous Violence");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerB, 17);
    }
}
