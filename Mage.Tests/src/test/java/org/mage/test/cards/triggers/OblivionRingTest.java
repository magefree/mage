package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 *
 * Card: When Oblivion Ring enters the battlefield, exile another target nonland
 * permanent. When Oblivion Ring leaves the battlefield, return the exiled card
 * to the battlefield under its owner's control.
 */
public class OblivionRingTest extends CardTestPlayerBase {

    /**
     * When Oblivion Ring enters the battlefield, exile another target nonland
     * permanent.
     */
    @Test
    public void testFirstTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Oblivion Ring");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

    /**
     * When Oblivion Ring leaves the battlefield, return the exiled card to the
     * battlefield under its owner's control.
     */
    @Test
    public void testSecondTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Oblivion Ring");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Naturalize");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Naturalize", "Oblivion Ring");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testWithOblivionRingExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Oblivion Ring");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.HAND, playerB, "Revoke Existence");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Revoke Existence", "Oblivion Ring");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    /**
     * Tests that when Oblivion Ring gets destroyed planeswalker returns with
     * new counters and can be used second time at the same turn
     */
    @Test
    public void testExilePlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Oblivion Ring");
        addCard(Zone.BATTLEFIELD, playerA, "Jace Beleren");
        // Exile target artifact or enchantment.
        addCard(Zone.HAND, playerA, "Revoke Existence");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-1: Target player draws a card", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Revoke Existence", "Oblivion Ring", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-1: Target player draws a card", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount("Oblivion Ring", 1);
        assertGraveyardCount(playerA, "Revoke Existence", 1);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertGraveyardCount(playerA, "Jace Beleren", 0);
        assertPermanentCount(playerA, "Jace Beleren", 1); // returns back
        assertHandCount(playerA, 2); // can use ability twice
    }

    /**
     * Oblivion Ring leaves from battlefield Effect brings Hangarback Walker
     * back with counters. But with rules it should come back with no counters
     */
    @Test
    public void testReturningHangarbackWalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        // Hangarback Walker enters the battlefield with X +1/+1 counters on it.
        // When Hangarback Walker dies, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield for each +1/+1 counter on Hangarback Walker.
        // {1}, {t}: Put a +1/+1 counter on Hangarback Walker.
        addCard(Zone.HAND, playerA, "Hangarback Walker"); // {X}{X}
        addCard(Zone.HAND, playerA, "Naturalize");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.HAND, playerB, "Oblivion Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hangarback Walker");
        setChoice(playerA, "X=2");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Oblivion Ring");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Naturalize", "Oblivion Ring");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Oblivion Ring", 0);
        assertGraveyardCount(playerB, "Oblivion Ring", 1);
        assertPermanentCount(playerA, "Hangarback Walker", 0);
        assertGraveyardCount(playerA, "Hangarback Walker", 1);

    }
}
