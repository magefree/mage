package org.mage.test.cards.single.blb;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Skyskipper Duo
 * {4}{U} Creature - Bird Frog 3/3
 * Flying
 * When Skyskipper Duo enters, exile up to one other target creature you control. Return it to the
 * battlefield under its owner's control at the beginning of the next end step.
 * <p>
 * CR 603.7a: the delayed return ability is created by the resolution whether or not a creature
 * was exiled; with zero targets it still triggers at the beginning of the end step and returns nothing.
 */
public class SkyskipperDuoTest extends CardTestPlayerBase {

    private static final String duo = "Skyskipper Duo";
    private static final String bear = "Grizzly Bears";

    @Test
    public void test_DelayedReturnTriggerWithNoTarget() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, duo);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, duo);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no other creature: zero targets

        runCode("delayed return trigger at the beginning of the end step", 1, PhaseStep.END_TURN, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed return ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility() instanceof AtTheBeginOfNextEndStepDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, duo, 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void test_TargetReturnsAtEndStep() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, duo);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, duo);
        addTarget(playerA, bear);

        runCode("target is exiled before the end step", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (info, player, game) -> Assert.assertEquals("bear exiled", 0,
                        game.getBattlefield().getAllActivePermanents().stream()
                                .filter(p -> p.getName().equals(bear)).count()));

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, duo, 1);
        assertPermanentCount(playerA, bear, 1);
        assertExileCount(playerA, 0);
    }
}
