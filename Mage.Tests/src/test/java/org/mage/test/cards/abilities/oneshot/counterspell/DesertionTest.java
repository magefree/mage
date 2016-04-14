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
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DesertionTest extends CardTestPlayerBase {

    /**
     * I cast Kozilek, Butcher of Truth from my hand and my opponent cast
     * Desertion targeting Kozilek, Butcher of Truth. Desertion resolved but
     * Kozilek, Butcher of Truth has disappeared (not in play for my opponent as
     * expected and not in my command zone or hand or graveyard or library)
     *
     */
    @Test
    public void testCounterKozilek() {
        // When you cast Kozilek, Butcher of Truth, draw four cards.
        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        // When Kozilek is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.
        addCard(Zone.HAND, playerA, "Kozilek, Butcher of Truth"); // {10}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion"); // {10}

        // Counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard.
        addCard(Zone.HAND, playerB, "Desertion"); // {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kozilek, Butcher of Truth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Desertion", "Kozilek, Butcher of Truth");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, "Desertion", 1);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Kozilek, Butcher of Truth", 0);
        assertHandCount(playerA, "Kozilek, Butcher of Truth", 0);
        assertPermanentCount(playerB, "Kozilek, Butcher of Truth", 1);

    }
}
