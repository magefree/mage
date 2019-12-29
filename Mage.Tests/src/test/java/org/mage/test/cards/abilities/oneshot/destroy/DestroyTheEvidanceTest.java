
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DestroyTheEvidanceTest extends CardTestPlayerBase {

    // Destroy the Evidence - Sorcery {4}{B}
    // Destroy target land. Its controller reveals cards from the top of his
    // or her library until they reveal a land card, then puts those cards into their graveyard.
    
    /**
     * The target land is destroyed
     */
    @Test
    public void testLandIsDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Destroy the Evidence");
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        
        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Destroy the Evidence", "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Destroy the Evidence", 1);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
        assertPermanentCount(playerB, "Mountain", 1);
        
        assertLife(playerB, 20);
        
    }
    /**
     * The target land is Indestructible
     */
    @Test
    public void testLandIsIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Destroy the Evidence");
        
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 2);
        
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        
        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Destroy the Evidence", "Darksteel Citadel");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Destroy the Evidence", 1);
        assertGraveyardCount(playerB, "Darksteel Citadel", 0);
        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
        assertPermanentCount(playerB, "Darksteel Citadel", 2);
        
        assertLife(playerB, 20);
        
    }    
    
    /**
     * The target land is blinked meanwhile -> spell has no more valid target, spell has to fizzle
     */
   @Test
    public void testLandIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Destroy the Evidence");
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Ghostly Flicker", 1);
        
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        
        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Destroy the Evidence", "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ghostly Flicker", "Mountain^Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Destroy the Evidence", 1);
        assertGraveyardCount(playerB, "Ghostly Flicker", 1);
        assertPermanentCount(playerB, "Mountain", 2);
        assertPermanentCount(playerB, "Island", 1);
        
        assertGraveyardCount(playerB, 1);
        
        assertLife(playerB, 20);
        
    }    

}
