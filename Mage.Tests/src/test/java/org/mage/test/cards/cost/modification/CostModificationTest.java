package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests cost reduction effects
 *
 * @author BetaSteward
 */
public class CostModificationTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Helm of Awakening"); //-1
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben"); //+1
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Trinisphere");  // Set mana cost to min 3
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben"); //+1
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, 1);
    }

    /**
     * Test that cost reduction also works with mana source restriction Myr
     * Superion Spend only mana produced by creatures to cast Myr Superion
     *
     * Etherium Sculptor {1}{U} Artifact Creature - Vedalken Artificer 1/2
     * Artifact spells you cast cost {1} less to cast.
     */
    @Test
    public void testCostReductionWithManaSourceRestrictionWorking() {
        // Artifact spells you cast cost {1} less to cast
        addCard(Zone.BATTLEFIELD, playerA, "Etherium Sculptor");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        // Myr Superion {2}
        // Spend only mana produced by creatures to cast Myr Superion.
        addCard(Zone.HAND, playerA, "Myr Superion");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Add {G} to your mana pool.");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Superion");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Myr Superion", 1); // Can be cast because mana was produced by a creature
    }

    @Test
    public void testCostReductionWithManaSourceRestrictionNotWorking() {
        addCard(Zone.BATTLEFIELD, playerA, "Etherium Sculptor");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerA, "Myr Superion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Superion");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Myr Superion", 0); // Can't be cast because mana was not produced by a creature
        assertHandCount(playerA, "Myr Superion", 1); // Can't be cast because mana was not produced by a creature
    }

    /*
     I had Goblin Electromancer, but somehow Pyretic Ritual wasn't cheaper howeverDesperate Ritual was.
     */
    @Test
    public void testCostReductionForPyreticRitual() {
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // Add {R}{R}{R} to your mana pool.
        addCard(Zone.HAND, playerA, "Pyretic Ritual");
        // Fated Conflagration deals 5 damage to target creature or planeswalker.
        // If it's your turn, scry 2. (Look at the top two cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)
        addCard(Zone.HAND, playerA, "Fated Conflagration");

        addCard(Zone.BATTLEFIELD, playerB, "Carnivorous Moss-Beast"); // 4/5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyretic Ritual");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fated Conflagration", "Carnivorous Moss-Beast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pyretic Ritual", 1);
        assertGraveyardCount(playerA, "Fated Conflagration", 1);
        assertGraveyardCount(playerB, "Carnivorous Moss-Beast", 1);
    }
}
