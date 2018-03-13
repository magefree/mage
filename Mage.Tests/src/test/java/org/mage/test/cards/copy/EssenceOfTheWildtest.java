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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EssenceOfTheWildtest extends CardTestPlayerBase {

    /**
     * Essence of the Wild does not seem to correctly apply its copy effect to
     * your creatures. Upon entering the battlefield the other creatures had a
     * small symbol at the top right of their card to view the original card -
     * however, both 'sides' showed only the same, original card.
     * Power/Toughness and other abilities were also still those of the original
     * cards.
     *
     * Note: This was observed in a deck controlled by the computer when testing
     * other decks.
     *
     */
    @Test
    public void testCreatureCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Creatures you control enter the battlefield as a copy of Essence of the Wild.
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild"); // 6/6
        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Essence of the Wild", 2);
        assertPowerToughness(playerA, "Essence of the Wild", 6, 6, Filter.ComparisonScope.All);

    }

    /**
     * I control Essence of the Wild and Back from the Brink on the battlefield,
     * and start using Back from the Brink on the creatures in my graveyard. The
     * creature tokens don't enter the battlefield as copies of Essence of the
     * Wild.
     *
     * Since it's an unusual situation, I checked around if there's something in
     * the rules that would prevent this combo from working. Found this link and
     * they confirmed that it should work, the tokens should come into play as
     * 6/6s.
     */
    @Test
    public void testWithBackFromTheBrink() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Creatures you control enter the battlefield as a copy of Essence of the Wild.
        addCard(Zone.BATTLEFIELD, playerA, "Essence of the Wild"); // 6/6
        // Exile a creature card from your graveyard and pay its mana cost: Create a tokenonto the battlefield that's a copy of that card. Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Back from the Brink"); // Enchantment

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile a creature card");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Essence of the Wild", 2);
        assertPowerToughness(playerA, "Essence of the Wild", 6, 6, Filter.ComparisonScope.All);

    }
}
