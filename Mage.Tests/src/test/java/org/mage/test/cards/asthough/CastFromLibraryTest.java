/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CastFromLibraryTest extends CardTestPlayerBase {

    /**
     * Any creature that you cast through Vizier of the Menagerie (ie the card
     * on top of your deck) is castable at instant speed. This means that they
     * can be cast on your opponent's turn, before you scry (and thus change the
     * top card), or even right before the Vizier gets destroyed by a fatal push
     * or similar.
     */
    @Test
    public void testVizierOfTheMenagerie() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 2);

        // You may look at the top card of your library. (You may do this at any time.)
        // You may cast the top card of your library if it's a creature card.
        // You may spend mana as though it were mana of any type to cast creature spells.
        addCard(Zone.HAND, playerA, "Vizier of the Menagerie", 1); // Creature 3/4 {3}{G}

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vizier of the Menagerie");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Vizier of the Menagerie", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertLibraryCount(playerA, "Silvercoat Lion", 1);

    }
}
