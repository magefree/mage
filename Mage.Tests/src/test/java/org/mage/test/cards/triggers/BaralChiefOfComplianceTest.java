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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BaralChiefOfComplianceTest extends CardTestPlayerBase {

    /**
     * In the following scenario baral's loot ability isn't triggered.
     *
     * Baral, Chief of Compliance in my graveyard, opponent had a creature in
     * the stack.
     *
     * I cast Ojutai's Command with modes: Return creature from graveyard to
     * battlefield (targeting Baral), and counter their creature spell. Ojutai's
     * command resolves, do the modes in the order they appear in the card. I
     * put Baral onto the battlefield, and he is around to witness the creature
     * be counted. Baral's loot ability isn't triggered.
     *
     */
    @Test
    public void testBaralTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Instant and sorcery spells you cast cost {1} less to cast.
        // Whenever a spell or ability you control counters a spell, you may draw a card. If you do, discard a card.
        addCard(Zone.GRAVEYARD, playerB, "Baral, Chief of Compliance"); // Creature {1}{U}
        // Choose two -
        // Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield;
        // or You gain 4 life;
        // or Counter target creature spell;
        // or Draw a card
        addCard(Zone.HAND, playerB, "Ojutai's Command"); // Instant {2}{W}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ojutai's Command", "mode=1Baral, Chief of Compliance^mode=3Silvercoat Lion");
        setModeChoice(playerB, "1");
        setModeChoice(playerB, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Ojutai's Command", 1);
        assertGraveyardCount(playerB, 2);
        assertPermanentCount(playerB, "Baral, Chief of Compliance", 1);
    }

}
