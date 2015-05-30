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
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class HeroicTest extends CardTestPlayerBase {

    /**
     * When casting Dromoka's Command targeting two of my own Heroic creatures, only one of them triggers. 
     * It appears to be the one targeted with mode 4 (fight) rather than the one targeted with mode 3 (+1/+1 counter). 
     * Screenshot attached. Reproducible.
     */
    
    @Test
    public void testHeroicWithModal() {
        // Heroic - Whenever you cast a spell that targets Favored Hoplite, put a +1/+1 counter on Favored Hoplite and prevent all damage that would be dealt to it this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Favored Hoplite", 1); // 1/2
        // Heroic — Whenever you cast a spell that targets Lagonna-Band Trailblazer, put a +1/+1 counter on Lagonna-Band Trailblazer.
        addCard(Zone.BATTLEFIELD, playerA, "Lagonna-Band Trailblazer"); // 0/4
        
        // Mode 3 = Put a +1/+1 counter on target creature
        // Mode 4 = Target creature you control fights target creature you don't control
        addCard(Zone.HAND, playerA, "Dromoka's Command", 1); // {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); 
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dromoka's Command", "mode=3Lagonna-Band Trailblazer^mode=4Favored Hoplite^Silvercoat Lion");
        // Silvercoat lion will be set by AI as only possible target
        setModeChoice(playerA, "3");
        setModeChoice(playerA, "4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dromoka's Command", 1);
        
        assertPowerToughness(playerA, "Favored Hoplite", 2, 3);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Lagonna-Band Trailblazer", 2, 6);
        
    }
  
}
