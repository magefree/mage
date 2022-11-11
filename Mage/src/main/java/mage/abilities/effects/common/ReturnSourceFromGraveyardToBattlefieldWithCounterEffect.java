package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author weirddan455
 */
public class ReturnSourceFromGraveyardToBattlefieldWithCounterEffect extends ReturnSourceFromGraveyardToBattlefieldEffect {

    private final Counter counter;

    public ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(Counter counter, boolean tapped) {
        this(counter, tapped, true, false, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(Counter counter, boolean tapped, boolean ownerControl, boolean haste, boolean attacking) {
        super(tapped, ownerControl, haste, attacking);
        this.counter = counter;
        this.staticText = setText();
    }

    private ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(final ReturnSourceFromGraveyardToBattlefieldWithCounterEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public ReturnSourceFromGraveyardToBattlefieldWithCounterEffect copy() {
        return new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AddCounterSourceReplacementEffect counterEffect = new AddCounterSourceReplacementEffect(counter);
        game.addEffect(counterEffect, source);
        return super.apply(game, source);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder(staticText);
        sb.append(" with ");
        if (counter.getCount() == 1) {
            sb.append('a');
        } else {
            sb.append(counter.getCount());
        }
        sb.append(' ');
        sb.append(counter.getName());
        sb.append(" counter");
        if (counter.getCount() != 1) {
            sb.append('s');
        }
        sb.append(" on it");
        return sb.toString();
    }
}

class AddCounterSourceReplacementEffect extends ReplacementEffectImpl {

    private final Counter counter;

    public AddCounterSourceReplacementEffect(Counter counter) {
        super(Duration.EndOfStep, Outcome.BoostCreature);
        this.counter = counter;
    }

    private AddCounterSourceReplacementEffect(final AddCounterSourceReplacementEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public AddCounterSourceReplacementEffect copy() {
        return new AddCounterSourceReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        creature.addCounters(counter.copy(), source.getControllerId(), source, game, event.getAppliedEffects());
        discard();
        return false;
    }
}
