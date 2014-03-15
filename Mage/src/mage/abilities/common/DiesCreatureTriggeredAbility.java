package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author North
 */
public class DiesCreatureTriggeredAbility extends TriggeredAbilityImpl<DiesCreatureTriggeredAbility> {

    protected FilterCreaturePermanent filter;
    private boolean setTargetPointer;

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent("a creature"));
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another) {
        this(effect, optional, new FilterCreaturePermanent("another creature"));
        filter.add(new AnotherPredicate());
    }
    
    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another, boolean setTargetPointer) {
        this(effect, optional, new FilterCreaturePermanent("another creature"));
        filter.add(new AnotherPredicate());
        this.setTargetPointer = setTargetPointer;
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        this(effect, optional, filter, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public DiesCreatureTriggeredAbility(DiesCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesCreatureTriggeredAbility copy() {
        return new DiesCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.ZONE_CHANGE)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            // why is this check neccessary?
//            if (game.getPermanentOrLKIBattlefield(sourceId) == null) {
//                return false;
//            }
            if (zEvent.getFromZone().equals(Zone.BATTLEFIELD) && zEvent.getToZone().equals(Zone.GRAVEYARD)) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
                if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                    if (setTargetPointer) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " dies, " + super.getRule();
    }
}
