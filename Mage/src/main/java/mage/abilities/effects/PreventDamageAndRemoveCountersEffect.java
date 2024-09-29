package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.SavedCounterRemovedValue;
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
 * This requires to add the Watcher from createWatcher() together with the Prevention Effect.
 *
 * @author Susucr
 */
public class PreventDamageAndRemoveCountersEffect extends PreventionEffectImpl {
    private final boolean thatMany; // If true, remove as many counters as damage prevent. If false, remove 1 counter.
    private final boolean whileHasCounter; // If true, the creature need a counter for the effect to be active.

    public static Watcher createWatcher() {
        return new PreventDamageAndRemoveCountersWatcher();
    }

    public PreventDamageAndRemoveCountersEffect(boolean thatMany, boolean whileHasCounter, boolean textFromIt) {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        this.thatMany = thatMany;
        this.whileHasCounter = whileHasCounter;
        staticText = "If damage would be dealt to {this}" +
                (whileHasCounter ? " while it has a +1/+1 counter on it" : "") +
                ", prevent that damage and remove " +
                (thatMany ? "that many +1/+1 counters" : "a +1/+1 counter") +
                " from " + (textFromIt ? "it" : "{this}");
    }

    /**
     * A specific wording for the old "Phantom [...]" not covered by the new wording using "and"
     */
    public PreventDamageAndRemoveCountersEffect withPhantomText() {
        staticText = "If damage would be dealt to {this}, prevent that damage. Remove a +1/+1 counter from {this}.";
        return this;
    }

    protected PreventDamageAndRemoveCountersEffect(final PreventDamageAndRemoveCountersEffect effect) {
        super(effect);
        this.thatMany = effect.thatMany;
        this.whileHasCounter = effect.whileHasCounter;
    }

    @Override
    public PreventDamageAndRemoveCountersEffect copy() {
        return new PreventDamageAndRemoveCountersEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damageAmount = event.getAmount();
        // Prevent the damage.
        // Note that removing counters does not care if prevention did work.
        // Ruling on Phantom Wurm for instance:
        // > If damage that can't be prevented is be dealt to Phantom Wurm,
        // > you still remove a counter even though the damage is dealt. (2021-03-19)
        preventDamageAction(event, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        PreventDamageAndRemoveCountersWatcher watcher = game.getState().getWatcher(PreventDamageAndRemoveCountersWatcher.class);
        if (permanent == null || watcher == null || damageAmount <= 0) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(source.getId(), source.getSourceObjectZoneChangeCounter(), game);
        int beforeCount = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (thatMany) {
            // Remove them.
            permanent.removeCounters(CounterType.P1P1.createInstance(damageAmount), source, game);
        } else if (!watcher.hadMORCounterRemovedThisBatch(mor)) {
            // Remove one.
            permanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
        }
        int amountRemovedThisTime = beforeCount - permanent.getCounters(game).getCount(CounterType.P1P1);
        int amountRemovedInTotal = amountRemovedThisTime;
        if (!watcher.hadMORCounterRemovedThisBatch(mor)) {
            watcher.addMOR(mor);
        } else {
            // Sum the previous added counter
            amountRemovedInTotal += (Integer) source.getEffects().get(0).getValue(SavedCounterRemovedValue.VALUE_KEY);
        }
        onDamagePrevented(event, source, game, amountRemovedInTotal, amountRemovedThisTime);
        return false;
    }

    /**
     * Meant to be Overriden if needs be.
     */
    protected void onDamagePrevented(GameEvent event, Ability source, Game game, int amountRemovedInTotal, int amountRemovedThisTime) {
        source.getEffects().setValue(SavedCounterRemovedValue.VALUE_KEY, amountRemovedInTotal);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (!super.applies(event, source, game)
                || permanent == null
                || !event.getTargetId().equals(source.getSourceId())) {
            return false;
        }
        if (whileHasCounter && !permanent.getCounters(game).containsKey(CounterType.P1P1)) {
            // If the last counter has already be removed for the same batch of prevention, we still want to prevent the damage.
            PreventDamageAndRemoveCountersWatcher watcher = game.getState().getWatcher(PreventDamageAndRemoveCountersWatcher.class);
            MageObjectReference mor = new MageObjectReference(source.getId(), source.getSourceObjectZoneChangeCounter(), game);
            return watcher != null && watcher.hadMORCounterRemovedThisBatch(mor);
        }
        return true;
    }
}

class PreventDamageAndRemoveCountersWatcher extends Watcher {

    // We keep a very short-lived set of which PreventDamageAndRemoveCountersEffect caused
    // +1/+1 to get removed during the current damage batch.
    private final Set<MageObjectReference> morRemovedCounterThisDamageBatch = new HashSet<>();

    PreventDamageAndRemoveCountersWatcher() {
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