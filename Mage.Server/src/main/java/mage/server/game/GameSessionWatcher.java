package mage.server.game;

import mage.game.Game;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.players.Player;
import mage.server.User;
import mage.server.managers.UserManager;
import mage.util.ThreadUtils;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.SimpleCardsView;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameSessionWatcher {

    protected static final Logger logger = Logger.getLogger(GameSessionWatcher.class);

    private final UserManager userManager;
    protected final UUID userId;
    protected final Game game;
    protected boolean killed = false;
    protected final boolean isPlayer;

    protected GameView lastGameView = null; // cached game view for non-game threads

    public GameSessionWatcher(UserManager userManager, UUID userId, Game game, boolean isPlayer) {
        this.userManager = userManager;
        this.userId = userId;
        this.game = game;
        this.isPlayer = isPlayer;
    }

    public boolean init() {
        return init(true);
    }

    /**
     * @param sendNow send it or wait next tick, e.g. fill and flush, see GameController.startGame as example
     */
    public boolean init(boolean sendNow) {
        if (!killed) {
            Optional<User> user = userManager.getUser(userId);
            if (user.isPresent()) {
                ClientCallback call = new ClientCallback(ClientCallbackMethod.GAME_INIT, game.getId(), getGameView());
                if (sendNow) {
                    user.get().fireCallback(call);
                } else {
                    user.get().addCallback(call);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Sends queued messages in a separate thread, a caller is not blocked
     */
    public void flushCallbacks() {
        if (!killed) {
            userManager.getUser(userId).ifPresent(User::flushCallbacksQueue);
        }
    }

    public void update() {
        if (!killed) {
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_UPDATE, game.getId(), getGameView())));
        }

    }

    public void inform(final String message) {
        if (!killed) {
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_UPDATE_AND_INFORM, game.getId(), new GameClientMessage(getGameView(), null, message))));
        }
    }

    public void informPersonal(final String message) {
        if (!killed) {
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_INFORM_PERSONAL, game.getId(), new GameClientMessage(getGameView(), null, message))));
        }

    }

    public void gameOver(final String message) {
        if (!killed) {
            userManager.getUser(userId).ifPresent(user -> {
                user.removeGameWatchInfo(game.getId());
                user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_OVER, game.getId(), new GameClientMessage(getGameView(), null, message)));
            });
        }
    }

    /**
     * Cleanup if Session ends
     */
    public void cleanUp() {

    }

    public void gameError(final String message) {
        if (!killed) {
            // TODO: implement shared link here (store game state, full battlefield info, history/logs, etc with shared link to web page)
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_ERROR, game.getId(), message)));
        }
    }

    public void setKilled() {
        killed = true;
    }

    /**
     * Bootstrap with default game view, so all non-game thread calls will be safe like start/watch
     */
    public void startWithGameView(GameView defaultGameView) {
        if (GameView.ENABLE_GAME_VIEW_CACHE) {
            this.lastGameView = defaultGameView;
        }
    }

    static public GameView generateDefaultGameView(Game game) {
        return new GameView(game.getState(), game, null, UUID.randomUUID());
    }

    public GameView getGameView() {
        // game view calculation can take some time and can be called from non-game thread,
        // so recalculate game view by game thread only to protect from ConcurrentModificationException
        // warning, don't forget to sync logci with GameSessionWatcher and GameSessionPlayer
        if (this.lastGameView != null && !ThreadUtils.isRunGameThread()) {
            return this.lastGameView;
        }

        // short processing for the watcher
        GameView gameView = new GameView(game.getState(), game, null, userId);
        processWatchedHands(game, userId, gameView);

        if (GameView.ENABLE_GAME_VIEW_CACHE) {
            this.lastGameView = gameView;
        }

        return gameView;
    }

    protected static void processWatchedHands(Game game, UUID userId, GameView gameView) {
        gameView.getWatchedHands().clear();
        for (Player player : game.getPlayers().values()) {
            if (player.hasUserPermissionToSeeHand(userId)) {
                gameView.getWatchedHands().put(player.getName(), new SimpleCardsView(player.getHand().getCards(game), true));
            }
        }
    }

    public GameEndView getGameEndView(UUID playerId, Table table) {
        return new GameEndView(game.getState(), game, playerId, table);
    }

    public boolean isPlayer() {
        return isPlayer;
    }

}
