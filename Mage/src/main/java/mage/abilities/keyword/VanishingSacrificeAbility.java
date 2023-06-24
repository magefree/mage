package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class VanishingSacrificeAbility extends TriggeredAbilityImpl {
    public VanishingSacrificeAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        this.setRuleVisible(false);
    }

    public VanishingSacrificeAbility(final VanishingSacrificeAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId())) {
            Permanent p = game.getPermanent(this.getSourceId());
            if (p != null) {
                return p.getCounters(game).getCount(CounterType.TIME) == 0;
            }
        }
        return false;
    }

    @Override
    public VanishingSacrificeAbility copy() {
        return new VanishingSacrificeAbility(this);
    }
    
}
