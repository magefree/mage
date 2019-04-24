
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
