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
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class CounterspellTest extends CardTestPlayerBase {

    /**
        It looks like Boom//Bust can't be countered (although it says it's countered in the log).

        Code: Select all
            13:10: Benno casts Boom [8ce] targeting Mountain [4c8] Island [80c]
            13:10: Benno casts Counterspell [2b7] targeting Boom [8ce]
            13:10: Benno puts Boom [8ce] from stack into his or her graveyard
            13:10: Boom is countered by Counterspell [2b7]
            13:10: Benno puts Counterspell [2b7] from stack into his or her graveyard
            13:10: Mountain [4c8] was destroyed
            13:10: Island [80c] was destroyed
     */
    
    @Test
    public void testCounterSplitSpell() {
        // Boom - Sorcery   {1}{R}
        // Destroy target land you control and target land you don't control.        
        addCard(Zone.HAND, playerA, "Boom // Bust");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boom", "Mountain^Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Boom");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Boom // Bust", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        
        assertPermanentCount(playerA, "Mountain", 2);
        assertPermanentCount(playerB, "Island", 2);
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
