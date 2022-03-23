package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * Is applied when a {@link Permanent} matching the filter changes zones.
 *
 * @author LevelX2
 */
public class ZoneChangeAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected Zone fromZone;
    protected Zone toZone;
    protected String rule;

    public ZoneChangeAllTriggeredAbility(Zone zone, Zone toZone, Effect effect, FilterPermanent filter, String rule, boolean optional) {
        this(zone, null, toZone, effect, filter, rule, optional);
    }

    public ZoneChangeAllTriggeredAbility(Zone zone, Zone fromZone, Zone toZone, Effect effect, FilterPermanent filter, String rule, boolean optional) {
        super(zone, effect, optional);
        if (fromZone == Zone.BATTLEFIELD) {
            setLeavesTheBattlefieldTrigger(true);
        }        
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.rule = rule;
        this.filter = filter;
    }

    public ZoneChangeAllTriggeredAbility(final ZoneChangeAllTriggeredAbility ability) {
        super(ability);
        this.fromZone = ability.fromZone;
        this.toZone = ability.toZone;
        this.rule = ability.rule;
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if ((fromZone == null || fromZone.match(zEvent.getFromZone()))
                && (toZone == null || toZone.match(zEvent.getToZone()))) {
            Permanent perm;
            if (zEvent.getTarget() != null) {
                perm = zEvent.getTarget();
            } else {
                perm = game.getPermanent(event.getTargetId()); // LevelX2: maybe this part is not neccessary
            }
            if (filter.match(perm, controllerId, this, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return rule ;
    }

    @Override
    public ZoneChangeAllTriggeredAbility copy() {
        return new ZoneChangeAllTriggeredAbility(this);
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Zone getToZone() {
        return toZone;
    }
}
