
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Susucr
 */
public class CounterRemovedFromSourceWhileExiledTriggeredAbility extends TriggeredAbilityImpl {

    private final CounterType counterType;

    public CounterRemovedFromSourceWhileExiledTriggeredAbility(CounterType counterType, Effect effect) {
        this(counterType, effect, false);
    }

    public CounterRemovedFromSourceWhileExiledTriggeredAbility(CounterType counterType, Effect effect, boolean optional) {
        super(Zone.EXILED, effect, optional);
        this.counterType = counterType;
        setTriggerPhrase("Whenever a " + counterType.getName() + " counter is removed from {this} while it's exiled, ");
    }

    private CounterRemovedFromSourceWhileExiledTriggeredAbility(final CounterRemovedFromSourceWhileExiledTriggeredAbility ability) {
        super(ability);
        this.counterType = ability.counterType;
    }

    @Override
    public CounterRemovedFromSourceWhileExiledTriggeredAbility copy() {
        return new CounterRemovedFromSourceWhileExiledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(counterType.getName()) && event.getTargetId().equals(this.getSourceId());
    }
}
