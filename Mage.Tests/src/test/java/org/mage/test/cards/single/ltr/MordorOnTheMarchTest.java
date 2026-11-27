package org.mage.test.cards.single.ltr;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Mordor on the March
 * {3}{B}{R} Sorcery
 * Exile a creature card from your graveyard. Create a token that's a copy of it. It gains haste until
 * end of turn. Exile it at the beginning of the next end step.
 * Storm
 * <p>
 * CR 603.7a: the delayed "exile it" ability is created by the resolution whether or not a creature
 * card was exiled; with no token it still triggers at the beginning of the end step and exiles nothing.
 */
public class MordorOnTheMarchTest extends CardTestPlayerBase {

    private static final String mordor = "Mordor on the March";
    private static final String bear = "Grizzly Bears";

    private void addMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
    }

    @Test
    public void test_DelayedExileTriggerWithNoCreatureCardInGraveyard() {
        setStrictChooseMode(true);

        addMana();
        addCard(Zone.GRAVEYARD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, mordor);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mordor);

        runCode("delayed exile trigger at the beginning of the end step", 1, PhaseStep.END_TURN, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("one delayed ability on the stack", 1, game.getStack().size());
                    Assert.assertTrue("the delayed exile ability is on the stack",
                            game.getStack().getFirstOrNull().getStackAbility() instanceof AtTheBeginOfNextEndStepDelayedTriggeredAbility);
                });

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Swamp", 2);
        assertGraveyardCount(playerA, mordor, 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void test_CopyIsExiledAtEndStep() {
        setStrictChooseMode(true);

        addMana();
        addCard(Zone.GRAVEYARD, playerA, bear);
        addCard(Zone.HAND, playerA, mordor);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mordor);
        setChoice(playerA, bear);

        runCode("token copy is on the battlefield before the end step", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (info, player, game) -> {
                    Assert.assertEquals("token copy of the exiled creature", 1,
                            game.getBattlefield().getAllActivePermanents().stream()
                                    .filter(p -> p.isToken() && p.getName().equals(bear)).count());
                });

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertExileCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
    }
}
