
package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedMilledValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledBatchAllEvent;
import mage.game.events.MilledCardEvent;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Susucr
 */
public class OneOrMoreMilledTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<MilledCardEvent> {

    private final FilterCard filter;

    public OneOrMoreMilledTriggeredAbility(FilterCard filter, Effect effect) {
        this(filter, effect, false);
    }

    public OneOrMoreMilledTriggeredAbility(FilterCard filter, Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTriggerPhrase("Whenever one or more " + filter.getMessage() + " are milled, ");
        this.filter = filter;
    }

    private OneOrMoreMilledTriggeredAbility(final OneOrMoreMilledTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public OneOrMoreMilledTriggeredAbility copy() {
        return new OneOrMoreMilledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MILLED_CARDS_BATCH_FOR_ALL;
    }

    @Override
    public Stream<MilledCardEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((MilledBatchAllEvent) event)
                .getEvents()
                .stream()
                .filter(e -> Optional
                        .of(e)
                        .map(mce -> mce.getCard(game))
                        .filter(card -> filter.match(card, getControllerId(), this, game))
                        .isPresent());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int count = filterBatchEvent(event, game)
                .mapToInt(k -> 1)
                .sum();
        if (count <= 0) {
            return false;
        }
        getEffects().setValue(SavedMilledValue.VALUE_KEY, count);
        return true;
    }
}
