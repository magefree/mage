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
public class AlmsBeastTest extends CardTestPlayerBase {

    @Test
    public void testLifelink() {
        // Creatures blocking or blocked by Alms Beast have lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Alms Beast");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        block(2, playerA, "Alms Beast", "Silvercoat Lion");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        
        assertLife(playerB, 22); // 20 + 2 from lifelink
    }

    @Test
    public void testNoLifelinkAfterCombat() {
        // {T}: Rootwater Hunter deals 1 damage to target creature or player.
        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Hunter");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        // Prevent all damage that would be dealt to target creature this turn.
        addCard(Zone.HAND, playerA, "Shielded Passage");

        
        // Creatures blocking or blocked by Alms Beast have lifelink.
        addCard(Zone.BATTLEFIELD, playerB, "Alms Beast");

        attack(2, playerB, "Alms Beast");
        block(2, playerA, "Rootwater Hunter", "Alms Beast");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Shielded Passage", "Rootwater Hunter");
        
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: ", playerB); // no life because lifelink ends at combat end
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Shielded Passage", 1);
        assertPermanentCount(playerA, "Rootwater Hunter", 1);
        
        assertLife(playerA, 21); // 20 + 1 from lifelink block
        assertLife(playerB, 19); // -1 from Rootwater Hunter
    }
}