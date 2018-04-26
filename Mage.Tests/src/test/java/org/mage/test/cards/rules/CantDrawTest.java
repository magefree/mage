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
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantDrawTest extends CardTestPlayerBase {

    /**
     * Leovold, Emissary of Trest does not stop Sylvan Library from drawing
     * extra cards.
     */
    @Test
    public void testCardsNotDrawnFromLibraryEffect() {
        // Each opponent can't draw more than one card each turn.
        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Leovold, Emissary of Trest");

        // At the beginning of your draw step, you may draw two additional cards.
        // If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Library", 1);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);

        assertHandCount(playerA, 1); // only one card drawn at start of turn 3
        assertLife(playerA, 16); // pay 4 life for the card dawn normally (no other cards drawn)

    }

    @Test
    public void testCardsNotDrawnFromLibraryEffectNoUse() {
        // Each opponent can't draw more than one card each turn.
        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Leovold, Emissary of Trest");

        // At the beginning of your draw step, you may draw two additional cards.
        // If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Library", 1);

        setChoice(playerA, "No");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);

        assertHandCount(playerA, 1); // only one card drawn at start of turn 3
        assertLife(playerA, 20); // not used no damage

    }

}
