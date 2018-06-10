
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Dryad Militant
 * Creature - Dryad Soldier  1/1  {G/W}
 * If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
 * 
 * @author LevelX2
 */
public class DryadMilitantTest extends CardTestPlayerBase {
    
    /**
     * Tests that an instant or sorcery card is moved to exile
     */
    @Test
    public void testNormalCase() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 17);
        assertExileCount("Lightning Bolt", 1);
    }    
    /**
     * Tests if Dryad Militant dies by damage spell, the
     * spell gets exiled
     */
    @Test
    public void testDiesByDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dryad Militant");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 20);
        
        assertExileCount("Lightning Bolt", 1);
    }   
    
    /**
     * Tests if Dryad Militant dies by destroy spell, the
     * spell don't get exiled
     */
    @Test
    public void testDiesByDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Terminate");
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminate", "Dryad Militant");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 20);

        assertHandCount(playerA, "Terminate", 0);
        assertGraveyardCount(playerA, "Terminate", 1);
    }       
}


