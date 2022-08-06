package mage.watchers.common;

import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CreatedTokenWatcher extends Watcher {

    // Player ID to Number of tokens created
    private final Map<UUID, Integer> playerMap = new HashMap<>();
    private final Map<UUID, Map<Class<? extends Token>, Integer>> tokenCreatedMap = new HashMap<>();

    public CreatedTokenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATED_TOKEN) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);

            tokenCreatedMap.putIfAbsent(event.getPlayerId(), new HashMap<>());
            Class<? extends Token> tokenClazz = ((Token) game.getPermanent(event.getTargetId())).getClass();
            Map<Class<? extends Token>, Integer> playersTokens = tokenCreatedMap.getOrDefault(event.getPlayerId(), new HashMap<>());
            playersTokens.compute(tokenClazz, CardUtil::setOrIncrementValue);
            tokenCreatedMap.put(event.getPlayerId(), playersTokens);
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
        tokenCreatedMap.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return getPlayerCount(playerId, game) > 0;
    }

    public static int getPlayerCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(CreatedTokenWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0);
    }

    public static int getTypeCreatedCountByPlayer(UUID playerId, Class<? extends Token> tokenCLazz, Game game) {
        return game
                .getState()
                .getWatcher(CreatedTokenWatcher.class)
                .tokenCreatedMap
                .getOrDefault(playerId, new HashMap<>())
                .getOrDefault(tokenCLazz, 0);
    }
}
