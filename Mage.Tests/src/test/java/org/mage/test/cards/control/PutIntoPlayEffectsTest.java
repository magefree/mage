
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PutIntoPlayEffectsTest extends CardTestPlayerBase {
 
    /**
     * Checks if cards put into play with Lord of the Void triggered ability
     * are correctly controlled by the controller of Lord of the Void
     * e.g. the top card of the library of the current controller of Oracle of Mul Daya is revealed
     */
    @Test
    public void testLordOfTheVoidDirectFromDeck() {
        skipInitShuffling();
        // You may play an additional land on each of your turns.
        // Play with the top card of your library revealed.
        // You may play the top card of your library if it's a land card.
        addCard(Zone.LIBRARY, playerA, "Oracle of Mul Daya", 4);
        // Whenever Lord of the Void deals combat damage to a player, exile the top seven cards 
        // of that player's library, then put a creature card from among them 
        // onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerB, "Lord of the Void");

        setStrictChooseMode(true);
        attack(2, playerB, "Lord of the Void", playerA);
        addTarget(playerB, "Oracle of Mul Daya");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // under control
        assertLife(playerA, 13);
        assertPermanentCount(playerB, "Oracle of Mul Daya", 1);

        Assert.assertFalse("Top card of the library of player A should not be reveled.", playerA.isTopCardRevealed());
        Assert.assertTrue("Top card of the library of player B should be reveled.", playerB.isTopCardRevealed());
        
    }
    
    /**
    * Same as testLordOfTheVoid, but checks if it works when the Oracle was in previous under it's owner's control.
    */
    @Test
    public void testLordOfTheVoidPreviouslyControlled() {
        addCard(Zone.BATTLEFIELD, playerA, "Oracle of Mul Daya");
        // Whenever Lord of the Void deals combat damage to a player, exile the top seven cards 
        // of that player's library, then put a creature card from among them 
        // onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Lord of the Void");

        // Put target creature on top of its owner's library.
        addCard(Zone.HAND, playerB, "Griptide");

        setStrictChooseMode(true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Griptide", "Oracle of Mul Daya");
        attack(2, playerB, "Lord of the Void", playerA);
        addTarget(playerB, "Oracle of Mul Daya");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // under control
        assertLife(playerA, 13);
        assertPermanentCount(playerB, "Oracle of Mul Daya", 1);

        Assert.assertTrue("Top card of the library of player B should be revealed.", playerB.isTopCardRevealed());
        Assert.assertFalse("Top card of the library of player A should not be revealed.", playerA.isTopCardRevealed());
    }
    
    /**
     * A Silvercoat Lion from opponents deck will be put into play with Bribery.
     * Then the opponent bounces this card back to hand and cast the spell itself.
     */
    @Test
    public void testBribery() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // Bribery - Sorcery {3}{U}{U}
        // Search target opponent's library for a creature card and put that card onto the battlefield
        // under your control. Then that player shuffles their library.
        addCard(Zone.HAND, playerA, "Bribery");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Eye of Nowhere - Sorcery - Arcane - {U}{U}
        // Return target permanent to its owner's hand.
        addCard(Zone.HAND, playerB, "Eye of Nowhere");
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 1);
        skipInitShuffling();

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bribery", playerB);        
        addTarget(playerA, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Eye of Nowhere", "Silvercoat Lion");        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion");        

        setStopAt(2, PhaseStep.END_TURN);
        execute();


        assertGraveyardCount(playerA, "Bribery", 1);
        
        assertGraveyardCount(playerB, "Eye of Nowhere", 1);
        assertHandCount(playerB, "Silvercoat Lion", 0);        
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }    
}
