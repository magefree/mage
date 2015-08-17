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
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UginTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        // +2: Ugin, the Spirit Dragon deals 3 damage to target creature or player.
        // -X: Exile each permanent with converted mana cost X or less that's one or more colors.
        // -10: You gain 7 life, draw 7 cards, then put up to seven permanent cards from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Ugin, the Spirit Dragon"); // starts with 7 Loyality counters
        // Whenever a creature dies, you may put a quest counter on Quest for the Gravelord.
        addCard(Zone.BATTLEFIELD, playerA, "Quest for the Gravelord");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.LIBRARY, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        // -2: Put a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World onto the battlefield.
        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        addCard(Zone.HAND, playerB, "Nissa, Vastwood Seer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {source} deals 3 damage to target creature or player.", playerB);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Nissa, Vastwood Seer");
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Forest");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "-2: Put a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World onto the battlefield.");

        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Ashaya, the Awoken World", "Silvercoat Lion");

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "-X: Exile each permanent with converted mana cost X or less that's one or more colors");
        setChoice(playerA, "X=0");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ugin, the Spirit Dragon", 1);
        assertCounterCount("Ugin, the Spirit Dragon", CounterType.LOYALTY, 9);  // 7 + 2 - 0

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Ashaya, the Awoken World", 0);

        assertExileCount("Nissa, Vastwood Seer", 1);

        assertCounterCount("Quest for the Gravelord", CounterType.QUEST, 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

}
