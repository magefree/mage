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
public class DrawTriggeredTest extends CardTestPlayerBase {

    /*
     * Day's Undoing - Doesn't create card draw triggers "Specifically, it
     * doesn't work with Chasm Skulker.
     *
     * Steps to reproduce:
     1) Have Chasm Skulker on the battlefield.
     2) Cast Day's Undoing.
     3) You will draw 7 cards, but Chasm Skulker's ""when you draw a card"" trigger does not trigger." ==> What is correct
     */
    @Test
    public void DaysUndoingTriggeredDrewEventAreRemovedTest() {
        // Each player shuffles his or her hand and graveyard into his or her library, then draws seven cards. If it's your turn, end the turn.
        addCard(Zone.HAND, playerA, "Day's Undoing");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.
        // When Chasm Skulker dies, put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker.
        addCard(Zone.BATTLEFIELD, playerB, "Chasm Skulker", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day's Undoing");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertExileCount("Day's Undoing", 1);
        assertPermanentCount(playerB, "Chasm Skulker", 1);
        assertPowerToughness(playerB, "Chasm Skulker", 1, 1);

    }

    /**
     * Consecrated Sphinx does not trigger when "Edric, Spymaster of Trest" lets
     * my opponent draw cards.
     */
    @Test
    public void EdricSpymasterOfTrestTest() {
        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Consecrated Sphinx", 1);

        // Whenever a creature deals combat damage to one of your opponents, its controller may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Edric, Spymaster of Trest", 1);

        attack(2, playerB, "Edric, Spymaster of Trest");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 2); // 1 from start of turn and 1 from Edric
        assertHandCount(playerA, 4); // 2 * 2 from Sphinx = 4

    }

    /**
     * two consecrated sphinxes do not work properly, only gives one player
     * additional draw
     *
     */
    @Test
    public void TwoConsecratedSphinxDifferentPlayers() {
        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Consecrated Sphinx", 1);

        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerB, "Consecrated Sphinx", 1);

        setChoice(playerA, "Yes");
        setChoice(playerA, "No");
        setChoice(playerA, "No");

        setChoice(playerB, "Yes");
        setChoice(playerB, "No");
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 3); // 1 from start of turn 1 and 4 from Opponents draw of 2 cards
        assertHandCount(playerA, 2); // 2 from Sphinx triggered by the normal draw

    }

    @Test
    public void TwoConsecratedSphinxSamePlayer() {
        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Consecrated Sphinx", 2);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 1); // 1 from start of turn 1 and 4 from Opponents draw of 2 cards
        assertHandCount(playerA, 4); // 2 from Sphinx triggered by the normal draw

    }
}
