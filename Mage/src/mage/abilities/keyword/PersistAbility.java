package mage.abilities.keyword;

import mage.Constants;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class PersistAbility extends DiesTriggeredAbility {
    public PersistAbility() {
        super(new PersistEffect());
        this.addEffect(new ReturnSourceFromGraveyardToBattlefieldEffect());
    }

    public PersistAbility(final PersistAbility ability) {
        super(ability);
    }

    @Override
    public PersistAbility copy() {
        return new PersistAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (p.getCounters().getCount(CounterType.M1M1) == 0) {
                game.getState().setValue(new StringBuilder("persist").append(getSourceId()).toString(), new FixedTarget(p.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Persist <i>(When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)</i>";
    }
}

class PersistEffect extends OneShotEffect<PersistEffect> {

    public PersistEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    public PersistEffect(final PersistEffect effect) {
        super(effect);
    }

    @Override
    public PersistEffect copy() {
        return new PersistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new PersistReplacementEffect(), source);
        return false;
    }
}

class PersistReplacementEffect extends ReplacementEffectImpl<PersistReplacementEffect> {

    PersistReplacementEffect() {
        super(Duration.OneUse, Outcome.UnboostCreature, false);
        selfScope = true;
        staticText = "return it to the battlefield under its owner's control with a -1/-1 counter on it";
    }

    PersistReplacementEffect(final PersistReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PersistReplacementEffect copy() {
        return new PersistReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(), game);
        }
        used = true;
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(source.getSourceId())) {
            Object fixedTarget = game.getState().getValue(new StringBuilder("persist").append(source.getSourceId()).toString());
            if (fixedTarget instanceof FixedTarget && ((FixedTarget) fixedTarget).getFirst(game, source).equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
