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
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EndTurnEffectTest extends CardTestPlayerBase {

    /**
     * Additional bug: Days Undoing and Sphinx's Tutelage are broken. You
     * shouldn't get triggers off of Tutelage, since the turn ends, but it has
     * you resolve them in your cleanup step.
     *
     * http://tabakrules.tumblr.com/post/122350751009/days-undoing-has-been-officially-spoiled-on
     *
     */
    @Test
    public void testSpellsAffinity() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Whenever you draw a card, target opponent puts the top two cards of his or her library into his or her graveyard. If they're both nonland cards that share a color, repeat this process.
        // {5}{U}: Draw a card, then discard a card.
        addCard(Zone.BATTLEFIELD, playerA, "Sphinx's Tutelage");

        // Each player shuffles his or her hand and graveyard into his or her library, then draws seven cards. If it's your turn, end the turn.
        addCard(Zone.HAND, playerA, "Day's Undoing"); //Sorcery {2}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day's Undoing");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertExileCount("Day's Undoing", 1);

        assertHandCount(playerA, 7);
        assertHandCount(playerB, 7);

        assertGraveyardCount(playerB, 0); // because the trigegrs of Sphinx's Tutelage cease to exist

    }

}
