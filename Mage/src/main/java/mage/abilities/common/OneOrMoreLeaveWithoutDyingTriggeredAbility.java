package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author TheElk801
 */
public class OneOrMoreLeaveWithoutDyingTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    private final FilterPermanent filter;

    public OneOrMoreLeaveWithoutDyingTriggeredAbility(Effect effect, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        setTriggerPhrase("Whenever one or more " + filter.getMessage() + " leave the battlefield without dying, ");
    }

    private OneOrMoreLeaveWithoutDyingTriggeredAbility(final OneOrMoreLeaveWithoutDyingTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public OneOrMoreLeaveWithoutDyingTriggeredAbility copy() {
        return new OneOrMoreLeaveWithoutDyingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        return Zone.BATTLEFIELD.match(event.getFromZone())
                && !Zone.GRAVEYARD.match(event.getToZone())
                && filter.match(event.getTarget(), getControllerId(), this, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }
}
