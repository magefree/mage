package mage.server.game;

import mage.game.Game;
import mage.game.GameState;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.server.managers.ManagerFactory;
import mage.view.GameView;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReplaySession implements GameCallback {

    private final ManagerFactory managerFactory;
    private final GameReplay replay;
    protected final UUID userId;

    ReplaySession(ManagerFactory managerFactory, UUID gameId, UUID userId) {
        this.managerFactory = managerFactory;
        this.replay = new GameReplay(gameId);
        this.userId = userId;
    }

    public void replay() {
        replay.start();
        managerFactory.userManager().getUser(userId).ifPresent(user ->
                user.fireCallback(new ClientCallback(ClientCallbackMethod.REPLAY_INIT, replay.getGame().getId(), new GameView(replay.next(), replay.getGame(), null, null))));

    }

    public void stop() {
        gameResult("stopped replay");
    }

    public synchronized void next() {
        updateGame(replay.next(), replay.getGame());
    }

    public synchronized void next(int moves) {
        for (int i = 0; i < moves; i++) {
            replay.next();
        }
        updateGame(replay.next(), replay.getGame());
    }

    public synchronized void previous() {
        updateGame(replay.previous(), replay.getGame());
    }

    @Override
    public void gameResult(final String result) {
        managerFactory.userManager().getUser(userId).ifPresent(user ->
                user.fireCallback(new ClientCallback(ClientCallbackMethod.REPLAY_DONE, replay.getGame().getId(), result)));

        managerFactory.replayManager().endReplay(replay.getGame().getId(), userId);
    }

    private void updateGame(final GameState state, Game game) {
        if (state == null) {
            gameResult("game ended");
        } else {
            managerFactory.userManager().getUser(userId).ifPresent(user ->
                    user.fireCallback(new ClientCallback(ClientCallbackMethod.REPLAY_UPDATE, replay.getGame().getId(), new GameView(state, game, null, null))));

        }
    }

}
