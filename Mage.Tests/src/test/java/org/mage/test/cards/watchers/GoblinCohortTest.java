package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.g.GoblinCohort Goblin Cohort}
 * {R}
 * Creature — Goblin Warrior
 * Goblin Cohort can’t attack unless you’ve cast a creature spell this turn.
 *
 * @author BetaSteward
 */
public class GoblinCohortTest extends CardTestPlayerBase {
    /**
     * Goblin Cohort should be able to attack if the condition is met.
     */
    @Test
    public void testCanAttack() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Cohort");
        addCard(Zone.HAND, playerB, "Goblin Roughrider");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Goblin Roughrider");
        attack(2, playerB, "Goblin Cohort");
        
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
                
        assertAttacking("Goblin Cohort", true);
    }

    /**
     * Goblin Cohort shouldn't be allowed to attack if the condition isn't met
     */
    @Test
    public void testCannotAttack() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Cohort");
        addCard(Zone.HAND, playerB, "Goblin Roughrider");

        attack(2, playerB, "Goblin Cohort");
        
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);

        // TODO: Need a way to check if a creature can attack without this try-catch format
        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about cannot have action, but got:\n" + e.getMessage());
            }
        }
                
        assertAttacking("Goblin Cohort", false);
    }
}
