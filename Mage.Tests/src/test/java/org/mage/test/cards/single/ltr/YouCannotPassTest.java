package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class YouCannotPassTest extends CardTestPlayerBase {
    
    private static final String ycp = "You Cannot Pass!"; // {W} Instant
    // Destroy target creature that blocked or was blocked by a legendary creature this turn.

    private static final String yargle = "Yargle, Glutton of Urborg"; // legendary 9/3
    private static final String treefolk = "Indomitable Ancients"; // 2/10

    @Test
    public void testBlocked() {
        addCard(Zone.BATTLEFIELD, playerA, yargle);
        addCard(Zone.BATTLEFIELD, playerB, treefolk);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, ycp);

        attack(1, playerA, yargle);
        block(1, playerB, treefolk, yargle);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ycp, treefolk);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ycp, 1);
        assertGraveyardCount(playerB, treefolk, 1);
        assertDamageReceived(playerA, yargle, 2);
    }

    @Test
    public void testBlockedBy() {
        addCard(Zone.BATTLEFIELD, playerA, treefolk);
        addCard(Zone.BATTLEFIELD, playerB, yargle);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, ycp);

        attack(1, playerA, treefolk);
        block(1, playerB, yargle, treefolk);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ycp, treefolk);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ycp, 1);
        assertGraveyardCount(playerA, treefolk, 1);
        assertDamageReceived(playerB, yargle, 2);
    }

}
