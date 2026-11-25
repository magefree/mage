package org.mage.test.cards.single.eoe;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Devastating Onslaught
 * {X}{X}{R}
 * Sorcery
 * Create X tokens that are copies of target artifact or creature you control. Those tokens gain
 * haste until end of turn. Sacrifice them at the beginning of the next end step.
 * <p>
 * CR 603.7a: the delayed "sacrifice them" ability is created by the resolution whether or not any
 * token was created; with X = 0 it still triggers at the beginning of the next end step and
 * sacrifices nothing (CR 603.7c).
 */
public class DevastatingOnslaughtTest extends CardTestPlayerBase {

    private static final String onslaught = "Devastating Onslaught";
    private static final String bears = "Grizzly Bears";

    @Test
    public void test_DelayedSacrificeTriggerWithNoToken() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.HAND, playerA, onslaught);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, onslaught);
        setChoice(playerA, "X=0");
        addTarget(playerA, bears);

        runCode("delayed sacrifice trigger at the beginning of the end step", 1, PhaseStep.END_TURN, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed sacrifice ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility()
                                    instanceof AtTheBeginOfNextEndStepDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, 2);
        assertPermanentCount(playerA, bears, 1);
        assertGraveyardCount(playerA, onslaught, 1);
    }
}
