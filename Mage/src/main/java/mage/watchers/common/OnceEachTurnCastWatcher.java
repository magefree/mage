package mage.watchers.common;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author xenohedron
 */
public class OnceEachTurnCastWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> usedFrom = new HashMap<>();

    /**
     * For abilities that permit the casting of a spell from not own hand zone once each turn (per player)
     */
    public OnceEachTurnCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.getPlayerId() != null
                && event.hasApprovingIdentifier(MageIdentifier.OnceEachTurnCastWatcher)) {
            usedFrom.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                    .add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(UUID playerId, MageObjectReference mor) {
        return usedFrom.getOrDefault(playerId, Collections.emptySet()).contains(mor);
    }

    public static Hint getHint() {
        return OnceEachTurnCastHint.instance;
    }

}

enum OnceEachTurnCastHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        OnceEachTurnCastWatcher watcher = game.getState().getWatcher(OnceEachTurnCastWatcher.class);
        if (watcher != null) {
            boolean used = watcher.isAbilityUsed(ability.getControllerId(), new MageObjectReference(ability.getSourceId(), game));
            if (used) {
                Player player = game.getPlayer(ability.getControllerId());
                if (player != null) {
                    return "A spell has been cast by " + player.getLogName() + " with {this} this turn.";
                }
            }
        }
        return "";
    }

    @Override
    public OnceEachTurnCastHint copy() {
        return this;
    }
}
