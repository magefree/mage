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
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DrawEffectsTest extends CardTestPlayerBase {

    /**
     * The effects of multiple Thought Reflections are cumulative. For example,
     * if you have three Thought Reflections on the battlefield, you'll draw
     * eight times the original number of cards.
     */
    @Test
    public void testCard() {
        // If you would draw a card, draw two cards instead.
        addCard(Zone.BATTLEFIELD, playerB, "Thought Reflection", 3);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("Player B has to have 4 cards in hand", 8, playerB.getHand().size());

    }

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=17295&start=75#p181427
     * If I have a Notion Thief on the battlefield and cast Opportunity,
     * targeting my opponent, during my opponent's upkeep, the opponent
     * incorrectly draws the cards.
     */
    @Test
    public void testNotionThief() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Flash
        // If an opponent would draw a card except the first one he or she draws in each of his or her draw steps, instead that player skips that draw and you draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Notion Thief", 1);

        // Target player draws four cards.
        addCard(Zone.HAND, playerA, "Opportunity", 1);

        castSpell(2, PhaseStep.UPKEEP, playerA, "Opportunity", playerB);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Opportunity", 1);
        assertHandCount(playerA, 4);
        assertHandCount(playerB, 1);
    }

}
