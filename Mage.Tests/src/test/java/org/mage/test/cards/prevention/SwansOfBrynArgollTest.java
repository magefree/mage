
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SwansOfBrynArgollTest extends CardTestPlayerBase{
    
    /**
     * Since you can't prevent damage that Combust deals, it should be able to kill Swans of Bryn Argoll. 
     */
    @Test
    public void testMarkOfAsylumEffect() {
        // 4/3 Flying
        // If a source would deal damage to Swans of Bryn Argoll, prevent that damage. The source's controller draws cards equal to the damage prevented this way.
        addCard(Zone.BATTLEFIELD, playerA, "Swans of Bryn Argoll");
        
        // Combust deals 5 damage to target white or blue creature. The damage can't be prevented.
        // Combust can't be countered.
        addCard(Zone.HAND, playerB, "Combust", 1);        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Combust", "Swans of Bryn Argoll");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Combust", 1);
        assertPermanentCount(playerA, "Swans of Bryn Argoll", 0);
        assertGraveyardCount(playerA, "Swans of Bryn Argoll", 1);
        assertHandCount(playerA, 0);

    }
    /**
     * Since you can't prevent damage that Combust deals, it should be able to kill Swans of Bryn Argoll. 
     */
    @Test
    public void testAgainstBanefire() {
        // 4/3 Flying
        // If a source would deal damage to Swans of Bryn Argoll, prevent that damage. The source's controller draws cards equal to the damage prevented this way.
        addCard(Zone.BATTLEFIELD, playerA, "Swans of Bryn Argoll");
        
        // Banefire deals X damage to any target.
        // If X is 5 or more, Banefire can't be countered and the damage can't be prevented.
        addCard(Zone.HAND, playerB, "Banefire", 1);        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 8);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", "Swans of Bryn Argoll");
        setChoice(playerB, "X=7");
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Banefire", 1);
        assertPermanentCount(playerA, "Swans of Bryn Argoll", 0);
        assertGraveyardCount(playerA, "Swans of Bryn Argoll", 1);
        assertHandCount(playerA, 0);

    }    
}