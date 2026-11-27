package org.mage.test.cards.single.tmt;

import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Shredder, Shadow Master
 * {3}{B}{B}
 * Legendary Creature - Human Ninja 5/5
 * Whenever Shredder attacks a player, for each other opponent, create a token that's a copy of
 * Shredder tapped and attacking that player, except it isn't legendary. Sacrifice those tokens at
 * end of combat.
 * Whenever Shredder deals combat damage to a player, that player loses half their life, rounded up.
 * <p>
 * CR 603.7a: the delayed "sacrifice those tokens" ability is created by the resolution whether or
 * not any token was created. In a two-player game the defending player is the only opponent, so
 * "for each other opponent" makes no token, but the trigger is still created and resolves at end
 * of combat sacrificing nothing (CR 603.7c).
 */
public class ShredderShadowMasterTest extends CardTestPlayerBase {

    private static final String shredder = "Shredder, Shadow Master";

    @Test
    public void test_DelayedSacrificeTriggerWithNoToken() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, shredder);

        attack(1, playerA, shredder);

        runCode("delayed sacrifice trigger at end of combat", 1, PhaseStep.END_COMBAT, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed sacrifice ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility()
                                    instanceof AtTheEndOfCombatDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerA, shredder, 1);
    }
}
