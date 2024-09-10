package mage.server.managers;

import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.Game;
import mage.game.GameOptions;
import mage.server.game.GameController;
import mage.view.GameView;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface GameManager {
    UUID createGameSession(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId, GameOptions gameOptions);

    void joinGame(UUID gameId, UUID userId);

    Optional<UUID> getChatId(UUID gameId);

    void sendPlayerUUID(UUID gameId, UUID userId, UUID data);

    void sendPlayerString(UUID gameId, UUID userId, String data);

    void sendPlayerManaType(UUID gameId, UUID playerId, UUID userId, ManaType data);

    void sendPlayerBoolean(UUID gameId, UUID userId, Boolean data);

    void sendPlayerInteger(UUID gameId, UUID userId, Integer data);

    void quitMatch(UUID gameId, UUID userId);

    void sendPlayerAction(PlayerAction playerAction, UUID gameId, UUID userId, Object data);

    boolean watchGame(UUID gameId, UUID userId);

    void stopWatching(UUID gameId, UUID userId);

    void cheatShow(UUID gameId, UUID userId, UUID playerId);

    void removeGame(UUID gameId);

    boolean saveGame(UUID gameId);

    GameView getGameView(UUID gameId, UUID playerId);

    int getNumberActiveGames();

    Map<UUID, GameController> getGameController();
}
