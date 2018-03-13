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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JacesMindseekerTest extends CardTestPlayerBase {

    /**
     * Jace's Mindseeker trigger ability is not working properly. It doesn't
     * allow me to cast an instant or sorcery if there is one among the 5 cards
     * put into the graveyard. I think the problem is that when the cards are
     * put into the graveyard, the cards can't be cast anymore. What if the
     * cards are revealed first before they are put into the graveyard? That
     * doesn't follow the sequence on the card, but it might solve the bug.
     */
    @Test
    public void testJacesMindseeker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Flying
        // When Jace's Mindseeker enters the battlefield, target opponent puts the top five cards of his or her library into his or her graveyard.
        // You may cast an instant or sorcery card from among them without paying its mana cost.
        addCard(Zone.HAND, playerA, "Jace's Mindseeker", 1); // Creature 4/4 {4}{U}{U}

        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Mindseeker");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");
        setChoice(playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace's Mindseeker", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 4);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }
}
