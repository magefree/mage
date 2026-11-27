package org.mage.test.cards.single.fut;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Nacatl War-Pride
 * {3}{G}{G}{G}
 * Creature - Cat Warrior 3/3
 * Whenever Nacatl War-Pride attacks, create X tokens that are copies of Nacatl War-Pride and that
 * are tapped and attacking, where X is the number of creatures defending player controls. Exile
 * the tokens at the beginning of the next end step.
 * <p>
 * CR 603.7a: the delayed "exile the tokens" ability is created by the resolution whether or not
 * any token was created; with none it still triggers at the beginning of the end step and exiles
 * nothing (CR 603.7c).
 */
public class NacatlWarPrideTest extends CardTestPlayerBase {

    private static final String nacatl = "Nacatl War-Pride";

    @Test
    public void test_DelayedExileTriggerWithNoDefendingCreature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nacatl);

        attack(1, playerA, nacatl);

        runCode("delayed exile trigger at the beginning of the end step", 1, PhaseStep.END_TURN, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed exile ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility()
                                    instanceof AtTheBeginOfNextEndStepDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, nacatl, 1);
        assertExileCount(playerA, 0);
    }
}
