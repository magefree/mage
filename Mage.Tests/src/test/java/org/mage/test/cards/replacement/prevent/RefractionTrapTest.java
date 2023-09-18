
package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class RefractionTrapTest extends CardTestPlayerBase {

    /**
     * I found a bug when i cast Refraction Trap I choose to redirect damage to
     * opponent creature and it stays alive (in 2 situations)
     */
    @Test
    public void testPreventDamageFromSpell() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // If an opponent cast a red instant or sorcery spell this turn, you may pay {W} 
        // rather than pay Refraction Trap's mana cost.
        // Prevent the next 3 damage that a source of your choice would deal to you and/or 
        // permanents you control this turn. If damage is prevented this way, Refraction Trap
        // deals that much damage to any target.
        addCard(Zone.HAND, playerB, "Refraction Trap");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Refraction Trap", "Silvercoat Lion", "Lightning Bolt");
        setChoice(playerB, true);
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Refraction Trap", 1);
        
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        
    }

}