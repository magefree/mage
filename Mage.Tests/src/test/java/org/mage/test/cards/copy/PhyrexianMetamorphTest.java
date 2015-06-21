/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class PhyrexianMetamorphTest extends CardTestPlayerBase {


    @Test
    public void testCopyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph"); // {3}{UP}
        addCard(Zone.HAND, playerA, "Cloudshift");
        
        //Flying
        // Vanishing 3 (This permanent enters the battlefield with three time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)
        // When Aven Riftwatcher enters the battlefield or leaves the battlefield, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Aven Riftwatcher");  // 2/3

        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Ponyback Brigade");  // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Aven Riftwatcher");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Aven Riftwatcher");
        setChoice(playerA, "Ponyback Brigade");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerA, "Cloudshift", 1);
        
        assertPermanentCount(playerA, "Ponyback Brigade", 1);
        assertPermanentCount(playerA, "Goblin", 3);

    }  

    /**
     * An opponent cast Phyrexian Metamorph and cloned another opponent's
     * Maelstrom Wanderer(his Commander). The first opponent then dealt combat
     * damage with Brago, King Eternal and chose to flicker several permanents,
     * including the Phyrexian Metamorph/Maelstrom Wanderer, but he was not able
     * to choose a new creature to clone when the Phyrexian Metamorph re-entered
     * the battlefield.
     */

    @Test
    public void testFlickerWithBrago() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph"); // {3}{UP}

        // Flying
        // When Brago, King Eternal deals combat damage to a player, exile any number of target nonland permanents you control, then return those cards to the battlefield under their owner's control.        
        addCard(Zone.BATTLEFIELD, playerA, "Brago, King Eternal"); // 2/4
        
        // Creatures you control have haste.
        // Cascade, cascade
        addCard(Zone.BATTLEFIELD, playerB, "Maelstrom Wanderer");  // 7/5
        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Ponyback Brigade");  // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Maelstrom Wanderer");

        attack(3, playerA, "Brago, King Eternal");
        addTarget(playerA, "Maelstrom Wanderer");        
        setChoice(playerA, "Ponyback Brigade");
        
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
               
        assertPermanentCount(playerA, "Ponyback Brigade", 1);
        assertPermanentCount(playerA, "Goblin", 3);

    }  

    /**
     * I had a Harmonic Sliver, my opponent played Phyrexian Metamorph copying
     * it. The resulting copy only had one instance of the artifact-enchantment
     * destroying ability, where it should have had two of them and triggered
     * twice (the Metamorph might have nothing to do with this)
     */
   @Test
    public void testHarmonicSliver() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph"); // {3}{UP}

        addCard(Zone.BATTLEFIELD, playerB, "Alloy Myr", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail", 1);
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.BATTLEFIELD, playerB, "Harmonic Sliver"); // 2/4
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Harmonic Sliver");
        addTarget(playerA, "Alloy Myr");
        addTarget(playerA, "Kitesail");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
               
        assertPermanentCount(playerA, "Harmonic Sliver", 1);

        assertGraveyardCount(playerB, "Alloy Myr", 1);
        assertGraveyardCount(playerB, "Kitesail", 1);

    }  
    
    /**
     * If a Harmonic Sliver enters the battlefield 
     * the controller has to destroy one artifacts or enchantments
     */
    @Test
    public void testHarmonicSliverNative1() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.HAND, playerA, "Harmonic Sliver"); 

        addCard(Zone.BATTLEFIELD, playerB, "Alloy Myr", 2); // 2/2        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harmonic Sliver");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
               
        assertPermanentCount(playerA, "Harmonic Sliver", 1);

        assertGraveyardCount(playerB, "Alloy Myr", 1);

    }      
    
    /**
     * If a Harmonic Sliver enters the battlefield and there is already one on the battlefield
     * the controller has to destroy two artifacts or enchantments
     */
    @Test
    public void testHarmonicSliverNative2() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.HAND, playerA, "Harmonic Sliver"); 

        addCard(Zone.BATTLEFIELD, playerB, "Alloy Myr", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail", 1);
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.BATTLEFIELD, playerB, "Harmonic Sliver"); // 2/4
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harmonic Sliver");
        addTarget(playerA, "Alloy Myr");
        addTarget(playerA, "Kitesail");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
               
        assertPermanentCount(playerA, "Harmonic Sliver", 1);

        assertGraveyardCount(playerB, "Alloy Myr", 1);
        assertGraveyardCount(playerB, "Kitesail", 1);

    }       
}
