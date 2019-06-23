/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SkylineCascadeTest extends CardTestPlayerBase {

    /**
     * Reported bug on Skyline Cascade not working properly.
     * 
     * Test the typical situation - tapped creature not being able to untap during next untap step.
     */
    @Test
    public void testPreventsTappedCreatureUntapping() {
                
        // {W} 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        
        /**
         * Skyline Cascade enters the battlefield tapped.
         * When Skyline Cascade enters the battlefield, target creature an opponent controls doesn't untap during its controller's next untap step.
         * Tap: Add {U} .
         */        
        addCard(Zone.HAND, playerB, "Skyline Cascade");
                
        attack(1, playerA, "Savannah Lions");

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Skyline Cascade");
        addTarget(playerB, "Savannah Lions");
        
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        
        execute();
        
        assertTapped("Savannah Lions", true);
        assertTapped("Skyline Cascade", true);
    }
    
    /**
     * Reported bug on Skyline Cascade not working properly.
     * 
     * "Skyline Cascade's triggered ability doesn't tap the creature. It can target any creature, tapped or untapped. 
     * If that creature is already untapped at the beginning of its controller's next untap step, the effect won't do anything."
     * https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=402038
     * 
     * An untapped creature will remain untapped.
     */
    @Test
    public void testDoesNotStopUntappedCreatureUntapping() {
                
        // {W} 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        
        /**
         * Skyline Cascade enters the battlefield tapped.
         * When Skyline Cascade enters the battlefield, target creature an opponent controls doesn't untap during its controller's next untap step.
         * Tap: Add {U} .
         */        
        addCard(Zone.HAND, playerB, "Skyline Cascade");
                
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Skyline Cascade");
        addTarget(playerB, "Savannah Lions");
        
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        
        execute();
        
        assertTapped("Savannah Lions", false);
        assertTapped("Skyline Cascade", true);
    }
}