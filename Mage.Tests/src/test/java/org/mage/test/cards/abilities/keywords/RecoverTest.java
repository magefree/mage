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
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RecoverTest extends CardTestPlayerBase {

    /**
     * 702.58a Recover is a triggered ability that functions only while the card
     * with recover is in a player’s graveyard. “Recover [cost]” means “When a
     * creature is put into your graveyard from the battlefield, you may pay
     * [cost]. If you do, return this card from your graveyard to your hand.
     * Otherwise, exile this card.”
     */
    @Test
    public void testReturnToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // You gain 4 life.
        // Recover {1}{W}
        addCard(Zone.HAND, playerA, "Sun's Bounty");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sun's Bounty");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerA, "Sun's Bounty", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 24);

        assertTappedCount("Plains", true, 4);

    }

    @Test
    public void testGoingToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // You gain 4 life.
        // Recover {1}{W}
        addCard(Zone.HAND, playerA, "Sun's Bounty");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sun's Bounty");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Plains", true, 2);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount("Sun's Bounty", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 24);

    }
}
