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
    public void testCardTrinisphere() {
        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Trinisphere");
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

    // Trinisphere interacts incorrectly with Phyrexian mana. As implemented, Gitaxian Probe gets a required cost of {2}{U/P},
    // which allows paying 2 life and only 2 mana. This is incorrect: Trinisphere requires that at least 3 mana be paid, and
    // payment through life doesn't count. (Source: http://blogs.magicjudges.org/rulestips/2012/08/how-trinisphere-works-with-phyrexian-mana/)
    @Test
    public void testCardTrinispherePhyrexianMana() {
        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Trinisphere");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Look at target player's hand.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Gitaxian Probe"); // Sorcery {U/P}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Gitaxian Probe", playerA);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, "Gitaxian Probe", 1);
        assertGraveyardCount(playerB, "Gitaxian Probe", 0);
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
     I had Goblin Electromancer, but somehow Pyretic Ritual wasn't cheaper however Desperate Ritual was.
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
    
    /*
    * Reported bug: Grand Arbiter Augustin IV makes moth spells you cast and your opponent cast {1} more. Should only affect opponent's spells costs.
    */
    @Test
    public void testArbiterIncreasingCostBothPlayers() {
        
        String gArbiter = "Grand Arbiter Augustin IV";
        String divination = "Divination";
        String doomBlade = "Doom Blade";
                
        /*
        Grand Arbiter Augustin IV {2}{W}{U} 
            2/3 Legendary Creature - Human Advisor
            White spells you cast cost 1 less to cast.
            Blue spells you cast cost 1 less to cast.
            Spells your opponents cast cost 1 more to cast.
        */
        addCard(Zone.BATTLEFIELD, playerA, gArbiter);
        addCard(Zone.HAND, playerA, divination); // {2}{U} Sorcery: draw two cards
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        
        addCard(Zone.HAND, playerB, doomBlade);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2); // {1}{B} Instant: destroy target non-black creature
        
        // Divination should only cost {1}{U} now with the cost reduction in place for your blue spells.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, divination);
        
        // Doom Blade cast by the opponent should cost {2}{B} now with the cost increase in effect for opponent spells.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, doomBlade, gArbiter);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, divination, 1);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 2);
        assertPermanentCount(playerA, gArbiter, 1);
        assertHandCount(playerB, doomBlade, 1);
    }
}
