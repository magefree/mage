package org.mage.test.cards.single.gtc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.o.ObzedatGhostCouncil Obzedat, Ghost Council} {1}{W}{W}{B}{B}, 5/5
 * When Obzedat enters, target opponent loses 2 life and you gain 2 life.
 * At the beginning of your end step you may exile Obzedat. If you do, return it to the battlefield under its
 * owner's control at the beginning of your next upkeep. It gains haste.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class ObzedatGhostCouncilTest extends CardTestPlayerBase {

    private static final String obzedat = "Obzedat, Ghost Council";

    /**
     * It comes back at the next upkeep with haste, and its enters trigger drains again.
     */
    @Test
    public void testReturnsWithHaste() {
        addCard(Zone.BATTLEFIELD, playerA, obzedat);

        setChoice(playerA, true); // exile it at the end step
        addTarget(playerA, playerB); // enters trigger on the way back
        attack(3, playerA, obzedat);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, obzedat, 1);
        assertLife(playerB, 20 - 2 - 5);
        assertLife(playerA, 20 + 2);
    }

    /**
     * 400.7: once the exiled card has left exile it is a new object, so there is nothing to return.
     */
    @Test
    public void testNotReturnedAfterLeavingExile() {
        addCard(Zone.BATTLEFIELD, playerA, obzedat);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Pull from Eternity"); // {W}: put target face-up exiled card into its owner's graveyard

        setChoice(playerA, true); // exile it at the end step
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Pull from Eternity", obzedat);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, obzedat, 0);
        assertGraveyardCount(playerA, obzedat, 1);
    }
}
