
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

public class PersistAbility extends DiesSourceTriggeredAbility {

    public PersistAbility() {
        super(new PersistEffect());
        this.addEffect(new ReturnSourceFromGraveyardToBattlefieldEffect(false, true));
    }

    public PersistAbility(final PersistAbility ability) {
        super(ability);
    }

    @Override
    public PersistAbility copy() {
        return new PersistAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return super.checkEventType(event, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent.getCounters(game).getCount(CounterType.M1M1) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "persist <i>(When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)</i>";
    }
}

class PersistEffect extends OneShotEffect {

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
        Counters countersToAdd = new Counters();
        countersToAdd.addCounter(CounterType.M1M1.createInstance());
        game.setEnterWithCounters(source.getSourceId(), countersToAdd);
        return true;
    }
}
