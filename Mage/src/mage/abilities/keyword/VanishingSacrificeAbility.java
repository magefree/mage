package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class VanishingSacrificeAbility extends TriggeredAbilityImpl<VanishingSacrificeAbility> {
    public VanishingSacrificeAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public VanishingSacrificeAbility(final VanishingSacrificeAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_REMOVED && event.getData().equals("Time") && event.getTargetId().equals(this.getSourceId())) {
            Permanent p = game.getPermanent(this.getSourceId());
            if (p != null) {
                return p.getCounters().getCount(CounterType.TIME) == 0;
            }
        }
        return false;
    }

    @Override
    public VanishingSacrificeAbility copy() {
        return new VanishingSacrificeAbility(this);
    }

    @Override
    public String getRule() {
        return "";
    }
}
