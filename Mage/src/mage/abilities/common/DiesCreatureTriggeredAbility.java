package mage.abilities.common;

import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
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

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent("a creature"));
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another) {
        this(effect, optional, new FilterCreaturePermanent("another creature"));
        filter.add(new AnotherPredicate());
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
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
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;

            if (game.getPermanent(sourceId) == null) {
                if (game.getLastKnownInformation(sourceId, Zone.BATTLEFIELD) == null) {
                    return false;
                }
            }

            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
                System.out.println("The permanent in the trigger is " + permanent.getName());
                if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
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
