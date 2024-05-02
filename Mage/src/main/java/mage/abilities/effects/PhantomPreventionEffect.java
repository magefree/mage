package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 * This requires to add the PhantomPreventionWatcher
 *
 * @author Susucr
 */
public class PhantomPreventionEffect extends PreventionEffectImpl {

    public static PhantomPreventionWatcher createWatcher() {
        return new PhantomPreventionWatcher();
    }

    public PhantomPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to {this}, prevent that damage. Remove a +1/+1 counter from {this}";
    }

    protected PhantomPreventionEffect(final PhantomPreventionEffect effect) {
        super(effect);
    }

    @Override
    public PhantomPreventionEffect copy() {
        return new PhantomPreventionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        PhantomPreventionWatcher watcher = game.getState().getWatcher(PhantomPreventionWatcher.class);
        if (permanent != null && watcher != null) {
            MageObjectReference mor = new MageObjectReference(source.getId(), source.getSourceObjectZoneChangeCounter(), game);
            if (!watcher.hadMORCounterRemovedThisBatch(mor)) {
                watcher.addMOR(mor);
                if (permanent.getCounters(game).containsKey(CounterType.P1P1)) {
                    StringBuilder sb = new StringBuilder(permanent.getName()).append(": ");
                    permanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
                    sb.append("Removed a +1/+1 counter ");
                    game.informPlayers(sb.toString());
                }
            }
        }

        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }
}

class PhantomPreventionWatcher extends Watcher {

    // We keep a very short-lived set of which PhantomPreventionEffect caused
    // +1/+1 to get removed during the current damage batch.
    private final Set<MageObjectReference> morRemovedCounterThisDamageBatch = new HashSet<>();

    PhantomPreventionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // This watcher resets every time a Damage Batch could have fired (even if all damage was prevented)
        if (event.getType() != GameEvent.EventType.DAMAGED_BATCH_COULD_HAVE_FIRED) {
            return;
        }
        morRemovedCounterThisDamageBatch.clear();
    }

    @Override
    public void reset() {
        super.reset();
        morRemovedCounterThisDamageBatch.clear();
    }

    boolean hadMORCounterRemovedThisBatch(MageObjectReference mor) {
        return morRemovedCounterThisDamageBatch.contains(mor);
    }

    void addMOR(MageObjectReference mor) {
        morRemovedCounterThisDamageBatch.add(mor);
    }
}