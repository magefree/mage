package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;

/**
 *
 * @author weirddan455
 */
public class ControlledModifiedCreatureAsSpellCastWatcher extends Watcher {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public ControlledModifiedCreatureAsSpellCastWatcher() {
        super(WatcherScope.GAME);
    }

    private final HashMap<MageObjectReference, Boolean> spellsCastCondition = new HashMap<>();

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null) {
                MageObjectReference mor = new MageObjectReference(spell, game);
                Boolean condition = !game.getBattlefield().getAllActivePermanents(filter, spell.getControllerId(), game).isEmpty();
                spellsCastCondition.put(mor, condition);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellsCastCondition.clear();
    }

    public boolean checkModifiedCondition(MageObjectReference mor) {
        return spellsCastCondition.getOrDefault(mor, false);
    }
}
