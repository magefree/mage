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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GenjuOfTheSpiresTest extends CardTestPlayerBase {

    /**
     * Genju of the Spires isn't working properly: enchanted mountain got
     * destroyed by a Strip Mine, the ability triggered, I chose Yes, but it
     * didn't come back to my hand. There was nothing in play that could have
     * affected it. We rolled back to confirm and it happened a second time. I
     * haven't checked every one of them, but I think there's a chance that the
     * other 5 Genjus could also have the same problem?
     */
    @Test
    public void testGenjuReturnsToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Enchant Mountain
        // {2}: Enchanted Mountain becomes a 6/1 red Spirit creature until end of turn. It's still a land.
        // When enchanted Mountain is put into a graveyard, you may return Genju of the Spires from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Genju of the Spires", 1); // {R}

        // {T}: Add {C} to your mana pool.
        // {T}, Sacrifice Strip Mine: Destroy target land.
        addCard(Zone.BATTLEFIELD, playerB, "Strip Mine", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Genju of the Spires", "Mountain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice", "Mountain", "Genju of the Spires", StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Strip Mine", 1);

        assertGraveyardCount(playerA, "Genju of the Spires", 0);
        assertPermanentCount(playerA, "Genju of the Spires", 0);
        assertHandCount(playerA, "Genju of the Spires", 1);
    }

}
