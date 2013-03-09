package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class PersistAbility extends DiesTriggeredAbility {
    public PersistAbility() {
        super(new ReturnSourceFromGraveyardToBattlefieldEffect());
        this.addEffect(new AddCountersSourceEffect(CounterType.M1M1.createInstance()));
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
