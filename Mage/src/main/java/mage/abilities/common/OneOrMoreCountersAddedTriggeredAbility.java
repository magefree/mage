package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

public class OneOrMoreCountersAddedTriggeredAbility extends TriggeredAbilityImpl {

    private final CounterType counterType;

    public OneOrMoreCountersAddedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public OneOrMoreCountersAddedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, CounterType.P1P1);
    }

    public OneOrMoreCountersAddedTriggeredAbility(Effect effect, boolean optional, CounterType counterType) {
        super(Zone.ALL, effect, optional);
        this.counterType = counterType;
        setTriggerPhrase("Whenever one or more " + counterType.getName() + " counters are put on {this}, ");
    }

    private OneOrMoreCountersAddedTriggeredAbility(final OneOrMoreCountersAddedTriggeredAbility ability) {
        super(ability);
        this.counterType = ability.counterType;
    }

    @Override
    public OneOrMoreCountersAddedTriggeredAbility copy() {
        return new OneOrMoreCountersAddedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(counterType.getName())
                && event.getAmount() > 0
                && event.getTargetId().equals(this.getSourceId());
    }
}
