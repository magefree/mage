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
package org.mage.test.cards.continuous.playereffects;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PlayCardsFromGraveyardTest extends CardTestPlayerBase {

    @Test
    public void testYawgmothsWill() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        addCard(Zone.HAND, playerA, "Yawgmoth's Will"); // {2}{B}
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        // You gain 3 life.
        // Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Reviving Dose"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reviving Dose");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 10/4/2004 	It will exile itself since it goes to the graveyard after its effect starts.
        assertExileCount("Yawgmoth's Will", 1);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertExileCount("Reviving Dose", 1);
        assertExileCount("Silvercoat Lion", 1);

        assertHandCount(playerA, 1);

        assertLife(playerA, 23);
        assertLife(playerB, 20);
    }

    /**
     * The null casting cost cards(Ancestral Visions, Lotus Bloom) can be cast
     * from graveyard with Yawgmoth's Will
     */
    @Test
    public void testYawgmothsWillNoCastingCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        addCard(Zone.HAND, playerA, "Yawgmoth's Will"); // {2}{B}
        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Vision");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will");
        // you may not suspend it from graveyard
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");
        // It may not be possible to cast it from graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Vision");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 10/4/2004 	It will exile itself since it goes to the graveyard after its effect starts.
        assertExileCount("Yawgmoth's Will", 1);

        assertHandCount(playerA, 0);
        assertExileCount("Ancestral Vision", 0);

        assertGraveyardCount(playerA, "Ancestral Vision", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
