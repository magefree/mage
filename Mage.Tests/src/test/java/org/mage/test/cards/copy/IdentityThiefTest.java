package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class IdentityThiefTest extends CardTestPlayerBase {

    /**
     * This is probably a narrow case of a wider problem base. Identity Thief
     * copied Molten Sentry and died immediately (should have been either a 5/2
     * or a 2/5, whatever the original Molten Sentry was).
     */
    @Test
    public void testCopyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // As Molten Sentry enters the battlefield, flip a coin. If the coin comes up heads, Molten Sentry enters the battlefield as a 5/2 creature with haste.
        // If it comes up tails, Molten Sentry enters the battlefield as a 2/5 creature with defender.
        addCard(Zone.HAND, playerA, "Molten Sentry"); // {3}{R}

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Identity Thief"); // {2}{U}{U}

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Molten Sentry");
        setFlipCoinResult(playerA, true);

        attack(2, playerB, "Identity Thief");
        addTarget(playerB, "Molten Sentry");
        setChoice(playerB, true);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertExileCount("Molten Sentry", 1);

        assertPermanentCount(playerB, "Identity Thief", 0);
        assertPermanentCount(playerB, "Molten Sentry", 1);
    }

    @Test
    public void testCopyPrimalClay() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
        addCard(Zone.HAND, playerA, "Primal Clay"); // {4}

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Identity Thief"); // {2}{U}{U}

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Primal Clay");
        setChoice(playerA, "a 3/3 artifact creature");

        attack(2, playerB, "Identity Thief");
        setChoice(playerB, "Yes");
        addTarget(playerB, "Primal Clay");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Primal Clay", 1);

        assertPermanentCount(playerB, "Identity Thief", 0);
        assertPermanentCount(playerB, "Primal Clay", 1);
        assertPowerToughness(playerB, "Primal Clay", 3, 3);
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/2131
     *      If I copy a creature with a +1/+1 counter on it, it copies the counter as well as the other stats. This should not be the case.
     */
    @Test
    public void testShouldNotCopyP1P1Counters() {
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate", 1); // {1}{G} 2/3 vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Battlegrowth"); // {G} instant - Put a +1/+1 counter on target creature.

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Identity Thief"); // {2}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth");
        addTarget(playerA, "Sylvan Advocate");

        attack(2, playerB, "Identity Thief");
        addTarget(playerB, "Sylvan Advocate");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertExileCount("Sylvan Advocate", 1);

        assertGraveyardCount(playerA, "Battlegrowth", 1);
        assertPermanentCount(playerB, "Identity Thief", 0);
        assertPermanentCount(playerB, "Sylvan Advocate", 1);
        assertCounterCount(playerB, "Sylvan Advocate", CounterType.P1P1, 0);
        assertPowerToughness(playerB, "Sylvan Advocate", 2, 3);
    }
}
