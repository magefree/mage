package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class SpellsCastNotFromHandWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    public SpellsCastNotFromHandWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.getFromZone() != Zone.HAND) {
            map.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    public static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(SpellsCastNotFromHandWatcher.class)
                .map
                .getOrDefault(playerId, 0);
    }
}
