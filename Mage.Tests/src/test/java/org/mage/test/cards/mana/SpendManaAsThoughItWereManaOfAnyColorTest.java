
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SpendManaAsThoughItWereManaOfAnyColorTest extends CardTestPlayerBase {

    @Test
    public void testDaxosOfMeletis() {
        // Put two 2/2 black Zombie creature tokens onto the battlefield.
        // Flashback (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.LIBRARY, playerA, "Moan of the Unhallowed", 1);

        skipInitShuffling();

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library.
        // You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and
        // you may spend mana as though it were mana of any color to cast it.
        addCard(Zone.BATTLEFIELD, playerB, "Daxos of Meletis", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        attack(2, playerB, "Daxos of Meletis");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Moan of the Unhallowed");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 24);

        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, "Moan of the Unhallowed", 1);
        assertPermanentCount(playerB, "Zombie Token", 2);

    }

    /**
     * Celestial Dawn does not allow spending of off-color mana for any purpose.
     * Had a Black Market down, was trying to cast Darksteel Forge, could not
     * spend the black mana on the forge.
     */
    @Test
    public void testCelestialDawn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        // Lands you control are Plains.
        // Nonland cards you own that aren't on the battlefield, spells you control, and nonland permanents you control are white.
        // You may spend white mana as though it were mana of any color.
        // You may spend other mana only as though it were colorless mana.
        addCard(Zone.BATTLEFIELD, playerA, "Celestial Dawn", 1);

        // Whenever a creature dies, put a charge counter on Black Market.
        // At the beginning of your precombat main phase, add {B} for each charge counter on Black Market.
        addCard(Zone.BATTLEFIELD, playerA, "Black Market", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Artifacts you control are indestructible.
        addCard(Zone.HAND, playerA, "Darksteel Forge", 1); // Artifact {9}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Lightning Bolt", "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Forge");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertCounterCount("Black Market", CounterType.CHARGE, 1);

        assertPermanentCount(playerA, "Darksteel Forge", 1);

    }

    @Test
    public void testCelestialDawnAny() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Lands you control are Plains.
        // Nonland cards you own that aren't on the battlefield, spells you control, and nonland permanents you control are white.
        // You may spend white mana as though it were mana of any color.
        // You may spend other mana only as though it were colorless mana.
        addCard(Zone.BATTLEFIELD, playerA, "Celestial Dawn", 1);

        addCard(Zone.HAND, playerA, "Vedalken Mastermind", 1); // Creature {U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vedalken Mastermind");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vedalken Mastermind", 1);

    }
}
