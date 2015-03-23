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
 * @author BetaSteward
 */
public class MadnessTest extends CardTestPlayerBase {

    /**
     * 702.34. Madness
     * 702.34a Madness is a keyword that represents two abilities. The first is a static ability that functions
     * while the card with madness is in a player’s hand. The second is a triggered ability that
     * functions when the first ability is applied. “Madness [cost]” means “If a player would discard
     * this card, that player discards it, but may exile it instead of putting it into his or her graveyard”
     * and “When this card is exiled this way, its owner may cast it by paying [cost] rather than paying
     * its mana cost. If that player doesn’t, he or she puts this card into his or her graveyard.”
     * 702.34b Casting a spell using its madness ability follows the rules for paying alternative costs in
     * rules 601.2b and 601.2e–g.
     * 
     */

    /** 
     * 	Arrogant Wurm
     * 	3GG
     * 	Creature -- Wurm
     * 	4/4
     * 	Trample
     * 	Madness {2}{G} (If you discard this card, you may cast it for its 
     *  madness cost instead of putting it into your graveyard.)
     *
     */
    
    /**
     * Raven's Crime
     * B
     * Sorcery
     * Target player discards a card.
     * Retrace (You may cast this card from your graveyard by discarding a land 
     * card in addition to paying its other costs.)
     * 
     */

    @Test
    public void testMadness() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Arrogant Wurm");
        addCard(Zone.HAND, playerA, "Raven's Crime");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Arrogant Wurm", 1);
        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertHandCount(playerA, 0);

    }
    
    @Test
    public void testNoMadness() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Arrogant Wurm");
        addCard(Zone.HAND, playerA, "Raven's Crime");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Arrogant Wurm", 0);
        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertGraveyardCount(playerA, "Arrogant Wurm", 1);
        assertHandCount(playerA, 0);

    }

}
