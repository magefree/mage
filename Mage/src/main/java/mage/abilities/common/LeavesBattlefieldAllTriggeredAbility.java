
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class LeavesBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected SetTargetPointer setTargetPointer;

    public LeavesBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public LeavesBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(Zone.BATTLEFIELD, effect, filter, optional);
    }

    public LeavesBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE);
    }

    public LeavesBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    protected LeavesBattlefieldAllTriggeredAbility(final LeavesBattlefieldAllTriggeredAbility ability) {
        super(ability);
        filter = ability.filter;
        setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public LeavesBattlefieldAllTriggeredAbility copy() {
        return new LeavesBattlefieldAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
            if (filter.match(permanent, getControllerId(), this, game)) {
                if (setTargetPointer != SetTargetPointer.NONE) {
                    for (Effect effect : this.getEffects()) {
                        switch (setTargetPointer) {
                            case PERMANENT:
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                break;
                            case PLAYER:
                                effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                                break;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " leaves the battlefield, " ;
    }
}
