/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.flicker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class GisaGloriousResurrectorBlinkedTest extends CardTestPlayerBase {
    
    @Test
    public void testNormalCastAndRetrieve() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Gisa, Glorious Resurrector");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");  // mana for Lightning Bolt

        // The Memnite dies and gets exiled.  It should be put onto the battlefield under playerA's control during the next upkeep
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Memnite");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        // should be under playerA control
        assertPermanentCount(playerA, "Memnite", 1);
    }
    
    @Test
    public void testBlinkedGisa() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Gisa, Glorious Resurrector");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Flicker");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);  // mana for Lightning Bolt
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);  // mana for Flicker

        // The Memnite dies and gets exiled.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Memnite");
        
        // Blink the Gisa, Glorious Resurrector
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flicker", "Gisa, Glorious Resurrector");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        // exiled Memnite stays in exile zone
        assertPermanentCount(playerA, "Memnite", 0);
    }
}
