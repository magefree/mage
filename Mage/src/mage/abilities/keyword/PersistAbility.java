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
            if (!p.getCounters().containsKey(CounterType.P1P1) || p.getCounters().getCount(CounterType.M1M1) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Persist";
    }
}
