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
package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TradingPostTest extends CardTestPlayerBase {

    /**
     * Trading Post doesn't let me sacrifice a creature owned by my opponent,
     * but controlled by me. I get an error message saying You cannot sacrifice
     * this creature.
     */
    @Test
    public void testSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // {1}, {T}, Discard a card: You gain 4 life.
        // {1}, {T}, Pay 1 life: Create a 0/1 white Goat creature token onto the battlefield.
        // {1}, {T}, Sacrifice a creature: Return target artifact card from your graveyard to your hand.
        // {1}, {T}, Sacrifice an artifact: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Trading Post", 1);
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. (It can attack and this turn.)
        addCard(Zone.HAND, playerA, "Act of Treason"); // Sorcery {2}{R}
        addCard(Zone.GRAVEYARD, playerA, "Helm of Possession");

        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Savannah Lions");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},{T}, Sacrifice a creature", "Helm of Possession", "Act of Treason", StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, "Savannah Lions");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Act of Treason", 1);

        assertPermanentCount(playerB, "Savannah Lions", 0);
        assertGraveyardCount(playerB, "Savannah Lions", 1);

        assertTapped("Trading Post", true);
        assertHandCount(playerA, 1);

    }
}
