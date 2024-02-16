
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GlimpseOfNatureTest extends CardTestPlayerBase {

    /**
     * Glimpse of Nature triggers do not draw a card.
     */
    @Test
    public void testCardsAreDrawnFromCreatureCasting() {
        // Whenever you cast a creature spell this turn, draw a card.
        addCard(Zone.HAND, playerA, "Glimpse of Nature", 1);// Sorcery {G}
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // Flash (You may cast this spell any time you could cast an instant.)
        // First strike (This creature deals combat damage before creatures without first strike.)
        addCard(Zone.HAND, playerB, "Benalish Knight", 1); // Creature {2}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse of Nature", true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Benalish Knight");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Glimpse of Nature", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertPermanentCount(playerB, "Benalish Knight", 1);

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 0);
    }

}
