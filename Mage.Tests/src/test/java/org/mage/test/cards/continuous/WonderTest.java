package org.mage.test.cards.continuous;

import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.GRAVEYARD, playerA, "Wonder");
        addCard(Zone.GRAVEYARD, playerA, "Runeclaw Bear");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
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
        addCard(Zone.GRAVEYARD, playerA, "Wonder");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), false);
    }

    @Test
    public void testOtherZones() {
        addCard(Zone.BATTLEFIELD, playerA, "Wonder");
        addCard(Zone.HAND, playerA, "Wonder");
        addCard(Zone.LIBRARY, playerA, "Wonder");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), false);
    }

    @Test
    public void testDestroyIsland() {
        addCard(Zone.GRAVEYARD, playerA, "Wonder");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Demolish");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demolish", "Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Elite Vanguard", FlyingAbility.getInstance(), false);
    }

}
