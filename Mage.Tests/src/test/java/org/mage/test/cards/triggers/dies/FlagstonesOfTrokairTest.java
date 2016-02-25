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
public class FlagstonesOfTrokairTest extends CardTestPlayerBase {

    /**
     * If a flagstones of trokair enchated with spreading seas that is then
     * ghostquartered. Should only fetch 1 land, but instead got 2.
     */
    @Test
    public void testDontTriggerIfIsland() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); // Enchantment Aura - {1}{U}
        // {tap}: Add {W} to your mana pool.
        // When Flagstones of Trokair is put into a graveyard from the battlefield, you may search your library for a Plains card and put it onto the battlefield tapped. If you do, shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Flagstones of Trokair", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        // {T}: Add {C} to your mana pool.
        // {T}, Sacrifice Ghost Quarter: Destroy target land. Its controller may search his or her library for a basic land card, put it onto the battlefield, then shuffle his or her library.
        addCard(Zone.BATTLEFIELD, playerB, "Ghost Quarter", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Flagstones of Trokair");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}, Sacrifice", "Flagstones of Trokair");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ghost Quarter", 1);
        assertGraveyardCount(playerA, "Flagstones of Trokair", 1);
        assertGraveyardCount(playerA, "Spreading Seas", 1);

        assertPermanentCount(playerA, 3);

    }

}
