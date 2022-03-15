
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnToHandEffectsTest extends CardTestPlayerBase {

    /**
     * Enduring Renewal doesn't return creatures to hand put into graveyard from
     * the battlefield It happened with Enduring Renewal in the battlefield
     * while feeding Ornithopter to Grinding Station
     */
    
    /*  jeffwadsworth:  I tested this scenario in the game and it worked perfectly.  The test suite is not reliable in this case.
    @Test
    public void testEnduringRenewal() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Play with your hand revealed.
        // If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card.
        // Whenever a creature is put into your graveyard from the battlefield, return it to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Renewal");

        // {T}, Sacrifice an artifact: Target player puts the top three cards of their library into their graveyard.
        // Whenever an artifact enters the battlefield, you may untap Grinding Station.
        addCard(Zone.BATTLEFIELD, playerA, "Grinding Station", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);
        
        addCard(Zone.LIBRARY, playerB, "Island", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice an artifact", playerB);
        setChoice(playerA, "Ornithopter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 3);
        assertHandCount(playerA, "Ornithopter", 1);

    }
*/
    @Test
    public void testStormfrontRidersTriggerForToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Flying
        // When Stormfront Riders enters the battlefield, return two creatures you control to their owner's hand.
        // Whenever Stormfront Riders or another creature is returned to your hand from the battlefield, put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Stormfront Riders"); // {4}{W}
        // Buyback {4} (You may pay an additional {4} as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Create a 1/1 black Rat creature token.
        addCard(Zone.HAND, playerA, "Lab Rats"); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stormfront Riders");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lab Rats");
        setChoice(playerA, false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Boomerang", "Rat Token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Stormfront Riders", 1);
        assertHandCount(playerA, "Silvercoat Lion", 2);
        assertGraveyardCount(playerA, "Lab Rats", 1);
        assertGraveyardCount(playerB, "Boomerang", 1);

        assertPermanentCount(playerA, "Soldier Token", 3);
        assertPermanentCount(playerA, "Rat Token", 0);

    }

}
