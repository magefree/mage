package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.Objects;

/**
 * @author Susucr
 */
public class DiesOneOrMoreCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;

    public DiesOneOrMoreCreatureTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
        this.setTriggerPhrase("Whenever one or more " + filter.getMessage() + " die,");
    }

    private DiesOneOrMoreCreatureTriggeredAbility(final DiesOneOrMoreCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesOneOrMoreCreatureTriggeredAbility copy() {
        return new DiesOneOrMoreCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((ZoneChangeBatchEvent) event)
                .getEvents()
                .stream()
                .filter(ZoneChangeEvent::isDiesEvent)
                .map(ZoneChangeEvent::getTargetId)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(p -> filter.match(p, getControllerId(), this, game));
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return ((ZoneChangeBatchEvent) event)
                .getEvents()
                .stream()
                .allMatch(e -> TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, e, game));
    }
}