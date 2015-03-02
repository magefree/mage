package mage.abilities.keyword;


import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;

/**
 * @author Loki
 */
public class UndyingAbility extends DiesTriggeredAbility {
    
    public UndyingAbility() {
        super(new UndyingEffect());
        this.addEffect(new ReturnSourceFromGraveyardToBattlefieldEffect(false, true));
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
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (!permanent.getCounters().containsKey(CounterType.P1P1) || permanent.getCounters().getCount(CounterType.P1P1) == 0) {
                game.getState().setValue("undying" + getSourceId(),permanent.getId());
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

class UndyingEffect extends OneShotEffect {

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

class UndyingReplacementEffect extends ReplacementEffectImpl {

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
            game.getState().setValue("undying" + source.getSourceId(), null);
            permanent.addCounters(CounterType.P1P1.createInstance(), game);
        }
        used = true;
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            // Check if undying condition is true
            UUID targetId = (UUID) game.getState().getValue("undying" + source.getSourceId());
            if (targetId != null && targetId.equals(source.getSourceId())) {
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
