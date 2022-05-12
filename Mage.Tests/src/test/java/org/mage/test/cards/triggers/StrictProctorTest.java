/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class StrictProctorTest extends CardTestPlayerBase {

    @Test
    public void testStrictProctorPaid() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Whenever a permanent entering the battlefield causes a triggered ability to 
        // trigger, counter that ability unless its controller pays {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Strict Proctor", 1);
        // Whenever a land enters the battlefield under your control, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Primeval Bounty", 1);
        // land to play for trigger
        addCard(Zone.HAND, playerA, "Swamp", 1);
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");  // play land
        setChoice(playerA, "Yes");  // pay the {2} mana cost to prevent the countering of the trigger
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 23); // player gains the 3 life
        
    }
    
    @Test
    public void testStrictProctorNotPaid() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Whenever a permanent entering the battlefield causes a triggered ability to 
        // trigger, counter that ability unless its controller pays {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Strict Proctor", 1);
        // Whenever a land enters the battlefield under your control, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Primeval Bounty", 1);
        // land to play for trigger
        addCard(Zone.HAND, playerA, "Swamp", 1);
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");  // play land
        setChoice(playerA, "No");  // do not pay the {2} mana cost to prevent the countering of the trigger
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 20); // player does not gain the 3 life
        
    }
}
