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

public class SerraAscendantTest extends CardTestPlayerBase {

    /**
     * The game goes on; he plays his Serra Ascendant on turn one, passes the
     * turn, you play your newly unbanned Wild Nacatl with a Stomping Ground and
     * also pass the turn. On turn 2, he casts a Martyr of Sands and sacrifices
     * it, revealing 3 white cards to gain 9 life and end up at 29. He goes to
     * the combat phase, declares Serra as an attacker, and you happily block
     * him, thinking that this is such a bad move from him. After the damage is
     * dealt, the Serra is still there, bigger than ever.
     */
    @Test
    public void testSilence() {
        addCard(Zone.HAND, playerA, "Plains", 2);
        // As long as you have 30 or more life, Serra Ascendant gets +5/+5 and has flying.
        addCard(Zone.HAND, playerA, "Serra Ascendant");
        // {1}, Reveal X white cards from your hand, Sacrifice Martyr of Sands: You gain three times X life.
        addCard(Zone.HAND, playerA, "Martyr of Sands");
        addCard(Zone.HAND, playerA, "Silvercoat Lion",3);

        addCard(Zone.HAND, playerB, "Stomping Ground", 1);
        addCard(Zone.HAND, playerB, "Wild Nacatl", 1);
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serra Ascendant");
        
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Stomping Ground");
        setChoice(playerB, "Yes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wild Nacatl");
        
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Martyr of Sands");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},You may reveal X white cards from your hand");
        setChoice(playerA,"X=3");
        
        attack(3, playerA, "Serra Ascendant");
        block(3, playerB, "Wild Nacatl", "Serra Ascendant");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        
        execute();

        assertGraveyardCount(playerA, "Martyr of Sands", 1);

        assertLife(playerB, 18);
        assertLife(playerA, 30);
        
        assertPermanentCount(playerB, "Wild Nacatl", 1);
    
        assertPermanentCount(playerA, "Serra Ascendant", 1);
        assertPowerToughness(playerA, "Serra Ascendant", 6, 6);
        
    }       

}