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
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CastAsInstantTest extends CardTestPlayerBase {

    @Test
    public void testEffectOnlyForOneTurn() {
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        // The next sorcery card you cast this turn can be cast as though it had flash.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Quicken"); // {U}
        // Devoid (This card has no color.)
        // Target opponent exiles two cards from his or her hand and loses 2 life.
        addCard(Zone.HAND, playerB, "Witness the End"); // {3}{B}

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Quicken");
        castSpell(1, PhaseStep.UPKEEP, playerB, "Witness the End", playerA);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Quicken", 1);
        assertGraveyardCount(playerB, "Witness the End", 1);

        assertExileCount("Silvercoat Lion", 2);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }

}
