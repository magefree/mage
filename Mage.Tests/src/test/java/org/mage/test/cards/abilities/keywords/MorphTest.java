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
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author levelX2
 */

public class MorphTest extends CardTestPlayerBase {

    /**
     * Tests if a creature with Morph is cast normal, it behaves as normal creature
     *
     */
    @Test
    public void testCastMoprhCreatureWithoutMorph() {
        /*    
        Pine Walker
        Creature - Elemental
        5/5
        Morph {4}{G} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        Whenever Pine Walker or another creature you control is turned face up, untap that creature.
        */
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "No"); // cast it normal as 5/5
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 5, 5);

    }


    /**
     * Cast the creature face down as a 2/2
     */
    @Test
    public void testCastFaceDown() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);

    }
    /**
     * Test triggered turn face up ability of Pine Walker
     */
    @Test
    public void testCopyFaceDwonMorphCreature() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        attack(3, playerA, "");
        
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
        
        assertPermanentCount(playerA, "", 0);
        assertPermanentCount(playerA, "Pine Walker", 1);        
        assertPowerToughness(playerA, "Pine Walker", 5, 5);
        assertTapped("Pine Walker", false);

    }
    /**
     * Test that Morph creature do not trigger abilities with their face up attributes
     * 
     */
    @Test
    public void testMorphedRemovesAttributesCreature() {
        // Ponyback Brigade {3}{R}{W}{B}
        // Creature - Goblin Warrior
        // 2/2
        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        // Morph {2}{R}{W}{B}(You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)        
        addCard(Zone.HAND, playerA, "Ponyback Brigade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Soldier of the Pantheon", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ponyback Brigade");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20); // and not 21 
        
        assertPermanentCount(playerA, "", 1);
        assertPermanentCount(playerB, "Soldier of the Pantheon", 1);

    }
    
   /**
     * Test to copy a morphed 2/2 creature
     * 
     */
    @Test
    public void testCopyAMorphedCreature() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        
        // Clever Impersonator  {2}{U}{U}
        // Creature - Shapeshifter
        // 0/0
        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");
        setChoice(playerB, "Yes"); // use to copy a nonland permanent
        addTarget(playerB, ""); // Morphed creature
                
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2,2);
        assertPermanentCount(playerB, "", 1);
        assertPowerToughness(playerB, "", 2,2);

    }    
    
}
