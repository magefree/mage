package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RobberOfTheRichTest extends CardTestPlayerBase {

    private static final String robber = "Robber of the Rich";
    private static final String passage = "Whitesun's Passage";
    private static final String blackguard = "Bane Alley Blackguard";

    @Test
    public void testRobber() {
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, robber);
        addCard(Zone.LIBRARY, playerB, passage);
        addCard(Zone.HAND, playerB, "Mountain", 3);

        attack(1, playerA, robber, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, passage);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 5);
        assertGraveyardCount(playerB, passage, 1);
    }

    @Test
    public void testRobberWithOtherRogue() {
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, robber);
        addCard(Zone.BATTLEFIELD, playerA, blackguard);
        addCard(Zone.LIBRARY, playerB, passage, 5);
        addCard(Zone.HAND, playerB, "Mountain");

        attack(1, playerA, robber, playerB);
        attack(3, playerA, blackguard, playerB);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, passage);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 5);
        assertGraveyardCount(playerB, passage, 1);
    }
}
