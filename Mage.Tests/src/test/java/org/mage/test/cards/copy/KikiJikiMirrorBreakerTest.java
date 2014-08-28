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

package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KikiJikiMirrorBreakerTest extends CardTestPlayerBase {

    @Test
    public void testSimpleCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Voice of Resurgence", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a token that's a copy of target nonlegendary creature you control onto the battlefield. That token has haste. Sacrifice it at the beginning of the next end step.");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Voice of Resurgence", 2);
    }

    @Test
    public void testSimpleCopySacrificeAtEnd() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Voice of Resurgence", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a token that's a copy of target nonlegendary creature you control onto the battlefield. That token has haste. Sacrifice it at the beginning of the next end step.");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Elemental", 1); // because the copy was sacrificed
        assertPermanentCount(playerA, "Voice of Resurgence", 1);
    }

    @Test
    public void testCopyAndCopiedTokenDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Voice of Resurgence", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Flamebreak");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a token that's a copy of target nonlegendary creature you control onto the battlefield. That token has haste. Sacrifice it at the beginning of the next end step.");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Flamebreak");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 17);

        assertGraveyardCount(playerB, "Flamebreak", 1);

        assertPermanentCount(playerA, "Voice of Resurgence", 0);
        assertGraveyardCount(playerA, "Voice of Resurgence", 1);

        assertPermanentCount(playerA, "Elemental", 2);

        
    }

}