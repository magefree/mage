
package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OblivionSowerTest extends CardTestPlayerBase {

    /**
     * When putting lands into play from an opponent's exile zone using Oblivion
     * Sower, the BFZ dual lands behave exactly the opposite way of how they
     * should: if you control less than two basics, they enter the battlefield
     * untapped, and if you control more, they enter tapped.
     */
    @Test
    public void testPlayLandsFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // When you cast Oblivion Sower, target opponent exiles the top four cards of their library, then you may put any number of land cards that player owns from exile onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Oblivion Sower"); // Creature - 5/8

        // Canopy Vista enters the battlefield tapped unless you control two or more basic lands.
        addCard(Zone.LIBRARY, playerB, "Canopy Vista", 3);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Sower");
        // addTarget(playerA, playerB); Auto-chosen since only option

        addTarget(playerA, "Canopy Vista^Canopy Vista^Canopy Vista");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Oblivion Sower", 0);
        assertPermanentCount(playerA, "Oblivion Sower", 1);

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Canopy Vista", 3);

        assertTappedCount("Canopy Vista", false, 3);

    }
}
