package mage.watchers.common;

import mage.MageObjectReference;
import mage.Mana;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;


/**
 * Watcher saves the mana that was spent to cast a spell
 * automatically added in each game
 * <p>
 * Resets each turn
 *
 * @author LevelX2
 */
public class ManaSpentToCastWatcher extends Watcher {

    private final Map<MageObjectReference, Mana> manaMap = new HashMap<>();

    public ManaSpentToCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // There was a check for the from zone being the hand, but that should not matter
        if (event.getType() == GameEvent.EventType.SPELL_CAST){
                Spell spell = (Spell) game.getObject(event.getTargetId());
                if (spell != null) {
                    manaMap.put(new MageObjectReference(spell.getSpellAbility()),
                            spell.getSpellAbility().getManaCostsToPay().getUsedManaToPay());
                }
        }
    }

    public Mana getManaPayment(MageObjectReference source) {
        return manaMap.getOrDefault(source, null);
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
    }
}
