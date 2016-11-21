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
public class ParleyTest extends CardTestPlayerBase {

    /**
     * Selvala, Explorer Returned reveals cards at the wrong moments.
     *
     * I've noticed her revealing all cards during my upkeep, when I'm not even
     * activating her
     *
     */
    @Test
    public void testNothingHappens() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Parley - {T}: Each player reveals the top card of his or her library. For each nonland card revealed this way, add {G} to your mana pool and you gain 1 life. Then each player draws a card.
        addCard(Zone.HAND, playerA, "Selvala, Explorer Returned");// Creature {1}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Selvala, Explorer Returned");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Selvala, Explorer Returned", 1);
    }

    @Test
    public void testTwoMana() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Parley - {T}: Each player reveals the top card of his or her library. For each nonland card revealed this way, add {G} to your mana pool and you gain 1 life. Then each player draws a card.
        addCard(Zone.HAND, playerA, "Selvala, Explorer Returned");// Creature {1}{G}{W}

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 2);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Selvala, Explorer Returned");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Parley");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Selvala, Explorer Returned", 1);
        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }

}
