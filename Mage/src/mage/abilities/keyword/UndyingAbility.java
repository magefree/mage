package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class UndyingAbility extends DiesTriggeredAbility {
    public UndyingAbility() {
        super(new ReturnSourceFromGraveyardToBattlefieldEffect());
        this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
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
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (!p.getCounters().containsKey(CounterType.P1P1) || p.getCounters().getCount(CounterType.P1P1) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Undying";
    }
}
