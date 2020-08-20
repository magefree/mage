
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KaradorGhostChieftainTest extends CardTestPlayerBase {

    @Test
    public void castReducedTwo() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2);
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        addCard(Zone.HAND, playerA, "Karador, Ghost Chieftain");// {5}{B}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();        
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);
    }

    /**
     * I had a couple problems in a commander game last night. Using Karador,
     * Ghost Chieftain as my commander. Most of the match, his casting cost was
     * correctly calculated, reducing the extra commander tax and generic mana
     * costs by the number of creature cards in my graveyard. On the 4th cast
     * though, the cost was 12 mana total. I tried casting a few times over a
     * couple turns, but it was still an incorrect cost (I had probably 15
     * creatures in my graveyard).
     */
    @Test
    public void castReducedSeven() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 7);
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        addCard(Zone.HAND, playerA, "Karador, Ghost Chieftain");// {5}{B}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();        
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);
    }
    
    @Test
    public void castCastTwiceFromGraveyard() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 7);
        
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift");// Instant {W}
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        addCard(Zone.HAND, playerA, "Karador, Ghost Chieftain");// {5}{B}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Karador, Ghost Chieftain");
                     
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertAllCommandsUsed();
        
        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertGraveyardCount(activePlayer, "Cloudshift", 1);
        
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);
    }
}
