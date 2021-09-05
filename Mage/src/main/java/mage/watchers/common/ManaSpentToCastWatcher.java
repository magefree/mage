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
    private final Map<UUID, Integer> xValueMap = new HashMap<>();
    private final Map<UUID, Integer> xValueMapLong = new HashMap<>(); // do not reset, keep until game end

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
                    xValueMapLong.put(spell.getSourceId(), spell.getSpellAbility().getManaCostsToPay().getX());
                }
                return;
            case ZONE_CHANGE:
                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                    manaMap.remove(event.getTargetId());
                    xValueMap.remove(event.getTargetId());
                    xValueMapLong.remove(event.getTargetId());
                }
        }
    }

    public Mana getLastManaPayment(UUID sourceId) {
        return manaMap.getOrDefault(sourceId, null);
    }

    /**
     * Return X value for casted spell or permanents
     *
     * @param source
     * @param useLongSource - use X value that keeps until end of game (for info only)
     * @return
     */
    public int getLastXValue(Ability source, boolean useLongSource) {
        Map<UUID, Integer> xSource = useLongSource ? this.xValueMapLong : this.xValueMap;
        if (xSource.containsKey(source.getSourceId())) {
            // cast normal way
            return xSource.get(source.getSourceId());
        } else {
            // put to battlefield without cast (example: copied spell must keep announced X)
            return source.getManaCostsToPay().getX();
        }
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
        xValueMap.clear();
        // xValueMapLong.clear(); // must keep until game end, so don't clear between turns
    }
}
