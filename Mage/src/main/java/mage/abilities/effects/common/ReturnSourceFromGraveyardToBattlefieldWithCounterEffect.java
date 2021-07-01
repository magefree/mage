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
        super(tapped);
        this.counter = counter;
        setText();
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

    private void setText() {
        StringBuilder sb = new StringBuilder("return it to the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (ownerControl) {
            sb.append(" under its owner's control");
        }
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
        staticText = sb.toString();
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
