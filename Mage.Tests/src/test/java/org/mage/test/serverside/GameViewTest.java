package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.view.GameView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.HashSet;
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
        Assertions.assertEquals(0, currentGame.getOptions().bannedUsers.size());
        Game copiedGame = currentGame.copy();
        Assertions.assertEquals(0, copiedGame.getOptions().bannedUsers.size());
        //
        currentGame.getOptions().bannedUsers.add("123");
        Assertions.assertEquals(1, currentGame.getOptions().bannedUsers.size());
        copiedGame = currentGame.copy();
        Assertions.assertEquals(1, copiedGame.getOptions().bannedUsers.size());

        // normal hand
        GameView gameView = getGameView(playerA, userA);
        Assertions.assertNotNull(gameView);
        Assertions.assertNotNull(gameView.getMyHand());
        Assertions.assertEquals(1, gameView.getMyHand().size());
        Assertions.assertEquals("Forest", gameView.getMyHand().values().stream().findFirst().get().getName());
        Assertions.assertEquals(0, gameView.getLookedAt().size());
        Assertions.assertEquals(0, gameView.getRevealed().size());
        Assertions.assertEquals(0, gameView.getWatchedHands().size());

        // empty hand
        gameView = getGameView(playerB, userB);
        Assertions.assertNotNull(gameView);
        Assertions.assertNotNull(gameView.getMyHand());
        Assertions.assertEquals(0, gameView.getMyHand().size());
        Assertions.assertEquals(0, gameView.getLookedAt().size());
        Assertions.assertEquals(0, gameView.getRevealed().size());
        Assertions.assertEquals(0, gameView.getWatchedHands().size());

        // watcher hand
        gameView = getGameView(null, userWatcher);
        Assertions.assertNotNull(gameView);
        Assertions.assertNotNull(gameView.getMyHand());
        Assertions.assertEquals(0, gameView.getMyHand().size());
        Assertions.assertEquals(0, gameView.getLookedAt().size());
        Assertions.assertEquals(0, gameView.getRevealed().size());
        Assertions.assertEquals(0, gameView.getWatchedHands().size());

        // A gives access to hand for B and watcher
        playerA.addPermissionToShowHandCards(userB);
        playerA.addPermissionToShowHandCards(userWatcher);

        gameView = getGameView(playerA, userA);
        Assertions.assertEquals(0, gameView.getWatchedHands().size());
        gameView = getGameView(playerB, userB);
        Assertions.assertEquals(1, gameView.getWatchedHands().size());
        gameView = getGameView(null, userWatcher);
        Assertions.assertEquals(1, gameView.getWatchedHands().size());
    }
}
