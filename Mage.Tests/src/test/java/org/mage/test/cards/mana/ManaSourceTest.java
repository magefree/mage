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
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ManaSourceTest extends CardTestPlayerBase {

    /**
     * I can use Simian Spirit Guide's mana to cast Myr Superion, but it is a
     * creature card and not a creature when it is in hand, so it's wrong.
     */
    @Test
    public void testCantCastWithCreatureCard() {
        // Exile Simian Spirit Guide from your hand: Add {R} to your mana pool.
        addCard(Zone.HAND, playerB, "Simian Spirit Guide", 1);
        // Spend only mana produced by creatures to cast Myr Superion.
        addCard(Zone.HAND, playerB, "Myr Superion", 1); // {2}

        addCard(Zone.BATTLEFIELD, playerB, "Manakin", 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Exile");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Myr Superion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Simian Spirit Guide", 1);

        assertPermanentCount(playerB, "Myr Superion", 0);
        assertHandCount(playerB, "Myr Superion", 1);

    }

}
