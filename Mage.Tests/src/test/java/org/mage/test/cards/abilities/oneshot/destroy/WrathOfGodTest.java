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
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WrathOfGodTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains",4);
        addCard(Zone.HAND, playerA, "Wrath of God");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); 
        // If Mossbridge Troll would be destroyed, regenerate it.
        // Tap any number of untapped creatures you control other than Mossbridge Troll with total power 10 or greater: Mossbridge Troll gets +20/+20 until end of turn.        
        addCard(Zone.BATTLEFIELD, playerA, "Mossbridge Troll"); 

        // Flying
        // Darksteel Gargoyle is indestructible. ("Destroy" effects and lethal damage don't destroy it.)
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Gargoyle"); 

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); 
        addCard(Zone.BATTLEFIELD, playerB, "Mossbridge Troll"); 
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Gargoyle"); 

        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Mossbridge Troll", 0);
        assertPermanentCount(playerA, "Darksteel Gargoyle", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Mossbridge Troll", 0);
        assertPermanentCount(playerB, "Darksteel Gargoyle", 1);
    }
}