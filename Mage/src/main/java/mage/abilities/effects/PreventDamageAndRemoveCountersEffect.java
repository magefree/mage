package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author antoni-g
 */
public class PreventDamageAndRemoveCountersEffect extends PreventionEffectImpl {
    private final boolean thatMany;
    private final boolean whileHasCounter;

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
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        preventDamageAction(event, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        if (!thatMany) {
            damage = 1;
        }
        permanent.removeCounters(CounterType.P1P1.createInstance(damage), source, game); //MTG ruling (this) loses counters even if the damage isn't prevented
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return super.applies(event, source, game)
                && permanent != null
                && event.getTargetId().equals(source.getSourceId())
                && (!whileHasCounter || permanent.getCounters(game).containsKey(CounterType.P1P1));
    }
}
