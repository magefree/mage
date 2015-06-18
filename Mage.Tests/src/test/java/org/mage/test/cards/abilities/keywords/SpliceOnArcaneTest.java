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

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SpliceOnArcaneTest extends CardTestPlayerBase {

    /**
     * Test that it works to cast Through the Breach
     * by slicing it on an arcane spell
     *
     */
    @Test
    public void testSpliceThroughTheBreach() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Sorcery - Arcane  {R}
        // Lava Spike deals 3 damage to target player.
        addCard(Zone.HAND, playerA, "Lava Spike",1);
        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        // Splice onto Arcane {2}{R}{R} (As you cast an Arcane spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)
        addCard(Zone.HAND, playerA, "Through the Breach",1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion",1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lava Spike", playerB);
        setChoice(playerA, "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        
        assertGraveyardCount(playerA, "Lava Spike", 1);
        assertHandCount(playerA, "Through the Breach", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); 
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
        Assert.assertEquals("All available mana has to be used", 0, playerA.getManaAvailable(currentGame).size());
    }
 
    @Test
    public void testSpliceTorrentOfStone() {        
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Sorcery - Arcane  {R}
        // Lava Spike deals 3 damage to target player.
        addCard(Zone.HAND, playerA, "Lava Spike",1);
        // Torrent of Stone deals 4 damage to target creature.
        // Splice onto Arcane-Sacrifice two Mountains. (As you cast an Arcane spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)        
        addCard(Zone.HAND, playerA, "Torrent of Stone",1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion",1);        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lava Spike", playerB);
        addTarget(playerA, "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        
        assertGraveyardCount(playerA, "Lava Spike", 1);
        assertHandCount(playerA, "Torrent of Stone", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Mountain", 0); 
        Assert.assertEquals("No more mana available", "[]", playerA.getManaAvailable(currentGame).toString());
    }      
    /**
     * Nourishing Shoal's interaction with Splicing Through the Breach is
     * bugged. You should still need to pay 2RR as an additional cost, which is
     * not affected by the alternate casting method of Shoal, but you are able
     * to Splice it for free. This is a very relevant bug right now due to the
     * appearance of the deck over the weekend, and it makes the deck absurdly
     * powerful.
     */
    @Test
    public void testSpliceThroughTheBreach2() {        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // You may exile a green card with converted mana cost X from your hand rather than pay Nourishing Shoal's mana cost.
        // You gain X life.
        addCard(Zone.HAND, playerA, "Nourishing Shoal",1);
        addCard(Zone.HAND, playerA, "Giant Growth",1);
        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        // Splice onto Arcane {2}{R}{R} (As you cast an Arcane spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)
        addCard(Zone.HAND, playerA, "Through the Breach",1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion",1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nourishing Shoal");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerA, "Nourishing Shoal", 1);
        assertHandCount(playerA, "Through the Breach", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); 
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
        
        Assert.assertEquals("All available mana has to be used","[]", playerA.getManaAvailable(currentGame).toString());
    }    
    
   
}
