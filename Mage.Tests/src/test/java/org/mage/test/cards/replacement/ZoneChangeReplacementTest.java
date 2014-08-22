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

package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Checks if change zone replacement effects work as intended
 * 
 * @author LevelX2
 */

public class ZoneChangeReplacementTest extends CardTestPlayerBase {

    // If Darksteel Colossus would be put into a graveyard from anywhere, 
    // reveal Darksteel Colossus and shuffle it into its owner's library instead.        
    @Test
    public void testFromLibraryZoneChange() {
        addCard(Zone.LIBRARY, playerA, "Darksteel Colossus");
        // Tome Scour - Sorcery - {U}
        // Target player puts the top five cards of his or her library into his or her graveyard.
        addCard(Zone.HAND, playerA, "Tome Scour");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        skipInitShuffling();
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tome Scour", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Darksteel Colossus", 0);
        assertGraveyardCount(playerA, 5); // 4 + Tome Scour
        
    }
    
    @Test
    public void testFromHandZoneChange() {
        addCard(Zone.HAND, playerA, "Progenitus");
        // Distress - Sorcery - {B}{B}
        // Target player reveals his or her hand. You choose a nonland card from it. That player discards that card.
        addCard(Zone.HAND, playerA, "Distress");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Distress", playerA);
        setChoice(playerA, "Progenitus");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Progenitus", 0);
        assertGraveyardCount(playerA, 1); // Distress
        
        assertHandCount(playerA, "Progenitus", 0);
    }

    @Test
    public void checkBridgeDoesWork() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Diabolic Edict - Instant - {1}{B}
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");
        // Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from 
        // Below is in your graveyard, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Bridge from Below");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Diabolic Edict", playerA);
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, 3); // Diabolic Edict + Bridge from Below + Silvercoat Lion
        assertPermanentCount(playerA, "Zombie", 1); // Silvercoat Lion goes to graveyard so a Zombie tokes is created
        
    }

    @Test
    public void testDoesntTriggerDiesTriggeredAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Progenitus");
        // Diabolic Edict - Instant - {1}{B}
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");
        // Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from 
        // Below is in your graveyard, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Bridge from Below");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Diabolic Edict", playerA);
        setChoice(playerA, "Progenitus");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Progenitus", 0);
        assertGraveyardCount(playerA, 2); // Diabolic Edict + Bridge from Below
        assertPermanentCount(playerA, "Zombie", 0); // Progenitus never touches graveyard - so no Zombie tokes is created
        
    }
    
    // Have Progenitus and Humility on the battlefield. Destroy Progenitus. Progenitus should go to the graveyard
    // since it doesn't have any replacement effect. Currently, it gets shuffled into the library.


    @Test
    public void testHumilityDeactivatesReplacementEffectAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Progenitus");
        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");
        // Diabolic Edict - Instant - {1}{B}
        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Diabolic Edict");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
                
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,  "Diabolic Edict", playerA);
        setChoice(playerA, "Progenitus");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Progenitus", 0);
        assertGraveyardCount(playerA, "Progenitus", 1);
        assertGraveyardCount(playerA, 2); // Diabolic Edict + Progenitus
        
        
    }
    
    @Test
    public void testHumilityAndKumano() {
        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");

        // 3/3
        // If a creature dealt damage by Kumano's Pupils this turn would die, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kumano's Pupils");

        // Instant {1}{G}
        // Target creature gets +1/+1 until end of turn.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Aggressive Urge");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Aggressive Urge", "Kumano's Pupils");
        
        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Kumano's Pupils", "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Kumano's Pupils", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        
        assertExileCount("Silvercoat Lion", 0);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);        
        
    }  
   
}

