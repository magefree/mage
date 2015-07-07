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
public class HallowedMoonlightTest extends CardTestPlayerBase {

    @Test
    public void testGrindstoneProgenius() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Hallowed Moonlight");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerB, "Pillarfield Ox");
        // Put a 1/1 colorless Spirit creature token onto the battlefield.
        // Splice onto Arcane {W}
        addCard(Zone.HAND, playerB, "Spiritual Visit");
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerB, "Reanimate");

        castSpell(2, PhaseStep.DRAW, playerA, "Hallowed Moonlight");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Spiritual Visit");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Reanimate", "Pillarfield Ox");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Hallowed Moonlight", 1);
        assertGraveyardCount(playerB, "Spiritual Visit", 1);

        assertPermanentCount(playerB, "Spirit", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertExileCount(playerB, 1);
        assertExileCount("Pillarfield Ox", 1);

    }

}
