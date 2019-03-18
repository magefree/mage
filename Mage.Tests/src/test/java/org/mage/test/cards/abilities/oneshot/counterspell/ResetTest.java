
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ResetTest extends CardTestPlayerBase {

    /**
     * I was playing a game against Show and Tell with the deck Solidarity (or
     * Reset High Tide) and xmage would not allow me to play Reset costing me
     * the match.
     *
     * I suspect it may be a timing issue because of Resets odd wording
     * preventing it from being played on the opponents upkeep.
     *
     * It was working in the previous build so this issue surprised me.
     */
    @Test
    public void testResetDoesWork() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2, true);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Cast Reset only during an opponent's turn after their upkeep step.
        // Untap all lands you control.
        addCard(Zone.HAND, playerB, "Reset");
        // Counter target spell.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Dismiss");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reset");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Dismiss", "Lightning Bolt");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Reset", 1);
        assertGraveyardCount(playerB, "Dismiss", 1);

        assertHandCount(playerB, 1);

    }

    @Test
    public void testResetDoesNotWork() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Cast Reset only during an opponent's turn after their upkeep step.
        // Untap all lands you control.
        addCard(Zone.HAND, playerA, "Reset");
        // Counter target spell.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Dismiss"); // {2}{U}{U}
        addCard(Zone.HAND, playerA, "Lumengrid Warden"); // {1}{U}  1/3

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lumengrid Warden");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reset");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dismiss", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lumengrid Warden", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerA, "Reset", 1);
        assertHandCount(playerA, "Dismiss", 1);
        assertLife(playerB, 20);
        assertLife(playerA, 17);

    }
}
