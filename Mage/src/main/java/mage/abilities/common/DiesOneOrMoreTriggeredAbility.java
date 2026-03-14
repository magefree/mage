package mage.abilities.common;

import mage.MageObject;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public class DiesOneOrMoreTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    private final FilterPermanent filter;
    private final boolean setTargetPointer;

    public DiesOneOrMoreTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(effect, filter, optional, false);
    }

    public DiesOneOrMoreTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        this.setTriggerPhrase("Whenever one or more " + filter.getMessage() + " die, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    private DiesOneOrMoreTriggeredAbility(final DiesOneOrMoreTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
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
        List<ZoneChangeEvent> events = getFilteredEvents((ZoneChangeBatchEvent) event, game);
        if (events.isEmpty()) {
            return false;
        }
        if (setTargetPointer) {
            this.getAllEffects().setTargetPointer(new FixedTargets(
                    events.stream()
                            .map(GameEvent::getTargetId)
                            .map(game::getCard)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet()),
                    game
            ));
        }
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return ((ZoneChangeBatchEvent) event)
                .getEvents()
                .stream()
                .anyMatch(e -> TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, e, game));
    }
}
