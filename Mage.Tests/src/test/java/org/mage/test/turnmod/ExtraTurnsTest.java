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
package org.mage.test.turnmod;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ExtraTurnsTest extends CardTestPlayerBase {

    /**
     * Emrakul, the Promised End not giving an extra turn when cast in the
     * opponent's turn
     */
    @Test
    public void testEmrakulCastOnOpponentsTurnCheckTurn3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        // Flying
        // Trample
        // Protection from instants
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time he or she could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        Assert.assertTrue("Turn 3 is no extra turn ", !currentGame.getState().isExtraTurn());
        Assert.assertTrue("For turn " + currentGame.getTurnNum() + ", playerA has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId().equals(playerA.getId()));
    }

    @Test
    public void testEmrakulCastOnOpponentsTurnCheckTurn4() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        // Flying
        // Trample
        // Protection from instants
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time he or she could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        setStopAt(4, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        Assert.assertTrue("Turn 4 is a controlled turn ", !playerB.isGameUnderControl());
        Assert.assertTrue("For turn " + currentGame.getTurnNum() + ", playerB has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId().equals(playerB.getId()));
    }

    @Test
    public void testEmrakulCastOnOpponentsTurnCheckTurn5() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        // Flying
        // Trample
        // Protection from instants
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time he or she could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        setStopOnTurn(5);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        Assert.assertTrue("Turn 5 is an extra turn ", currentGame.getState().isExtraTurn());
        Assert.assertTrue("For turn " + currentGame.getTurnNum() + ", playerB has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId().equals(playerB.getId()));
    }
}
