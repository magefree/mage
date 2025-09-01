package mage.watchers.common;

import java.util.*;

import mage.MageObjectReference;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class ForetoldWatcher extends Watcher {

    private final Set<MageObjectReference> foretoldCards = new HashSet<>();
    // cards foretold - ZCC stored to reference from stack (exile zone plus 1)

    private final Map<UUID, Integer> playerForetellCount = new HashMap<>();
    // map of player id to number of times they foretell a card, cleared each turn


    public ForetoldWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CARD_FORETOLD) {
            return;
        }
        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            foretoldCards.add(new MageObjectReference(card, game, 1));
        }
        if (event.getFlag()) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null) {
                playerForetellCount.compute(player.getId(), CardUtil::setOrIncrementValue);
            }
        }
    }

    public boolean checkForetold(UUID sourceId, Game game) {
        return foretoldCards.contains(new MageObjectReference(sourceId, game));
    }

    public int getPlayerForetellCountThisTurn(UUID playerId) {
        return playerForetellCount.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        playerForetellCount.clear();
    }
}
