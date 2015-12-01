package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

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
        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.P1P1.createInstance());
        game.setEnterWithCounters(source.getSourceId(), countersToAdd);
        return true;
    }
}
