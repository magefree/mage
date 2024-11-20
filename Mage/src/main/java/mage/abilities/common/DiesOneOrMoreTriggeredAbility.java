package mage.abilities.common;

import mage.MageObject;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author Susucr
 */
public class DiesOneOrMoreTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    private final FilterCreaturePermanent filter;

    public DiesOneOrMoreTriggeredAbility(Effect effect, FilterCreaturePermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTriggerPhrase("Whenever one or more " + filter.getMessage() + " die, ");
    }

    private DiesOneOrMoreTriggeredAbility(final DiesOneOrMoreTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesOneOrMoreTriggeredAbility copy() {
        return new DiesOneOrMoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (!event.isDiesEvent()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && filter.match(permanent, getControllerId(), this, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return ((ZoneChangeBatchEvent) event)
                .getEvents()
                .stream()
                .allMatch(e -> TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, e, game));
    }
}
