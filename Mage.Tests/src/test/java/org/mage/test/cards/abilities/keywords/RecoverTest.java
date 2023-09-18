
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RecoverTest extends CardTestPlayerBase {

    /**
     * 702.58a Recover is a triggered ability that functions only while the card
     * with recover is in a player's graveyard. “Recover [cost]” means “When a
     * creature is put into your graveyard from the battlefield, you may pay
     * [cost]. If you do, return this card from your graveyard to your hand.
     * Otherwise, exile this card.”
     */
    @Test
    public void testReturnToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // You gain 4 life.
        // Recover {1}{W}
        addCard(Zone.HAND, playerA, "Sun's Bounty");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sun's Bounty");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerA, "Sun's Bounty", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 24);

        assertTappedCount("Plains", true, 4);

    }

    @Test
    public void testGoingToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // You gain 4 life.
        // Recover {1}{W}
        addCard(Zone.HAND, playerA, "Sun's Bounty");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sun's Bounty");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Plains", true, 2);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount("Sun's Bounty", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 24);

    }
}
