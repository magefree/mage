package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class StrokeOfMidnightTest extends CardTestPlayerBase {

    /**
     * Stroke of Midnight
     * {2}{W}
     * Instant
     * <p>
     * Destroy target nonland permanent. Its controller creates a 1/1 white Human creature token.
     */
    private static final String stroke = "Stroke of Midnight";

    // Indestructible permanent. Should still give a token.
    private static final String relic = "Darksteel Relic";

    // 2/1 creature
    private static final String piker = "Goblin Piker";

    @Test
    public void destroyOpponentPermanent() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, stroke);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, piker);

        castSpell(1, PhaseStep.UPKEEP, playerA, stroke, piker);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, piker, 0);
        assertGraveyardCount(playerB, piker, 1);
        assertTokenCount(playerB, "Human Token", 1);
    }

    @Test
    public void destroyOwnPermanent() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, stroke);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, piker);

        castSpell(1, PhaseStep.UPKEEP, playerA, stroke, piker);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, piker, 0);
        assertGraveyardCount(playerA, piker, 1);
        assertTokenCount(playerA, "Human Token", 1);
    }

    @Test
    public void indestructiblePermanent() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, stroke);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, relic);

        castSpell(1, PhaseStep.UPKEEP, playerA, stroke, relic);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, relic, 1);
        assertTokenCount(playerB, "Human Token", 1);
    }
}
