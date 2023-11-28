package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
}
