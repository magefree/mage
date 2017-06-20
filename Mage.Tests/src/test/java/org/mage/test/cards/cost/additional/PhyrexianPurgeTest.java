package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PhyrexianPurgeTest extends CardTestPlayerBase {

    /*
     Target two creatures, pay 3 life for each.
    */
    @Test
    public void simpleTest() {
        
        String pPurge = "Phyrexian Purge";
        
        // Sorcery {2}{B}{R}
        // Destroy any number of target creatures.
        // Phyrexian Purge costs 3 life more to cast for each target.
        addCard(Zone.HAND, playerA, pPurge);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // {0} 1/1 artifact creature
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // {3}{R} 3/3
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pPurge);
        addTarget(playerA, "Memnite^Hill Giant");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, pPurge, 1);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertLife(playerA, 14); // lost 6 life (3 * 2 targets)
    }
    
    /*
     * Target two creatures, one of them bounced before resolution. Still pay 6 life.
    */
    @Test
    public void bouncedCreatureStillPaysFullCost() {
        
        String pPurge = "Phyrexian Purge";
        
        // Sorcery {2}{B}{R}
        // Destroy any number of target creatures.
        // Phyrexian Purge costs 3 life more to cast for each target.
        addCard(Zone.HAND, playerA, pPurge);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.HAND, playerB, "Unsummon"); // {U} instant return target creature back to hand
        addCard(Zone.BATTLEFIELD, playerB, "Island"); 
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // {0} 1/1 artifact creature
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // {3}{R} 3/3
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pPurge);
        addTarget(playerA, "Memnite^Hill Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", "Hill Giant");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, pPurge, 1);
        assertGraveyardCount(playerB, "Unsummon", 1);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertHandCount(playerB, "Hill Giant", 1);
        assertLife(playerA, 14); // lost 6 life (3 * 2 targets)
    }
    
    /*
     * Target two creatures, spell is countered. Still pays 6 life.
    */
    @Test
    public void counteredSpellStillPaysFullCost() {
        
        String pPurge = "Phyrexian Purge";
        
        // Sorcery {2}{B}{R}
        // Destroy any number of target creatures.
        // Phyrexian Purge costs 3 life more to cast for each target.
        addCard(Zone.HAND, playerA, pPurge);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.HAND, playerB, "Counterspell"); // {U}{U} instant - counter target spell
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2); 
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // {0} 1/1 artifact creature
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // {3}{R} 3/3
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pPurge);
        addTarget(playerA, "Memnite^Hill Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", pPurge);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, pPurge, 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        assertPermanentCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Hill Giant", 1);
        assertLife(playerA, 14); // lost 6 life (3 * 2 targets)
    }
}
