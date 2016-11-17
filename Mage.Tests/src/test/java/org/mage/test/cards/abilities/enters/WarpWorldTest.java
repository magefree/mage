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
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WarpWorldTest extends CardTestPlayerBase {

    /**
     * Hello. I was playing a 4 player EDH game, and after quite a ways into it
     * I casted Warp World. The game said that Prime Speaker Zegana's ability
     * triggered for hitting the battlefield, however on all of our screens
     * nothing was on the battlefield (not even other things that should have
     * hit from Warp World). I tried to upload the log but it gave me an error
     * about it possibly being an attack vector. Sorry. Below is at least the
     * end of the log with hopefully some useful information:
     */
    @Test
    public void testWarpWorld() {
        // Each player shuffles all permanents he or she owns into his or her library, then reveals that many cards from the top of his or her library.
        // Each player puts all artifact, creature, and land cards revealed this way onto the battlefield, then does the same for enchantment cards,
        // then puts all cards revealed this way that weren't put onto the battlefield on the bottom of his or her library.
        addCard(Zone.HAND, playerA, "Warp World"); // Sorcery {5}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 8);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warp World");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, "Warp World", 0);
        assertPermanentCount(playerA, 8);

    }
}
