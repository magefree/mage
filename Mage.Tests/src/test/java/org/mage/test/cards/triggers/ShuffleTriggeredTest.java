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
public class ShuffleTriggeredTest extends CardTestPlayerBase {

    @Test
    public void testWidespreadPanicDoesTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a spell or ability causes its controller to shuffle his or her library, that player puts a card from his or her hand on top of his or her library.
        addCard(Zone.HAND, playerA, "Widespread Panic", 1); // Enchantment {2}{R}
        // Search your library for a basic land card and put that card onto the battlefield. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Untamed Wilds"); // Sorcery - {2}{G}

        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Widespread Panic");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Untamed Wilds");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Widespread Panic", 1);
        assertGraveyardCount(playerA, "Untamed Wilds", 1);

        assertHandCount(playerA, "Silvercoat Lion", 0); // Because Untamed Wilds does trigger Widespread Panic the card goes to library
    }

    /**
     * Wenn ich mit Knowledge Exploitation einen Gegner seine Bibliothek mischen
     * lasse, dann triggert Widespread Panic für ihn (sollte garnicht triggern).
     * Bei Bribery ist es genauso.
     * 
     * If I have an opponent shuffle his library using Knowledge Exploitation, Widespread Panic triggers for him (shoudn't trigger at all). Same thing with Bribery.
     */
    @Test
    public void testWidespreadPanicDoesNotTriggerIfOpponentShufflesPlayersLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a spell or ability causes its controller to shuffle his or her library, that player puts a card from his or her hand on top of his or her library.
        addCard(Zone.HAND, playerA, "Widespread Panic", 1); // Enchantment {2}{R}
        // Prowl {3}{U}
        // Search target opponent's library for an instant or sorcery card. You may cast that card without paying its mana cost. Then that player shuffles his or her library.
        addCard(Zone.HAND, playerA, "Knowledge Exploitation"); // Sorcery - {5}{U}{U}

        addCard(Zone.HAND, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Widespread Panic");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Knowledge Exploitation", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Widespread Panic", 1);
        assertGraveyardCount(playerA, "Knowledge Exploitation", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1); // Because Knowledge Exploitation does not trigger the card stays in hand
    }

}
