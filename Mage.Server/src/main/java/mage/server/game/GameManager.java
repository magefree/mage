package mage.server.game;

import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.Game;
import mage.game.GameOptions;
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
public enum GameManager {
    instance;

    private final ConcurrentMap<UUID, GameController> gameControllers = new ConcurrentHashMap<>();
    private final ReadWriteLock gameControllersLock = new ReentrantReadWriteLock();

    public UUID createGameSession(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId, GameOptions gameOptions) {
        GameController gameController = new GameController(game, userPlayerMap, tableId, choosingPlayerId, gameOptions);
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

    public void joinGame(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.join(userId);
        }
    }

    public Optional<UUID> getChatId(UUID gameId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return Optional.of(gameController.getChatId());
        }
        return Optional.empty();
    }

    public void sendPlayerUUID(UUID gameId, UUID userId, UUID data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerUUID(userId, data);
        }
    }

    public void sendPlayerString(UUID gameId, UUID userId, String data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerString(userId, data);
        }
    }

    public void sendPlayerManaType(UUID gameId, UUID playerId, UUID userId, ManaType data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerManaType(userId, playerId, data);
        }
    }

    public void sendPlayerBoolean(UUID gameId, UUID userId, Boolean data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerBoolean(userId, data);
        }
    }

    public void sendPlayerInteger(UUID gameId, UUID userId, Integer data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerInteger(userId, data);
        }
    }

    public void quitMatch(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.quitMatch(userId);
        }
    }

    public void sendPlayerAction(PlayerAction playerAction, UUID gameId, UUID userId, Object data) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.sendPlayerAction(playerAction, userId, data);
        }
    }

    public boolean watchGame(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.watch(userId);
        }
        return false;
    }

    public void stopWatching(UUID gameId, UUID userId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.stopWatching(userId);
        }
    }

    public void cheat(UUID gameId, UUID userId, UUID playerId, DeckCardLists deckList) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            gameController.cheat(userId, playerId, deckList);
        }
    }

    public boolean cheat(UUID gameId, UUID userId, UUID playerId, String cardName) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.cheat(userId, playerId, cardName);
        }
        return false;
    }

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

    public boolean saveGame(UUID gameId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.saveGame();
        }
        return false;
    }

    public GameView getGameView(UUID gameId, UUID playerId) {
        GameController gameController = getGameControllerSafe(gameId);
        if (gameController != null) {
            return gameController.getGameView(playerId);
        }
        return null;
    }

    public int getNumberActiveGames() {
        return getGameController().size();
    }

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
