
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DesertionTest extends CardTestPlayerBase {

    /**
     * I cast Kozilek, Butcher of Truth from my hand and my opponent cast
     * Desertion targeting Kozilek, Butcher of Truth. Desertion resolved but
     * Kozilek, Butcher of Truth has disappeared (not in play for my opponent as
     * expected and not in my command zone or hand or graveyard or library)
     *
     */
    @Test
    public void testCounterKozilek() {
        // When you cast Kozilek, Butcher of Truth, draw four cards.
        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        // When Kozilek is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
        addCard(Zone.HAND, playerA, "Kozilek, Butcher of Truth"); // {10}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion"); // {10}

        // Counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard.
        addCard(Zone.HAND, playerB, "Desertion"); // {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kozilek, Butcher of Truth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Desertion", "Kozilek, Butcher of Truth");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, "Desertion", 1);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Kozilek, Butcher of Truth", 0);
        assertHandCount(playerA, "Kozilek, Butcher of Truth", 0);
        assertPermanentCount(playerB, "Kozilek, Butcher of Truth", 1);

    }
}
