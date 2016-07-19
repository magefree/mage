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
 *
 * @author LevelX2
 */
public class LeylineOfTheVoidTest extends CardTestPlayerBase {

    /**
     * Leyline of the Void is on the battlefield, Helm of Obedience is used on
     * an opponent with X=1. Now the Helm states to mill "until a creature card
     * or X cards are put into the graveyard this way". For each of those milled
     * cards, Leyline states to "remove [them] from the game instead". In other
     * words: Leyline + Helm and X of at least 1 exiles the library of one
     * unlucky opponent. Or should, because right now it doesn't. Right now it's
     * Helm's originally intended "X or 1st Creature" amount of cards that after
     * it's effect get exiled.
     */
    @Test
    public void testGrindstoneProgenius() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");

        // {X}, {T}: Target opponent puts cards from the top of his or her library into his or her graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Helm of Obedience");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X},{T}: Target opponent puts cards", playerB);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 71); // All cards go to exile replaced from Leyline of the void
    }

    /**
     * Today i casted Ill-gotten Gains in EDH (with a leyline of the veil in
     * play) and the spell simply discarded both players hands not letting
     * either of us choose cards to get back, this ended up with me losing the
     * game as i was going to combo off some cards in yard.
     */
    @Test
    public void testIllgottenGains() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");

        // Exile Ill-Gotten Gains.
        // Each player discards his or her hand,
        // then returns up to three cards from his or her graveyard to his or her hand.
        addCard(Zone.HAND, playerA, "Ill-Gotten Gains"); // Sorcery - {2}{B}{B}
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 4);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ill-Gotten Gains");
        setChoice(playerA, "Silvercoat Lion^Silvercoat Lion^Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 4);
        assertHandCount(playerB, 0);

        assertExileCount(playerA, 1);
        assertHandCount(playerA, 3);
    }
    
    @Test
    public void testMorbidAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.BATTLEFIELD, playerB, "Deathreap Ritual");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        
        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Murder");
        setChoice(playerA, "Memnite");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        
        assertHandCount(playerB, 0);
        assertExileCount(playerB, 1);
        
    }

}
