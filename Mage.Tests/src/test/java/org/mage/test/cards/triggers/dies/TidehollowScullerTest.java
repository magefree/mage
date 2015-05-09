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

public class TidehollowScullerTest extends CardTestPlayerBase {

    /**
     * Test if the same Tidehollow Sculler is cast multiple times, the correct
     * corresponding exiled cards are returned
     */
    @Test
    public void testCardFromHandWillBeExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Tidehollow Sculler {W}{B}
        // When Tidehollow Sculler enters the battlefield, target opponent reveals his or her hand and you choose a nonland card from it. Exile that card.
        // When Tidehollow Sculler leaves the battlefield, return the exiled card to its owner's hand.
        addCard(Zone.HAND, playerA, "Tidehollow Sculler", 1);
        // Boomerang {U}{U}
        // Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Boomerang", 1);
        // Scout's Warning {W}
        // The next creature card you play this turn can be played as though it had flash.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Scout's Warning", 1);

        addCard(Zone.HAND, playerB, "Bloodflow Connoisseur", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tidehollow Sculler");
        addTarget(playerA, "Bloodflow Connoisseur");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Tidehollow Sculler");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scout's Warning", null, "Boomerang");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tidehollow Sculler", null, "", "When {this} leaves the battlefield, return the exiled card to its owner's hand.");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA,"Boomerang", 1);
        assertGraveyardCount(playerA,"Scout's Warning", 1);
        assertHandCount(playerB, "Bloodflow Connoisseur", 0); // never comes back because first Tidehollow Sculler left battlefield before target was exiled
        assertHandCount(playerB, "Silvercoat Lion", 0);
        assertExileCount("Silvercoat Lion", 1);

        assertPermanentCount(playerA, "Tidehollow Sculler", 1);

    }
}