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
 * @author LevelX2
 */

public class RetraceTest extends CardTestPlayerBase {

    /**
     * 702.78. Retrace
     * 702.78a Retrace appears on some instants and sorceries. It represents a static ability 
     *  that functions while the card is in a player's graveyard. "Retrace" means "You may cast
     *  this card from your graveyard by discarding a land card as an additional cost to cast it."
     *  Casting a spell using its retrace ability follows the rules for paying additional costs
     *  in rules 601.2b and 601.2e-g.
     */
    

    @Test
    public void SimpleRetrace() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Raven's Crime");
        addCard(Zone.HAND, playerA, "Swamp");

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerB);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA,"Raven's Crime", 1);
        assertGraveyardCount(playerA,"Swamp", 1);
        
        assertGraveyardCount(playerB,"Silvercoat Lion", 1);
    }
    
    /**
     * Test that it does cost {B}{1} + land discard
     */
    @Test
    public void RetraceCostIncreaseCantPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Raven's Crime");
        addCard(Zone.HAND, playerA, "Swamp");

        // // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerB);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA,"Raven's Crime", 1);
        assertGraveyardCount(playerA,"Swamp", 0); // because not enough mana
        
        assertGraveyardCount(playerB,"Silvercoat Lion", 0); // because not enough mana
    }

    /**
     * Test that it does cost {B}{1} + land discard
     */
    @Test
    public void RetraceCostIncreaseCanPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Raven's Crime");
        addCard(Zone.HAND, playerA, "Swamp");

        // // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerB);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA,"Raven's Crime", 1);
        assertGraveyardCount(playerA,"Swamp", 1); 
        
        assertGraveyardCount(playerB,"Silvercoat Lion", 1); 
    }
            
}
