package mage.server.game;

import mage.game.Game;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.players.Player;
import mage.server.User;
import mage.server.managers.UserManager;
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

    private final UserManager userManager;
    protected final UUID userId;
    protected final Game game;
    protected boolean killed = false;
    protected final boolean isPlayer;

    public GameSessionWatcher(UserManager userManager, UUID userId, Game game, boolean isPlayer) {
        this.userManager = userManager;
        this.userId = userId;
        this.game = game;
        this.isPlayer = isPlayer;
    }

    public boolean init() {
        if (!killed) {
            Optional<User> user = userManager.getUser(userId);
            if (user.isPresent()) {
                user.get().fireCallback(new ClientCallback(ClientCallbackMethod.GAME_INIT, game.getId(), getGameView()));
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_UPDATE, game.getId(), getGameView())));
        }

    }

    public void inform(final String message) {
        if (!killed) {
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_INFORM, game.getId(), new GameClientMessage(getGameView(), null, message))));
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
            userManager.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_ERROR, game.getId(), message)));
        }
    }

    public void setKilled() {
        killed = true;
    }

    public GameView getGameView() {
        GameView gameView = new GameView(game.getState(), game, null, userId);
        processWatchedHands(game, userId, gameView);
        return gameView;
    }

    protected static void processWatchedHands(Game game, UUID userId, GameView gameView) {
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
