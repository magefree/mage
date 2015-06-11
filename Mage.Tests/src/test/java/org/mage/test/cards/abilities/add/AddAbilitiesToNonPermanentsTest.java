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
package org.mage.test.cards.abilities.add;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AddAbilitiesToNonPermanentsTest extends CardTestPlayerBase {


    /**
     * With Teferi, Mage of Zhalfir on the battlefield it has to be possible to search for 
     * a God of Storms in the deck by using Mystical Teachings.
     * 
     */
    @Test
    public void testSearchForCardWithFlashInLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Flash
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time he or she could cast a sorcery.        
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir");

        // Search your library for an instant card or a card with flash, reveal it, and put it into your hand. Then shuffle your library.
        // Flashback {5}{B}        
        addCard(Zone.HAND, playerA, "Mystical Teachings"); // "{3}{U}"
        
        addCard(Zone.LIBRARY, playerA, "Keranos, God of Storms");
        addCard(Zone.LIBRARY, playerA, "Plains", 3);

        skipInitShuffling();
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mystical Teachings");
        addTarget(playerA, "Keranos, God of Storms");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerA, "Mystical Teachings", 1);
        assertHandCount(playerA, "Keranos, God of Storms", 1);
    }
}
