package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedBatchEvent;

/**
 * @author Susucr
 */
public class BecomesTappedOneOrMoreTriggeredAbility extends TriggeredAbilityImpl {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        TappedBatchEvent batchEvent = (TappedBatchEvent) event;
        return batchEvent
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .anyMatch(p -> filter.match(p, getControllerId(), this, game));
    }
}
