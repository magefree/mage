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
package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OblivionSowerTest extends CardTestPlayerBase {

    /**
     * When putting lands into play from an opponent's exile zone using Oblivion
     * Sower, the BFZ dual lands behave exactly the opposite way of how they
     * should: if you control less than two basics, they enter the battlefield
     * untapped, and if you control more, they enter tapped.
     */
    @Test
    public void testPlayLandsFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // When you cast Oblivion Sower, target opponent exiles the top four cards of his or her library, then you may put any number of land cards that player owns from exile onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Oblivion Sower"); // Creature - 5/8

        // Canopy Vista enters the battlefield tapped unless you control two or more basic lands.
        addCard(Zone.LIBRARY, playerB, "Canopy Vista", 3);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Sower");

        addTarget(playerA, "Canopy Vista^Canopy Vista^Canopy Vista");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Oblivion Sower", 0);
        assertPermanentCount(playerA, "Oblivion Sower", 1);

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Canopy Vista", 3);

        assertTappedCount("Canopy Vista", false, 3);

    }
}
