package org.mage.test.cards.continuous;

import mage.Constants;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Wonder
 *   As long as Wonder is in your graveyard and you control an Island, creatures you control have flying.
 *
 * @author magenoxx_at_gmail.com
 */
public class WonderTest extends CardTestPlayerBase {

    /**
     * Tests creatures for Flying gained from Wonder ability when all conditions were met
     */
    @Test
    public void testCardWithAllConditionsMet() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Wonder");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Runeclaw Bear");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Corpse Traders");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Corpse Traders", FlyingAbility.getInstance(), true);
        assertAbility(playerB, "Llanowar Elves", FlyingAbility.getInstance(), false);

        // check no flying in graveyard
        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.equals("Runeclaw Bear")) {
                Assert.assertFalse(card.getAbilities().contains(FlyingAbility.getInstance()));
            }
        }
    }

    @Test
    public void testNoIsland() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Wonder");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), false);
    }

    @Test
    public void testOtherZones() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Wonder");
        addCard(Constants.Zone.HAND, playerA, "Wonder");
        addCard(Constants.Zone.LIBRARY, playerA, "Wonder");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), false);
    }

    @Test
    public void testDestroyIsland() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Wonder");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Demolish");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Demolish", "Island");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), false);
    }

}
