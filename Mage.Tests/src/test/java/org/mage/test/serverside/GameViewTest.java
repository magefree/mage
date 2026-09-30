package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.server.game.GameSessionWatcher;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class GameViewTest extends CardTestPlayerBase {

    @Test
    public void test_GameViewForPlayersAndWatchers() {
        addCard(Zone.HAND, playerA, "Forest", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        UUID userA = UUID.randomUUID();
        UUID userB = UUID.randomUUID();
        UUID userWatcher = UUID.randomUUID();

        // game copy test
        Assert.assertEquals(0, currentGame.getOptions().bannedUsers.size());
        Game copiedGame = currentGame.copy();
        Assert.assertEquals(0, copiedGame.getOptions().bannedUsers.size());
        //
        currentGame.getOptions().bannedUsers.add("123");
        Assert.assertEquals(1, currentGame.getOptions().bannedUsers.size());
        copiedGame = currentGame.copy();
        Assert.assertEquals(1, copiedGame.getOptions().bannedUsers.size());

        // normal hand
        GameView gameView = getGameView(playerA, userA);
        Assert.assertNotNull(gameView);
        Assert.assertNotNull(gameView.getMyHand());
        Assert.assertEquals(1, gameView.getMyHand().size());
        Assert.assertEquals("Forest", gameView.getMyHand().values().stream().findFirst().get().getName());
        Assert.assertEquals(0, gameView.getLookedAt().size());
        Assert.assertEquals(0, gameView.getRevealed().size());
        Assert.assertEquals(0, gameView.getWatchedHands().size());

        // empty hand
        gameView = getGameView(playerB, userB);
        Assert.assertNotNull(gameView);
        Assert.assertNotNull(gameView.getMyHand());
        Assert.assertEquals(0, gameView.getMyHand().size());
        Assert.assertEquals(0, gameView.getLookedAt().size());
        Assert.assertEquals(0, gameView.getRevealed().size());
        Assert.assertEquals(0, gameView.getWatchedHands().size());

        // watcher hand
        gameView = getGameView(null, userWatcher);
        Assert.assertNotNull(gameView);
        Assert.assertNotNull(gameView.getMyHand());
        Assert.assertEquals(0, gameView.getMyHand().size());
        Assert.assertEquals(0, gameView.getLookedAt().size());
        Assert.assertEquals(0, gameView.getRevealed().size());
        Assert.assertEquals(0, gameView.getWatchedHands().size());

        // A gives access to hand for B and watcher
        playerA.addPermissionToShowHandCards(userB);
        playerA.addPermissionToShowHandCards(userWatcher);

        gameView = getGameView(playerA, userA);
        Assert.assertEquals(0, gameView.getWatchedHands().size());
        gameView = getGameView(playerB, userB);
        Assert.assertEquals(1, gameView.getWatchedHands().size());
        gameView = getGameView(null, userWatcher);
        Assert.assertEquals(1, gameView.getWatchedHands().size());
    }

    @Test
    public void test_GameViewPerformance() {
        // simple performance test - real long game will contain much more effects and other things

        int multiply = 10;
        addCard(Zone.HAND, playerA, "Forest", 2 * multiply);
        addCard(Zone.HAND, playerA, "Spiked Corridor // Torture Pit", 1 * multiply);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2 * multiply);

        addCard(Zone.GRAVEYARD, playerA, "Forest", 2 * multiply);
        addCard(Zone.GRAVEYARD, playerA, "Spiked Corridor // Torture Pit", 1 * multiply);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2 * multiply);

        addCard(Zone.EXILED, playerA, "Forest", 2 * multiply);
        addCard(Zone.EXILED, playerA, "Spiked Corridor // Torture Pit", 1 * multiply);
        addCard(Zone.EXILED, playerA, "Grizzly Bears", 2 * multiply);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 * multiply);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 * multiply);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 * multiply);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2 * multiply);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2 * multiply);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();


        // performance tests
        int copyOperations = 1000;

        // warmup, otherwise a first measured run also pays for JIT and class loading
        for (int i = 0; i < 100; i++) {
            currentGame.copy();
            new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
            getGameView(playerA);
        }

        // counters are global, so remember a value before each use case and print a difference
        Game copiedGame = null;
        long copiesBefore = currentGame.getCopiedCount();
        long viewsBefore = GameView.CREATED_COUNT.get();
        long startMs = System.currentTimeMillis();
        for (int i = 0; i < copyOperations; i++) {
            copiedGame = currentGame.copy();
        }
        long gameCopyMs = System.currentTimeMillis() - startMs;
        long gameCopyCopies = currentGame.getCopiedCount() - copiesBefore;
        long gameCopyViews = GameView.CREATED_COUNT.get() - viewsBefore;

        GameView copiedGameView = null;
        copiesBefore = currentGame.getCopiedCount();
        viewsBefore = GameView.CREATED_COUNT.get();
        startMs = System.currentTimeMillis();
        for (int i = 0; i < copyOperations; i++) {
            copiedGameView = new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
        }
        long viewDirectMs = System.currentTimeMillis() - startMs;
        long viewDirectCopies = currentGame.getCopiedCount() - copiesBefore;
        long viewDirectViews = GameView.CREATED_COUNT.get() - viewsBefore;

        // fake session without real threads manager
        GameSessionWatcher sessionWatcher = new GameSessionWatcher(null, UUID.randomUUID(), currentGame, false);
        sessionWatcher.startWithGameView(GameSessionWatcher.generateDefaultGameView(currentGame));
        copiesBefore = currentGame.getCopiedCount();
        viewsBefore = GameView.CREATED_COUNT.get();
        startMs = System.currentTimeMillis();
        for (int i = 0; i < copyOperations; i++) {
            copiedGameView = sessionWatcher.getGameView();
        }
        long viewOverSessionMs = System.currentTimeMillis() - startMs;
        long viewOverSessionCopies = currentGame.getCopiedCount() - copiesBefore;
        long viewOverSessionViews = GameView.CREATED_COUNT.get() - viewsBefore; // it's ok to create 1000 views cause it's test/game thread

        printResult("game copy", copyOperations, gameCopyMs, gameCopyCopies, gameCopyViews);
        printResult("view create direct", copyOperations, viewDirectMs, viewDirectCopies, viewDirectViews);
        printResult("view create over session", copyOperations, viewOverSessionMs,
                viewOverSessionCopies, viewOverSessionViews);
    }

    private static void printResult(String name, int operations, long totalMs, long copies, long views) {
        System.out.println(String.format(
                "%-26s operations: %5d, total: %6d ms, average: %8.3f ms, game copies: %5d, views: %5d",
                name, operations, totalMs, (double) totalMs / operations, copies, views));
    }
}
