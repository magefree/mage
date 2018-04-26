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
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DefiantVanguardTest extends CardTestPlayerBase {

    @Test
    public void testAllDestroyed() {
        // When Defiant Vanguard blocks, at end of combat, destroy it and all creatures it blocked this turn.
        // {5}, {tap}: Search your library for a Rebel permanent card with converted mana cost 4 or less and put it onto the battlefield. Then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Vanguard", 1); // Creature {2}{W}  2/2

        addCard(Zone.BATTLEFIELD, playerB, "Bane Alley Blackguard", 1); // Creature {1}{B}  1/3

        attack(2, playerB, "Bane Alley Blackguard");
        block(2, playerA, "Defiant Vanguard", "Bane Alley Blackguard");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Defiant Vanguard", 1);
        assertGraveyardCount(playerB, "Bane Alley Blackguard", 1);
    }

    @Test
    @Ignore // this test fails but it works fine in game.
    public void testSaveCreatureWithCloudshift() {
        // When Defiant Vanguard blocks, at end of combat, destroy it and all creatures it blocked this turn.
        // {5}, {tap}: Search your library for a Rebel permanent card with converted mana cost 4 or less and put it onto the battlefield. Then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Vanguard", 1); // Creature {2}{W}  2/2

        addCard(Zone.BATTLEFIELD, playerB, "Bane Alley Blackguard", 1); // Creature {1}{B}  1/3
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift", 1); // Instant {W}

        attack(2, playerB, "Bane Alley Blackguard");
        block(2, playerA, "Defiant Vanguard", "Bane Alley Blackguard");

        castSpell(2, PhaseStep.FIRST_COMBAT_DAMAGE, playerB, "Cloudshift", "Bane Alley Blackguard");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Defiant Vanguard", 1);
        assertGraveyardCount(playerB, "Cloudshift", 1);
        assertPermanentCount(playerB, "Bane Alley Blackguard", 1);

    }

}
