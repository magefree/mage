package mage.abilities.keyword;

import mage.constants.Duration;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class UndyingAbility extends DiesTriggeredAbility {
    
    public UndyingAbility() {
        super(new UndyingEffect());
        this.addEffect(new ReturnSourceFromGraveyardToBattlefieldEffect());
    }

    public UndyingAbility(final UndyingAbility ability) {
        super(ability);
    }

    @Override
    public DiesTriggeredAbility copy() {
        return new UndyingAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (!p.getCounters().containsKey(CounterType.P1P1) || p.getCounters().getCount(CounterType.P1P1) == 0) {
                game.getState().setValue(new StringBuilder("undying").append(getSourceId()).toString(), new FixedTarget(p.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Undying <i>(When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)</i>";
    }
}

class UndyingEffect extends OneShotEffect<UndyingEffect> {

    public UndyingEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    public UndyingEffect(final UndyingEffect effect) {
        super(effect);
    }

    @Override
    public UndyingEffect copy() {
        return new UndyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new UndyingReplacementEffect(), source);
        return false;
    }
}

class UndyingReplacementEffect extends ReplacementEffectImpl<UndyingReplacementEffect> {

    UndyingReplacementEffect() {
        super(Duration.OneUse, Outcome.BoostCreature, false);
        selfScope = true;
        staticText = "return it to the battlefield under its owner's control with a +1/+1 counter on it";
    }

    UndyingReplacementEffect(final UndyingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public UndyingReplacementEffect copy() {
        return new UndyingReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), game);
        }
        used = true;
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(source.getSourceId())) {
            Object fixedTarget = game.getState().getValue(new StringBuilder("undying").append(source.getSourceId()).toString());
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
