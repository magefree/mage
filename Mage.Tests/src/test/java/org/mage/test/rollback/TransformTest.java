package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TransformTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // First strike, lifelink
        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        // BACK: It That Rides as One
        // Creature 4/4 First strike, lifelink
        addCard(Zone.HAND, playerA, "Lone Rider"); // Creature {1}{W} 1/1
        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Venerable Monk"); // Creature {2}{W} 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lone Rider");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Venerable Monk");

        attack(3, playerA, "Lone Rider");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 19);

        assertPermanentCount(playerA, "Venerable Monk", 1);
        assertPermanentCount(playerA, "It That Rides as One", 1);

    }

    @Test
    public void testRollbackWithTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // First strike, lifelink
        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        // BACK: It That Rides as One
        // Creature 4/4 First strike, lifelink
        addCard(Zone.HAND, playerA, "Lone Rider"); // Creature {1}{W} 1/1

        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Venerable Monk"); // Creature {2}{W} 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lone Rider");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Venerable Monk");

        attack(3, playerA, "Lone Rider");

        rollbackTurns(3, PhaseStep.END_TURN, playerA, 0);

        rollbackAfterActionsStart();
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Venerable Monk");
        rollbackAfterActionsEnd();

        attack(3, playerA, "Lone Rider");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 19);

        assertPermanentCount(playerA, "Venerable Monk", 1);
        assertPermanentCount(playerA, "It That Rides as One", 1);
    }
}