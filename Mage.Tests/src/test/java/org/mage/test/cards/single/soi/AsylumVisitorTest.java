package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests for Asylum Visitor:
 * {1}{B} 3/1 Vampire Wizard Creature
 * 
 * At the beginning of each player's upkeep, if that player has no cards in hand, you draw a card and you lose 1 life.
 * Madness {1}{B}
 * 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class AsylumVisitorTest extends CardTestPlayerBase {
    
    /**
     * No cards in own hand - draw card and lose life.
     */
    @Test
    public void testNoCardsInOwnHand() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Asylum Visitor", 1);

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertHandCount(playerA, 1);
    }   
    
    /**
     * Cards in own hand.
     */
    @Test
    public void testCardsInOwnHand() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Asylum Visitor", 1);
        addCard(Zone.HAND, playerA, "Bronze Sable", 3); // 2/1 artifact creature

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 3); // 3 bronze sables!
    }   
    
    /**
     * No cards in opponent's hand - draw card and lose life.
     */
    @Test
    public void testNoCardsInOpponentsHand() {
        
        addCard(Zone.HAND, playerA, "Asylum Visitor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Asylum Visitor");
        
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertHandCount(playerA, 1);
    }   
    
    /**
     * Cards in opponent's hand.
     */
    @Test
    public void testCardsInOpponentsHand() {
        
        addCard(Zone.HAND, playerA, "Asylum Visitor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3); 
        addCard(Zone.HAND, playerB, "Bronze Sable", 3); // 2/1 artifact creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Asylum Visitor");
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 3); // 3 bronze sables!
    } 
}
