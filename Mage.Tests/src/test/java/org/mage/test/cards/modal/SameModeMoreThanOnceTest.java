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
package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SameModeMoreThanOnceTest extends CardTestPlayerBase {

    @Test
    public void testEachModeOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Choose three. You may choose the same mode more than once.
        // - Target player draws a card and loses 1 life;
        // - Target creature gets -2/-2 until end of turn;
        // - Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Wretched Confluence"); // Instant {3}{B}{B}

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wretched Confluence", "mode=1targetPlayer=PlayerA^mode=2Pillarfield Ox^mode=3Silvercoat Lion");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wretched Confluence", 1);
        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertHandCount(playerA, 2);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);

    }

    @Test
    public void testSecondModeTwiceThridModeOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Choose three. You may choose the same mode more than once.
        // - Target player draws a card and loses 1 life;
        // - Target creature gets -2/-2 until end of turn;
        // - Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Wretched Confluence"); // Instant {3}{B}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Wall of Air");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wretched Confluence", "mode=1Pillarfield Ox^mode=2Wall of Air^mode=3Silvercoat Lion");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wretched Confluence", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPowerToughness(playerB, "Wall of Air", -1, 3);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);

    }
}
