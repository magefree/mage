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
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnToHandTest extends CardTestPlayerBase {

    /*
        Undiscovered Paradise not being returned to hand on untap step.
        The land provides mana of any color, but it's never returned to hand.
     */
    @Test
    public void testExilesCreatureAtEndStep() {

        // {W}: Honor Guard gets +0/+1 until end of turn.
        addCard(Zone.HAND, playerA, "Honor Guard"); // Creature 1/1

        // {T}: Add one mana of any color to your mana pool. During your next untap step, as you untap your permanents, return Undiscovered Paradise to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Undiscovered Paradise", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Honor Guard");

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Honor Guard", 1);
        assertPermanentCount(playerA, "Undiscovered Paradise", 0);
        assertHandCount(playerA, "Undiscovered Paradise", 1);

    }
}
