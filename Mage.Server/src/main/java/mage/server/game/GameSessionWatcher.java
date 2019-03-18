

package mage.server.game;

import mage.game.Game;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.players.Player;
import mage.server.User;
import mage.server.UserManager;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.SimpleCardsView;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameSessionWatcher {

    protected static final Logger logger = Logger.getLogger(GameSessionWatcher.class);

    protected final UUID userId;
    protected final Game game;
    protected boolean killed = false;
    protected final boolean isPlayer;

    public GameSessionWatcher(UUID userId, Game game, boolean isPlayer) {
        this.userId = userId;
        this.game = game;
        this.isPlayer = isPlayer;
    }

    public boolean init() {
        if (!killed) {
            Optional<User> user = UserManager.instance.getUser(userId);
            if (user.isPresent()) {
                user.get().fireCallback(new ClientCallback(ClientCallbackMethod.GAME_INIT, game.getId(), getGameView()));
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_UPDATE, game.getId(), getGameView())));
        }

    }

    public void inform(final String message) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_INFORM, game.getId(), new GameClientMessage(getGameView(), message))));
        }

    }

    public void informPersonal(final String message) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_INFORM_PERSONAL, game.getId(), new GameClientMessage(getGameView(), message))));
        }

    }

    public void gameOver(final String message) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> {
                user.removeGameWatchInfo(game.getId());
                user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_OVER, game.getId(), message));
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
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_ERROR, game.getId(), message)));

        }
    }

    public void setKilled() {
        killed = true;
    }

    public GameView getGameView() {
        GameView gameView = new GameView(game.getState(), game, null, userId);
        processWatchedHands(userId, gameView);
        return gameView;

    }

    protected void processWatchedHands(UUID userId, GameView gameView) {
        Map<String, SimpleCardsView> handCards = new HashMap<>();
        for (Player player : game.getPlayers().values()) {
            if (player.hasUserPermissionToSeeHand(userId)) {
                handCards.put(player.getName(), new SimpleCardsView(player.getHand().getCards(game), true));
                gameView.setWatchedHands(handCards);
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
