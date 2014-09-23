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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MisdirectionTest extends CardTestPlayerBase {

    /**
     * Tests if Misdirection for target opponent works correctly
     * https://github.com/magefree/mage/issues/574
     */
    @Test
    public void testChangeTargetOpponent() {
        // Target opponent discards two cards. Put the top two cards of your library into your graveyard.
        addCard(Zone.HAND, playerA, "Rakshasa's Secret");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        /*    
        Misdirection {3}{U}{U}
        Instant
        You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        Change the target of target spell with a single target.
        */
        addCard(Zone.HAND, playerB, "Misdirection");
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakshasa's Secret", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Rakshasa's Secret", "Rakshasa's Secret");
        addTarget(playerA, playerA); // only possible target is player A
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rakshasa's Secret", 1);
        assertGraveyardCount(playerB, "Misdirection", 1);
        assertHandCount(playerB, "Silvercoat Lion", 2);

    }
}