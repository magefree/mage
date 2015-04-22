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

package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ElvishArchdruidTest extends CardTestPlayerBase {

    /**
     * Elvish Archdruid gives +1/+1 to Nettle Sentinel
     * If Archdruid dies and Nettle Sentinel has got 2 damage
     * Nettle Sentile must go to graveyard immedeately
     */
    @Test
    public void testBoostWithUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Archdruid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Nettle Sentinel", 1);
        // Pyroclasm deals 2 damage to each creature.
        addCard(Zone.HAND, playerA, "Pyroclasm");        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyroclasm");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN); // has to be the same phase as the cast spell to see if Nettle Sentinel dies in this phase
        execute();

        assertGraveyardCount(playerA, "Pyroclasm", 1);
        assertPermanentCount(playerA, "Elvish Archdruid", 0);
        assertPermanentCount(playerA, "Nettle Sentinel", 0);
    }

}