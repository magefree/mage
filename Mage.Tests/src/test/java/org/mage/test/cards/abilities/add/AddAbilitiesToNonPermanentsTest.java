
package org.mage.test.cards.abilities.add;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AddAbilitiesToNonPermanentsTest extends CardTestPlayerBase {


    /**
     * With Teferi, Mage of Zhalfir on the battlefield it has to be possible to search for 
     * a God of Storms in the deck by using Mystical Teachings.
     * 
     */
    @Test
    public void testSearchForCardWithFlashInLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Flash
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time they could cast a sorcery.        
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir");

        // Search your library for an instant card or a card with flash, reveal it, and put it into your hand. Then shuffle your library.
        // Flashback {5}{B}        
        addCard(Zone.HAND, playerA, "Mystical Teachings"); // "{3}{U}"
        
        addCard(Zone.LIBRARY, playerA, "Keranos, God of Storms");
        addCard(Zone.LIBRARY, playerA, "Plains", 3);

        skipInitShuffling();
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mystical Teachings");
        addTarget(playerA, "Keranos, God of Storms");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerA, "Mystical Teachings", 1);
        assertHandCount(playerA, "Keranos, God of Storms", 1);
    }
}
