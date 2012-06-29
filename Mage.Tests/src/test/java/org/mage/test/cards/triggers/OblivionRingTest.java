package org.mage.test.cards.triggers;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 *
 * Card: When Oblivion Ring enters the battlefield, exile another target nonland permanent.
 * When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
 */
public class OblivionRingTest extends CardTestPlayerBase {

    /**
     * When Oblivion Ring enters the battlefield, exile another target nonland permanent.
     */
    @Test
    public void testFirstTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.HAND, playerA, "Oblivion Ring");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

    /**
     * When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
     */
    @Test
    public void testSecondTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.HAND, playerA, "Oblivion Ring");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Constants.Zone.HAND, playerB, "Naturalize");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Naturalize", "Oblivion Ring");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testWithOblivionRingExile() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.HAND, playerA, "Oblivion Ring");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Constants.Zone.HAND, playerB, "Revoke Existence");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Revoke Existence", "Oblivion Ring");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    /**
     * Tests that when Oblivion Ring gets destroyed planeswalker returns with new counters and can be used second time at the same turn
     */
    @Test
    public void testExilePlaneswalker() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Constants.Zone.HAND, playerA, "Oblivion Ring");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Jace Beleren");
        addCard(Constants.Zone.HAND, playerA, "Revoke Existence");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "-1: Target player draws 1 card", playerA);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Revoke Existence", "Oblivion Ring");
        activateAbility(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "-1: Target player draws 1 card", playerA);

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertPermanentCount(playerA, "Jace Beleren", 1); // returns back
        assertHandCount(playerA, 2); // can use ability twice
    }
}
