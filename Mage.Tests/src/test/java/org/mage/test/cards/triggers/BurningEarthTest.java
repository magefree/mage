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
public class BurningEarthTest extends CardTestPlayerBase {

    /**
     * Burning Earth - It doesn't cause the damage it should. My opponent taps a
     * Blood Crypt and an Overgrown Tomb for black and green mana respectively
     * and casts his card all the while without taking any damage.
     *
     */
    @Test
    public void testBurningEarth() {
        // Destroy target artifact or creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Putrefy"); // {1}{B}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blood Crypt", 1); // {B}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Overgrown Tomb", 1); // {B}{G}
        
        // Whenever a player taps a nonbasic land for mana, Burning Earth deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Burning Earth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Putrefy", "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Putrefy", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

}
