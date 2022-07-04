package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author andyfries
 */

public final class JinxedChoker extends CardImpl {

    public JinxedChoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of your end step, target opponent gains control of Jinxed Choker and puts a charge counter on it.
        Ability endStepAbility = new BeginningOfYourEndStepTriggeredAbility(new JinxedChokerChangeControllerEffect(), false);
        endStepAbility.addEffect(new JinxedChokerAddCounterEffect());
        endStepAbility.addTarget(new TargetOpponent());
        this.addAbility(endStepAbility);

        // At the beginning of your upkeep, Jinxed Choker deals damage to you equal to the number of charge counters on it.
        Ability upkeepAbility = new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DamageControllerEffect(new JinxedChokerDynamicValue()), false);
        this.addAbility(upkeepAbility);

        // {3}: Put a charge counter on Jinxed Choker or remove one from it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JinxedChokerCounterEffect(), new ManaCostsImpl<>("{3}"));
        this.addAbility(ability);
    }

    private JinxedChoker(final JinxedChoker card) {
        super(card);
    }

    @Override
    public JinxedChoker copy() {
        return new JinxedChoker(this);
    }
}

class JinxedChokerChangeControllerEffect extends ContinuousEffectImpl {

    public JinxedChokerChangeControllerEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public JinxedChokerChangeControllerEffect(final JinxedChokerChangeControllerEffect effect) {
        super(effect);
    }

    @Override
    public JinxedChokerChangeControllerEffect copy() {
        return new JinxedChokerChangeControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        } else {
            discard();
        }
        return false;
    }

}

class JinxedChokerAddCounterEffect extends OneShotEffect {

    JinxedChokerAddCounterEffect() {
        super(Outcome.Benefit);
        staticText = "and puts a charge counter on it";
    }

    private JinxedChokerAddCounterEffect(final JinxedChokerAddCounterEffect effect) {
        super(effect);
    }

    @Override
    public JinxedChokerAddCounterEffect copy() {
        return new JinxedChokerAddCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.addCounters(
                CounterType.CHARGE.createInstance(), source.getFirstTarget(), source, game
        );
    }
}


class JinxedChokerDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());

        int count = 0;
        if (permanent != null) {
            count = permanent.getCounters(game).getCount(CounterType.CHARGE);
        }
        return count;
    }

    @Override
    public JinxedChokerDynamicValue copy() {
        return new JinxedChokerDynamicValue();
    }

    @Override
    public String getMessage() {
        return "charge counter on it";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class JinxedChokerCounterEffect extends OneShotEffect {

    public JinxedChokerCounterEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put a charge counter on {this} or remove one from it";
    }

    public JinxedChokerCounterEffect(final JinxedChokerCounterEffect effect) {
        super(effect);
    }

    @Override
    public JinxedChokerCounterEffect copy() {
        return new JinxedChokerCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (!sourcePermanent.getCounters(game).containsKey(CounterType.CHARGE) || controller.chooseUse(outcome, "Put a charge counter on? (No removes one)", source, game)) {
                return new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true).apply(game, source);
            } else {
                return new RemoveCounterSourceEffect(CounterType.CHARGE.createInstance()).apply(game, source);
            }
        }
        return false;
    }
}