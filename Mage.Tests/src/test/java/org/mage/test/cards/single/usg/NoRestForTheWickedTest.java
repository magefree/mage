package org.mage.test.cards.single.usg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author anonymous
 */
public class NoRestForTheWickedTest extends CardTestPlayerBase {

    /**
     * Checks that all playerA creatures are back to playerA's hand
     */
    //@Test
    public void testSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "No Rest for the Wicked");
        
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Sengir Vampire");

        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Flowering Lumberknot");
        addCard(Zone.BATTLEFIELD, playerB, "Moorland Inquisitor");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "No Rest for the Wicked");
        
        attack(3, playerA, "Memnite");
        attack(3, playerA, "Royal Assassin");
        attack(3, playerA, "Sengin Vampire");        
        block(3, playerB, "Moorland Inquisitor", "Memnite");
        
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice {this}: Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 1); //No Rest only
        assertGraveyardCount(playerA, "No Rest for the Wicked", 1);
        assertGraveyardCount(playerA, "Memnite", 0);
        assertHandCount(playerA, "Memnite", 1);
    }
    
    /**
     * Checks that all playerA creatures sacrificed prior to playing
     * No Rest for the Wicked are back to playerA's hand
     */
    //@Test
    public void testSacrificeAfterDying() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "No Rest for the Wicked");
        
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Sengir Vampire");

        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Flowering Lumberknot");
        addCard(Zone.BATTLEFIELD, playerB, "Moorland Inquisitor");
        
        attack(3, playerA, "Memnite");
        attack(3, playerA, "Royal Assassin");
        attack(3, playerA, "Sengin Vampire");        
        block(3, playerB, "Moorland Inquisitor", "Memnite");
        
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "No Rest for the Wicked");
        
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice {this}: Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "No Rest for the Wicked", 0);
        assertGraveyardCount(playerA, 1); //No Rest only
        assertGraveyardCount(playerA, "No Rest for the Wicked", 1);
        assertGraveyardCount(playerA, "Memnite", 0);
        assertHandCount(playerA, "Memnite", 1);
    }
    
    /**
     * Take ownership of a creature from playerB and destroy it.
     * Checks that after sacrificing No Rest for the Wicked it is on playerB's
     * graveyard, and that it's not on either player's hand.
     * 400.3
     */
    @Test
    public void testTakeControlThenSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.HAND, playerA, "No Rest for the Wicked");        
        addCard(Zone.HAND, playerA, "Beacon of Unrest");
        
        addCard(Zone.BATTLEFIELD, playerA, "Moorland Inquisitor");

        addCard(Zone.BATTLEFIELD, playerB, "Moorland Inquisitor");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "No Rest for the Wicked");
        
        attack(4, playerB, "Memnite");
        block(4, playerA, "Moorland Inquisitor", "Memnite");
        
        castSpell(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Beacon of Unrest", "Memnite");
        
        attack(7, playerA, "Memnite");
        block(7, playerB, "Moorland Inquisitor", "Memnite");
        activateAbility(7, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice {this}: Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.");

        setStopAt(7, PhaseStep.END_TURN);
        execute();
        
        assertGraveyardCount(playerB, "Memnite", 1);
        assertHandCount(playerB, "Memnite", 0);

        assertPermanentCount(playerA, "No Rest for the Wicked", 0);
        assertGraveyardCount(playerA, 1); //No Rest only
        assertGraveyardCount(playerA, "No Rest for the Wicked", 1);
        assertGraveyardCount(playerA, "Memnite", 0);
        assertHandCount(playerA, "Memnite", 0);
    }
}
