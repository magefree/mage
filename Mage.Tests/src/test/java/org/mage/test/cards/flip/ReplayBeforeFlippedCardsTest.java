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
package org.mage.test.cards.flip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReplayBeforeFlippedCardsTest extends CardTestPlayerBase {

    @Test
    public void testHanweirMilitiaCaptain() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        // transformed side:
        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        // At the beginning of your end step, put a 1/1 white and black Human Cleric creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Hanweir Militia Captain", 1); // {1}{W} Creature
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hanweir Militia Captain");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hanweir Militia Captain", 0);
        assertPermanentCount(playerA, "Westvale Cult Leader", 1);
        assertPowerToughness(playerA, "Westvale Cult Leader", 5, 5);

    }

    /**
     * Return a flipped Hanweir Militia Captain to its owners hand and when
     * replayed it still has * / * power and toughness.
     */
    @Test
    public void testHanweirMilitiaCaptainReturned() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        // transformed side:
        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        // At the beginning of your end step, put a 1/1 white and black Human Cleric creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Hanweir Militia Captain", 1); // {1}{W} Creature
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Return target creature to its owner's hand.
        // Madness
        addCard(Zone.HAND, playerB, "Just the Wind", 1); // Instant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hanweir Militia Captain");

        castSpell(3, PhaseStep.DRAW, playerB, "Just the Wind", "Westvale Cult Leader");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Hanweir Militia Captain");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Just the Wind", 1);
        assertPermanentCount(playerA, "Hanweir Militia Captain", 1);
        assertPowerToughness(playerA, "Hanweir Militia Captain", 2, 2);

    }
}
