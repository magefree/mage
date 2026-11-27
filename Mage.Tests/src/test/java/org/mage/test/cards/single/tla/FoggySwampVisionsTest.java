package org.mage.test.cards.single.tla;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Foggy Swamp Visions
 * {1}{B}{B}
 * Sorcery
 * As an additional cost to cast this spell, waterbend {X}.
 * Exile X target creature cards from graveyards. For each creature card exiled this way, create a
 * token that's a copy of it. At the beginning of your next end step, sacrifice those tokens.
 * <p>
 * CR 603.7a: the delayed "sacrifice those tokens" ability is created by the resolution whether or
 * not any token was created; with X = 0 it still triggers at the beginning of the next end step
 * and sacrifices nothing (CR 603.7c).
 */
public class FoggySwampVisionsTest extends CardTestPlayerBase {

    private static final String foggy = "Foggy Swamp Visions";

    @Test
    public void test_DelayedSacrificeTriggerWithNoExiledCard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, foggy);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, foggy);
        setChoice(playerA, "X=0");

        runCode("delayed sacrifice trigger at the beginning of the end step", 1, PhaseStep.END_TURN, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed sacrifice ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility()
                                    instanceof AtTheBeginOfNextEndStepDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, foggy, 1);
    }
}
