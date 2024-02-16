package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ZuberasTest extends CardTestPlayerBase {
    /*
     * Ashen-Skin Zubera
     * Creature — Zubera Spirit 1/2, 1B (2)
     * When Ashen-Skin Zubera dies, target opponent discards a card for each 
     * Zubera that died this turn.
    
     * Dripping-Tongue Zubera
     * Creature — Zubera Spirit 1/2, 1G (2)
     * When Dripping-Tongue Zubera dies, put a 1/1 colorless Spirit creature 
     * token onto the battlefield for each Zubera that died this turn.
    
     * Ember-Fist Zubera
     * Creature — Zubera Spirit 1/2, 1R (2)
     * When Ember-Fist Zubera dies, it deals damage to any target
     * equal to the number of Zubera that died this turn.

     * Floating-Dream Zubera
     * Creature — Zubera Spirit 1/2, 1U (2)
     * When Floating-Dream Zubera dies, draw a card for each Zubera that died 
     * this turn.
    
     * Silent-Chant Zubera
     * Creature — Zubera Spirit 1/2, 1W (2)
     * When Silent-Chant Zubera dies, you gain 2 life for each Zubera that died this turn.
    */
    
    // test that creatures damaged by Aggravate attack
    @Test
    public void testZuberas() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Ashen-Skin Zubera");
        addCard(Zone.BATTLEFIELD, playerA, "Dripping-Tongue Zubera");
        addCard(Zone.BATTLEFIELD, playerA, "Ember-Fist Zubera");
        addCard(Zone.BATTLEFIELD, playerA, "Floating-Dream Zubera");
        addCard(Zone.BATTLEFIELD, playerA, "Silent-Chant Zubera");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 5);
        addCard(Zone.HAND, playerB, "Island", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silent-Chant Zubera");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Floating-Dream Zubera");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ember-Fist Zubera");
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ashen-Skin Zubera");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dripping-Tongue Zubera");
        
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();
                
        assertPermanentCount(playerA, "Spirit Token", 1);
        assertHandCount(playerB, 1);
        assertHandCount(playerA, 4);
        assertLife(playerB, 17);
        assertLife(playerA, 30);
        
    }
    

}
