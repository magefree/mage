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
public class EnterLeaveBattlefieldExileTargetTest extends CardTestPlayerBase {

    @Test
    public void testAngelOfSerenityExile() {
        // Flying
        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        addCard(Zone.HAND, playerA, "Angel of Serenity");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Serenity");
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Serenity", 1);
        assertExileCount("Silvercoat Lion", 1);
        assertExileCount("Pillarfield Ox", 1);

    }

    /**
     * When Angel of Serenity entered the battlefield on my opponent's main
     * phase it exiled 3 of my creatures (as it should), I cast Ultimate Price
     * (destroy monocolored creature) on my next main phase and destroyed the
     * Angel of Serenity. The log said the exiled cards were returned to my hand
     * but they remained in exile indefinitely.
     */
    @Test
    public void testAngelOfSerenityExileReturn() {
        // Flying
        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        addCard(Zone.HAND, playerA, "Angel of Serenity");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.HAND, playerB, "Ultimate Price", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Serenity");
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ultimate Price", "Angel of Serenity");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Ultimate Price", 1);
        assertGraveyardCount(playerA, "Angel of Serenity", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Pillarfield Ox", 1);
        assertExileCount(playerB, 0);

    }
}
