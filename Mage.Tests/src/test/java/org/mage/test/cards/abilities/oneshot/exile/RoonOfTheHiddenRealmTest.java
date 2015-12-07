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
package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RoonOfTheHiddenRealmTest extends CardTestPlayerBase {

    /**
     * Roon of the Hidden Realm is returning cards to their controler's control
     * instead of the owner's control at the end of the turn. I used his ability
     * on a Perplexing Chimera I gave my opponent and in the end of the turn it
     * returned to the battlefield in his control.
     */
    @Test
    public void testReturnToBattlefieldForOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Vigilance, Trample
        // {2}, {T}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Roon of the Hidden Realm");

        // Whenever an opponent casts a spell, you may exchange control of Perplexing Chimera and that spell. If you do, you may choose new targets for the spell.
        addCard(Zone.BATTLEFIELD, playerA, "Perplexing Chimera");

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        setChoice(playerA, "Yes");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}", "Perplexing Chimera");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTapped("Roon of the Hidden Realm", true);
        assertPermanentCount(playerA, "Roon of the Hidden Realm", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Perplexing Chimera", 0);
        assertPermanentCount(playerA, "Perplexing Chimera", 1);

    }
}
