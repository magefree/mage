package mage.watchers.common;

import mage.abilities.keyword.WarpAbility;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class VoidWatcher extends Watcher {

    // need to track separately for each player as it needs to be in range
    private final Set<UUID> players = new HashSet<>();

    public VoidWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                Spell spell = game.getSpell(event.getTargetId());
                if (spell != null && spell.getSpellAbility() instanceof WarpAbility) {
                    players.addAll(game.getState().getPlayersInRange(spell.getControllerId(), game));
                }
                return;
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (Zone.BATTLEFIELD.match(zEvent.getFromZone())
                        && zEvent.getTarget() != null
                        && !zEvent.getTarget().isLand(game)) {
                    players.addAll(game.getState().getPlayersInRange(zEvent.getTarget().getControllerId(), game));
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(VoidWatcher.class)
                .players
                .contains(playerId);
    }
}
