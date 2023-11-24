package mage.server.game;

import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.Game;
import mage.game.GameOptions;
import mage.server.managers.GameManager;
import mage.server.managers.ManagerFactory;
import mage.view.GameView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameManagerImpl implements GameManager {

    private final ManagerFactory managerFactory;
    private final ConcurrentMap<UUID, GameController> gameControllers = new ConcurrentHashMap<>();
    private final ReadWriteLock gameControllersLock = new ReentrantReadWriteLock();

    public GameManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public UUID createGameSession(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId, GameOptions gameOptions) {
        GameController gameController = new GameController(managerFactory, game, userPlayerMap, tableId, choosingPlayerId, gameOptions);
        final Lock w = gameControllersLock.writeLock();
        w.lock();
        try {
            gameControllers.put(game.getId(), gameController);
        } finally {
            w.unlock();
        }
        return gameController.getSessionId();
    }

    private GameController getGameControllerSafe(UUID gameId) {
        final Lock r = gameControllersLock.readLock();
        r.lock();
        try {
            return gameControllers.get(gameId);
        } finally {
            r.unlock();
        }
    }

    @Override
    public void joinGame(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.join(userId);
        }
    }

    @Override
    public Optional<UUID> getChatId(UUID gameId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return Optional.of(gameController.getChatId());
        }
        return Optional.empty();
    }

    @Override
    public void sendPlayerUUID(UUID gameId, UUID userId, UUID data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerUUID(userId, data);
        }
    }

    @Override
    public void sendPlayerString(UUID gameId, UUID userId, String data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerString(userId, data);
        }
    }

    @Override
    public void sendPlayerManaType(UUID gameId, UUID playerId, UUID userId, ManaType data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerManaType(userId, playerId, data);
        }
    }

    @Override
    public void sendPlayerBoolean(UUID gameId, UUID userId, Boolean data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerBoolean(userId, data);
        }
    }

    @Override
    public void sendPlayerInteger(UUID gameId, UUID userId, Integer data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerInteger(userId, data);
        }
    }

    @Override
    public void quitMatch(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.quitMatch(userId);
        }
    }

    @Override
    public void sendPlayerAction(PlayerAction playerAction, UUID gameId, UUID userId, Object data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerAction(playerAction, userId, data);
        }
    }

    @Override
    public boolean watchGame(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.watch(userId);
        }
        return false;
    }

    @Override
    public void stopWatching(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.stopWatching(userId);
        }
    }

    @Override
    public void cheatShow(UUID gameId, UUID userId, UUID playerId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.cheatShow(playerId);
        }
    }

    @Override
    public void removeGame(UUID gameId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.cleanUp();
            final Lock w = gameControllersLock.writeLock();
            w.lock();
            try {
                gameControllers.remove(gameId);
            } finally {
                w.unlock();
            }
        }
    }

    @Override
    public boolean saveGame(UUID gameId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.saveGame();
        }
        return false;
    }

    @Override
    public GameView getGameView(UUID gameId, UUID playerId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.getGameView(playerId);
        }
        return null;
    }

    @Override
    public int getNumberActiveGames() {
        return getGameController().size();
    }

    @Override
    public Map<UUID, GameController> getGameController() {
        Map<UUID, GameController> newControllers = new HashMap<>();
        final Lock r = gameControllersLock.readLock();
        r.lock();
        try {
            newControllers.putAll(gameControllers);
        } finally {
            r.unlock();
        }
        return newControllers;
    }
}
