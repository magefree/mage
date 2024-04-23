package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedBatchEvent;
import mage.game.events.TappedEvent;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Susucr
 */
public class BecomesTappedOneOrMoreTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<TappedEvent> {

    protected FilterPermanent filter;

    public BecomesTappedOneOrMoreTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterPermanent filter) {
        super(zone, effect, optional);
        this.filter = filter;
        setTriggerPhrase("Whenever one or more " + filter.getMessage() + " become tapped, ");
    }

    protected BecomesTappedOneOrMoreTriggeredAbility(final BecomesTappedOneOrMoreTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
    }

    @Override
    public BecomesTappedOneOrMoreTriggeredAbility copy() {
        return new BecomesTappedOneOrMoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_BATCH;
    }

    @Override
    public Stream<TappedEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((TappedBatchEvent) event)
                .getEvents()
                .stream()
                .filter(e -> Optional
                        .of(e)
                        .map(TappedEvent::getTargetId)
                        .map(game::getPermanent)
                        .filter(p -> filter.match(p, getControllerId(), this, game))
                        .isPresent()
                );
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return filterBatchEvent(event, game).findAny().isPresent();
    }
}
