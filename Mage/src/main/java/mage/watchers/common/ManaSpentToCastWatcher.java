package mage.watchers.common;

import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
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

    private final Map<MageObjectReference, Mana> manaMap = new HashMap<>();

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
                    manaMap.put(new MageObjectReference(spell.getSpellAbility()), spell.getSpellAbility().getManaCostsToPay().getUsedManaToPay());
                }
                return;
            case AT_END_OF_TURN:
                manaMap.clear();
        }
    }

    public Mana getLastManaPayment(MageObjectReference source) {return manaMap.getOrDefault(source, null);}

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
    }
}
