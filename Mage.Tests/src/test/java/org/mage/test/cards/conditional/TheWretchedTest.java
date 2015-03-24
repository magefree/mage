/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class TheWretchedTest extends CardTestPlayerBase {

    @Test
    public void testGainControl_One_NoRegenThusNothingIsRemovedFromCombat() {

        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration
        
        attack(2, playerA, "The Wretched");
        block(2, playerB, "Wall of Pine Needles", "The Wretched");
        block(2, playerB, "Living Wall", "The Wretched");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Wretched", 1);
        assertPermanentCount(playerA, "Wall of Pine Needles", 1);
        assertPermanentCount(playerA, "Living Wall", 1);
    }

    @Test
    public void testGainControl_One_RegenWhichRemovesBlockerFromCombat() {

        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration
        
        attack(2, playerA, "The Wretched");
        block(2, playerB, "Wall of Pine Needles", "The Wretched");
        block(2, playerB, "Living Wall", "The Wretched");
        
        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerB, "{G}: Regenerate {this}.");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Wretched", 1);
        assertPermanentCount(playerA, "Living Wall", 1);
        assertPermanentCount(playerB, "Wall of Pine Needles", 1);

    }
    
    @Test
    public void testLoseControlOfTheWretched() {

        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration
        
        attack(2, playerA, "The Wretched");
        block(2, playerB, "Wall of Pine Needles", "The Wretched");
        block(2, playerB, "Living Wall", "The Wretched");
        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Control Magic", "The Wretched");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "The Wretched", 1);
        assertPermanentCount(playerB, "Wall of Pine Needles", 1);
        assertPermanentCount(playerB, "Living Wall", 1);
    }
    
    @Test
    public void testRegenTheWretchedThusRemovingFromCombat() {

        addCard(Zone.BATTLEFIELD, playerA, "The Wretched");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Pine Needles"); // a 3/3 with regeneration
        addCard(Zone.BATTLEFIELD, playerB, "Living Wall"); // 0/6 Wall with regeneration
        
        attack(2, playerA, "The Wretched");
        block(2, playerB, "Wall of Pine Needles", "The Wretched");
        block(2, playerB, "Living Wall", "The Wretched");
        
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Regenerate", "The Wretched");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Wretched", 1);
        assertPermanentCount(playerB, "Wall of Pine Needles", 1);
        assertPermanentCount(playerB, "Living Wall", 1);
    }
}
