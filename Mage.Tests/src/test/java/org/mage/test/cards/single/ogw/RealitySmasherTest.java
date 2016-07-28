/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.single.ogw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Poor Reality Smasher is going to take such a beating with all these tests.
 * 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class RealitySmasherTest extends CardTestPlayerBase {
    
    @Ignore
    @Test
    public void testSimpleKillSpellChooseToDiscard() {
        
        // Reality Smasher {4}{<>} Trample, haste 5/5
        // Whenever Reality Smasher becomes the target of a spell an opponent controls, counter that spell unless its controller discards a card. 
        addCard(Zone.BATTLEFIELD, playerA, "Reality Smasher"); 
        addCard(Zone.HAND, playerB, "Doom Blade"); // {1}{B} destroy target non-black creature
        addCard(Zone.HAND, playerB, "Sigiled Starfish"); // discard fodder
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade");
        addTarget(playerB, "Reality Smasher");
        setChoice(playerB, "Yes"); // discard to prevent counter
        setChoice(playerB, "Sigiled Starfish");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Doom Blade", 1);        
        assertHandCount(playerB, "Sigiled Starfish", 0);
        assertGraveyardCount(playerB, "Sigiled Starfish", 1);
        assertGraveyardCount(playerA, "Reality Smasher", 1);
    }
    
    @Ignore
    @Test
    public void testSimpleKillSpellChooseNotToDiscard() {
        
        // Reality Smasher {4}{<>} Trample, haste 5/5
        // Whenever Reality Smasher becomes the target of a spell an opponent controls, counter that spell unless its controller discards a card. 
        addCard(Zone.BATTLEFIELD, playerA, "Reality Smasher"); 
        addCard(Zone.HAND, playerB, "Doom Blade"); // {1}{B} destroy target non-black creature
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade");
        addTarget(playerB, "Reality Smasher");
        setChoice(playerB, "No"); // no discard
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Doom Blade", 1);
        assertPermanentCount(playerA, "Reality Smasher", 1);
    }
}
