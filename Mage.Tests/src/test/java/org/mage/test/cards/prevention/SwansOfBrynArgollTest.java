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
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SwansOfBrynArgollTest extends CardTestPlayerBase{
    
    /**
     * Since you can't prevent damage that Combust deals, it should be able to kill Swans of Bryn Argoll. 
     */
    @Test
    public void testMarkOfAsylumEffect() {
        // 4/3 Flying
        // If a source would deal damage to Swans of Bryn Argoll, prevent that damage. The source's controller draws cards equal to the damage prevented this way.
        addCard(Zone.BATTLEFIELD, playerA, "Swans of Bryn Argoll");
        
        // Combust deals 5 damage to target white or blue creature. The damage can't be prevented.
        // Combust can't be countered by spells or abilities.
        addCard(Zone.HAND, playerB, "Combust", 1);        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Combust", "Swans of Bryn Argoll");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Combust", 1);
        assertPermanentCount(playerA, "Swans of Bryn Argoll", 0);
        assertGraveyardCount(playerA, "Swans of Bryn Argoll", 1);
        assertHandCount(playerA, 0);

    }
    /**
     * Since you can't prevent damage that Combust deals, it should be able to kill Swans of Bryn Argoll. 
     */
    @Test
    public void testAgainstBanefire() {
        // 4/3 Flying
        // If a source would deal damage to Swans of Bryn Argoll, prevent that damage. The source's controller draws cards equal to the damage prevented this way.
        addCard(Zone.BATTLEFIELD, playerA, "Swans of Bryn Argoll");
        
        // Banefire deals X damage to target creature or player.
        // If X is 5 or more, Banefire can't be countered by spells or abilities and the damage can't be prevented.
        addCard(Zone.HAND, playerB, "Banefire", 1);        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 8);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", "Swans of Bryn Argoll");
        setChoice(playerB, "X=7");
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Banefire", 1);
        assertPermanentCount(playerA, "Swans of Bryn Argoll", 0);
        assertGraveyardCount(playerA, "Swans of Bryn Argoll", 1);
        assertHandCount(playerA, 0);

    }    
}