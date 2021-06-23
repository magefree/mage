package mage.watchers.common;

import mage.Mana;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Watcher saves the mana that was spent to cast a spell
 * automatically added in each game
 *
 * @author LevelX2
 */
public class ManaSpentToCastWatcher extends Watcher {

    private final Map<UUID, Mana> manaMap = new HashMap<>();
    private final Map<UUID, Integer> xValueMap = new HashMap<>();

    public ManaSpentToCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // There was a check for the from zone being the hand, but that should not matter
        switch (event.getType()) {
            case SPELL_CAST:
                Spell spell = (Spell) game.getObject(event.getTargetId());
                if (spell != null) {
                    manaMap.put(spell.getSourceId(), spell.getSpellAbility().getManaCostsToPay().getUsedManaToPay());
                    xValueMap.put(spell.getSourceId(), spell.getSpellAbility().getManaCostsToPay().getX());
                }
                return;
            case ZONE_CHANGE:
                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                    manaMap.remove(event.getSourceId());
                    xValueMap.remove(event.getSourceId());
                }
        }
    }

    public Mana getAndResetLastPayment(UUID sourceId) {
        return manaMap.getOrDefault(sourceId, null);
    }

    public int getAndResetLastXValue(UUID sourceId) {
        return xValueMap.getOrDefault(sourceId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
        xValueMap.clear();
    }
}
