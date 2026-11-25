package org.mage.test.cards.single.clb;

import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Phantom Steed
 * {3}{U}
 * Creature - Horse Illusion 4/3
 * Flash
 * When Phantom Steed enters the battlefield, exile another target creature you control until
 * Phantom Steed leaves the battlefield.
 * Whenever Phantom Steed attacks, create a tapped and attacking token that's a copy of the
 * exiled card, except it's an Illusion in addition to its other types. Sacrifice that token at
 * end of combat.
 * <p>
 * CR 603.7a: the delayed "sacrifice that token" ability is created by the resolution whether or
 * not a token was created; with none it still triggers at end of combat and sacrifices nothing
 * (CR 603.7c).
 */
public class PhantomSteedTest extends CardTestPlayerBase {

    private static final String steed = "Phantom Steed";

    @Test
    public void test_DelayedSacrificeTriggerWithNoExiledCard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, steed);

        attack(1, playerA, steed);

        runCode("delayed sacrifice trigger at end of combat", 1, PhaseStep.END_COMBAT, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed sacrifice ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility()
                                    instanceof AtTheEndOfCombatDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, steed, 1);
        assertExileCount(playerA, 0);
    }
}
