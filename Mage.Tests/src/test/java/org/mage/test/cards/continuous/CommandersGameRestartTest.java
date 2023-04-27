package org.mage.test.cards.continuous;

import mage.constants.CommanderCardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.watchers.common.CommanderPlaysCountWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4PlayersWithAIHelps;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class CommandersGameRestartTest extends CardTestCommander4PlayersWithAIHelps {

    @Test
    public void test_KarnLiberated_Manual() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated.
        // Then put those cards onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated", 1);

        // prepare commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // prepare karn
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn Liberated", CounterType.LOYALTY, 20);

        // check watcher before restart
        runCode("before restart", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            UUID commanderId = game.getCommandersIds(player, CommanderCardType.ANY, false).stream().findFirst().orElse(null);
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            Assert.assertEquals("commander tax must be x1", 1, watcher.getPlaysCount(commanderId));
        });

        // game restart
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-14: ");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // check watcher after restart
        UUID commanderId = currentGame.getCommandersIds(playerA, CommanderCardType.ANY, false).stream().findFirst().orElse(null);
        CommanderPlaysCountWatcher watcher = currentGame.getState().getWatcher(CommanderPlaysCountWatcher.class);
        Assert.assertEquals("commander tax must be x0", 0, watcher.getPlaysCount(commanderId));

        assertPermanentCount(playerA, 0); // no cards on battle after game restart
    }

    @Test
    public void test_KarnLiberated_AI() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated.
        // Then put those cards onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated", 1);
        //
        addCard(Zone.HAND, playerB, "Balduvian Bears", 5);
        addCard(Zone.HAND, playerC, "Balduvian Bears", 5);
        addCard(Zone.HAND, playerD, "Balduvian Bears", 5);

        // prepare commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // prepare karn
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karn Liberated", CounterType.LOYALTY, 50);

        // check watcher before restart
        runCode("before restart", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            UUID commanderId = game.getCommandersIds(player, CommanderCardType.ANY, false).stream().findFirst().orElse(null);
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            Assert.assertEquals("commander tax must be x1", 1, watcher.getPlaysCount(commanderId));
        });

        // possible bug: ai can use restart in one of the simulations, so it can freeze the game (if bugged)
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerC);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
