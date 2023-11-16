package mage.watchers.common;

import mage.Mana;
import mage.abilities.Ability;
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
 * <p>
 * Resets each turn
 *
 * @author LevelX2
 */
public class ManaSpentToCastWatcher extends Watcher {

    private final Map<UUID, Mana> manaMap = new HashMap<>();

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
                }
                return;
            case ZONE_CHANGE:
                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                    manaMap.remove(event.getTargetId());
                }
        }
    }

    public Mana getLastManaPayment(UUID sourceId) {
        return manaMap.getOrDefault(sourceId, null);
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
    }
}
