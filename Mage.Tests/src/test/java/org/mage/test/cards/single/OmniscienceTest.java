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

public class OmniscienceTest extends CardTestPlayerBase {

    /**
     * Omniscience   {7}{U}{U}{U}
     *
     * Enchantment
     * You may cast nonland cards from your hand without paying their mana costs.
     *
     */

    @Test
    public void testCastingCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        
        /* player.getPlayable does not take alternate
           casting costs in account, so for the test the mana has to be available
           but won't be used
        */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);


        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Plains", false);
    }

    @Test
    public void testCastingSplitCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        addCard(Zone.HAND, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fire // Ice", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        
        assertTapped("Island", false);
        assertTapped("Mountain", false);
    }

    @Test
    public void testCastingShrapnelBlast() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        /* player.getPlayable does not take alternate
           casting costs in account, so for the test the mana has to be available
           but won't be used
        */
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);

        addCard(Zone.HAND, playerA, "Shrapnel Blast", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrapnel Blast");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);

        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertTapped("Mountain", false);
    }

}
