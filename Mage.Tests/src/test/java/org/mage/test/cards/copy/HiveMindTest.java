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
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HiveMindTest extends CardTestPlayerBase {

    /**
     * Check that opponent gets a copy of Lightning Bolt 
     */
    @Test
    public void testTransform() {        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a player casts an instant or sorcery spell, each other player copies that spell. 
        // Each of those players may choose new targets for his or her copy.
        addCard(Zone.BATTLEFIELD, playerA, "Hive Mind", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        setChoice(playerB, "Yes");
        addTarget(playerB, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertLife(playerA, 17);
    }
    
    /**
     * The Amulet Bloom deck in Modern has a few wincons, one of them being that the Bloom player
     * resolves a Hive Mind, then casts a Pact. The Hive Mind copies it so the opponent also casts a Pact.
     * If the opposing player has a Chalice set on ZERO, it will counter both copies which it should
     * NOT DO because Hive Mind puts a copy onto the stack, not 'cast'. So while the Bloom player's
     * copy is countered, the opponent will still cast and have to pay during their upkeep. 
     */
    @Test
    public void testChaliceOfTtheVoid() {        
        // Whenever a player casts an instant or sorcery spell, each other player copies that spell. 
        // Each of those players may choose new targets for his or her copy.        
        addCard(Zone.BATTLEFIELD, playerA, "Hive Mind", 1);
        // Put a 4/4 red Giant creature token onto the battlefield.
        // At the beginning of your next upkeep, pay {4}{R}. If you don't, you lose the game.
        addCard(Zone.HAND, playerA, "Pact of the Titan", 1);
        // Chalice of the Void enters the battlefield with X charge counters on it.
        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        addCard(Zone.BATTLEFIELD, playerB, "Chalice of the Void", 1);
        
        setChoice(playerB, "Yes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pact of the Titan");
        
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Pact of the Titan", 1);
        assertPermanentCount(playerA, "Giant", 0); // was countered by Chalice
        assertPermanentCount(playerB, "Giant", 1); // was not countered by Chalice because it was not cast
        Assert.assertTrue("Player A must have won", playerA.hasWon());
        Assert.assertTrue("Player B must have lost", playerB.hasLost());
        assertLife(playerB, 20);
        assertLife(playerA, 20);
    }    
}