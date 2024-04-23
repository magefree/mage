package mage.abilities.decorator;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.stream.Stream;

/**
 * @author Susucr
 */
public class ConditionalInterveningIfBatchTriggeredAbility<T extends GameEvent> extends ConditionalInterveningIfTriggeredAbility implements BatchTriggeredAbility<T> {

    private final BatchTriggeredAbility<T> ability;

    public ConditionalInterveningIfBatchTriggeredAbility(BatchTriggeredAbility<T> ability, Condition condition, String text) {
        super(ability, condition, text);
        this.ability = ability;
    }

    protected ConditionalInterveningIfBatchTriggeredAbility(final ConditionalInterveningIfBatchTriggeredAbility<T> triggered) {
        super(triggered);
        this.ability = triggered.ability.copy();
    }

    @Override
    public ConditionalInterveningIfBatchTriggeredAbility<T> copy() {
        return new ConditionalInterveningIfBatchTriggeredAbility<T>(this);
    }

    @Override
    public Stream<T> filterBatchEvent(GameEvent event, Game game) {
        return ability.filterBatchEvent(event, game);
    }
}
