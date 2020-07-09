package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Johnny E. Hastings
 */
public class SoulBurnTest extends CardTestPlayerBase {
    
    @Test
    public void testDamageOpponentAllBlackMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Soul Burn");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }   

    @Test
    public void testDamageOpponentOneBlackFourRedMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Soul Burn");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }  
    
    @Test
    public void testDamageOpponentAllKindsOfMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");        
        addCard(Zone.HAND, playerA, "Soul Burn");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 21);
        assertLife(playerB, 17);
    } 
    
    @Test
    public void testDamageSelfAllSwamps() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");      
        addCard(Zone.HAND, playerA, "Soul Burn");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
    
    @Test
    public void testDamageSelfWithSwampsAndMountains() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");      
        addCard(Zone.HAND, playerA, "Soul Burn");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    @Test
    public void testDamageSmallCreatureAllSwamps() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");   
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");  
        addCard(Zone.HAND, playerA, "Soul Burn");
        
        addCard(Zone.BATTLEFIELD, playerB, "Bog Imp");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", "Bog Imp");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Bog Imp", 0);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

    @Test
    public void testDamageSmallCreatureSwampsAndMountains() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");   
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");  
        addCard(Zone.HAND, playerA, "Soul Burn");
        
        addCard(Zone.BATTLEFIELD, playerB, "Bog Imp");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", "Bog Imp");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
         execute();
        assertPermanentCount(playerB, "Bog Imp", 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }    
   
    @Test
    public void testDamageBigCreatureAllSwamps() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp"); 
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");   
        addCard(Zone.HAND, playerA, "Soul Burn");
        
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", "Craw Wurm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }    
    
    @Test
    public void testDamageBigCreatureSwampsAndMountains() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain"); 
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");  
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");   
        addCard(Zone.HAND, playerA, "Soul Burn");
        
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Burn", "Craw Wurm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

}
