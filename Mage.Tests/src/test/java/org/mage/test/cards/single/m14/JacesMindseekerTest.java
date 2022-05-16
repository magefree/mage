
package org.mage.test.cards.single.m14;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JacesMindseekerTest extends CardTestPlayerBase {

    /**
     * Jace's Mindseeker trigger ability is not working properly. It doesn't
     * allow me to cast an instant or sorcery if there is one among the 5 cards
     * put into the graveyard. I think the problem is that when the cards are
     * put into the graveyard, the cards can't be cast anymore. What if the
     * cards are revealed first before they are put into the graveyard? That
     * doesn't follow the sequence on the card, but it might solve the bug.
     */
    @Test
    public void testJacesMindseeker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Flying
        // When Jace's Mindseeker enters the battlefield, target opponent puts the top five cards of their library into their graveyard.
        // You may cast an instant or sorcery card from among them without paying its mana cost.
        addCard(Zone.HAND, playerA, "Jace's Mindseeker", 1); // Creature 4/4 {4}{U}{U}

        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Mindseeker");
        addTarget(playerA, playerB);
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Jace's Mindseeker", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 4);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }
}
