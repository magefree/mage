
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UginTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        // +2: Ugin, the Spirit Dragon deals 3 damage to any target.
        // -X: Exile each permanent with converted mana cost X or less that's one or more colors.
        // -10: You gain 7 life, draw 7 cards, then put up to seven permanent cards from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Ugin, the Spirit Dragon"); // starts with 7 Loyality counters
        // Whenever a creature dies, you may put a quest counter on Quest for the Gravelord.
        addCard(Zone.BATTLEFIELD, playerA, "Quest for the Gravelord");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.LIBRARY, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        // -2: Create a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World.
        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        addCard(Zone.HAND, playerB, "Nissa, Vastwood Seer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Nissa, Vastwood Seer", true);
        setChoice(playerB, "Yes");
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Forest");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "-2: Create Ashaya, the Awoken World, a legendary 4/4 green Elemental creature token.");

        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Ashaya, the Awoken World", "Silvercoat Lion");

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "-X: Exile each permanent with mana value X or less that's one or more colors");
        setChoice(playerA, "X=3");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ugin, the Spirit Dragon", 1);
        assertCounterCount("Ugin, the Spirit Dragon", CounterType.LOYALTY, 6);  // 7 + 2 - 3

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Ashaya, the Awoken World", 0);

        assertExileCount("Nissa, Vastwood Seer", 1);
        assertExileCount("Quest for the Gravelord", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
}
