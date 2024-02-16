
package org.mage.test.cards.single.ths;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KeranosGodOfStormsTest extends CardTestPlayerBase {

    @Test
    public void testKeranosNormal() {
        // Reveal the first card you draw on each of your turns. 
        // Whenever you reveal a land card this way, draw a card. 
        // Whenever you reveal a nonland card this way, Keranos deals 3 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Keranos, God of Storms"); // {3}{U}{R}
        // Look at target player's hand.
        // Draw a card.
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 3);
        skipInitShuffling();

        addTarget(playerB, playerA); // damage from Keranos trigger

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Keranos, God of Storms", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1); // 1 Lion from the draw and 1 Lion from Peek
        
        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }
    
    /**
     * Keranos, God of Storms, will look at the first card drawn after he is
     * played, not the first card drawn each turn.
     *
     * My opponent, draws, plays Keranos, then plays Stroke of Genius. Keranos
     * triggers on the first card for Stroke of Genius.
     */
    @Test
    public void testKeranosCastAfterFirstDraw() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // Reveal the first card you draw on each of your turns. 
        // Whenever you reveal a land card this way, draw a card. 
        // Whenever you reveal a nonland card this way, Keranos deals 3 damage to any target.
        addCard(Zone.HAND, playerB, "Keranos, God of Storms"); // {3}{U}{R}
        // Look at target player's hand.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Peek");
        
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 3);
        skipInitShuffling();

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Keranos, God of Storms");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Peek", playerA); // you won't do damage because it's not the first draw this turn - Draw in draw phase was the first

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Keranos, God of Storms", 1);
        assertGraveyardCount(playerB, "Peek", 1);

        assertHandCount(playerB, "Silvercoat Lion", 2); // 1 Lion from the draw and 1 Lion from Peek
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
