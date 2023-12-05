package mage.abilities.effects.common.counter;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author notgreat
 */
public class AddCounterEnteringCreature extends ReplacementEffectImpl {

    private final MageObjectReference mor;
    private final Counter counters;

    public AddCounterEnteringCreature(MageObjectReference mor, Counter counters, Outcome outcome, String text) {
        super(Duration.EndOfTurn, outcome);
        this.staticText = "That creature enters the battlefield with "+text+" on it";
        this.mor = mor;
        this.counters = counters;
    }
    public AddCounterEnteringCreature(MageObjectReference mor, Counter counters, Outcome outcome){
        this(mor, counters, outcome, counters.getDescription());
    }

    /**
     * defaults to a single +1/+1 counter
     */
    public AddCounterEnteringCreature(MageObjectReference mor){
        this(mor, CounterType.P1P1.createInstance(), Outcome.BoostCreature, "an additional +1/+1 counter");
    }

    private AddCounterEnteringCreature(final AddCounterEnteringCreature effect) {
        super(effect);
        this.mor = effect.mor;
        this.counters = effect.counters;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && mor.refersTo(permanent, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(counters, source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public AddCounterEnteringCreature copy() {
        return new AddCounterEnteringCreature(this);
    }
}
