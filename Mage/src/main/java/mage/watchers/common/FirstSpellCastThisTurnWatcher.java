package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 */
public class FirstSpellCastThisTurnWatcher extends Watcher {

    private final Map<UUID, UUID> playerFirstSpellCast = new HashMap<>();
    private final Map<UUID, UUID> playerFirstCastSpell = new HashMap<>();

    public FirstSpellCastThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case CAST_SPELL:
                Spell spell = (Spell) game.getObject(event.getTargetId());
                if (spell != null && !playerFirstSpellCast.containsKey(spell.getControllerId())) {
                    if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                        playerFirstSpellCast.put(spell.getControllerId(), spell.getId());
                    } else if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                        playerFirstCastSpell.put(spell.getControllerId(), spell.getId());
                    }
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerFirstSpellCast.clear();
        playerFirstCastSpell.clear();
    }

    public UUID getIdOfFirstCastSpell(UUID playerId) {
        return playerFirstSpellCast.getOrDefault(playerId, playerFirstCastSpell.get(playerId));
    }
}
