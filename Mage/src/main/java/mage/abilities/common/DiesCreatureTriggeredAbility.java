package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author North
 */
public class DiesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    private boolean setTargetPointer;

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another) {
        this(effect, optional, another, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another, boolean setTargetPointer) {
        this(effect, optional, new FilterCreaturePermanent(another ? "another creature" : "a creature"));
        if (another) {
            filter.add(AnotherPredicate.instance);
        }
        this.setTargetPointer = setTargetPointer;
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        this(effect, optional, filter, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, filter, setTargetPointer);
    }

    public DiesCreatureTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + filter.getMessage() + (filter.getMessage().startsWith("one or more") ? " die, " : " dies, "));
    }

    public DiesCreatureTriggeredAbility(final DiesCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DiesCreatureTriggeredAbility copy() {
        return new DiesCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !filter.match(zEvent.getTarget(), controllerId, this, game)) {
            return false;
        }
        getEffects().setValue("creatureDied", zEvent.getTarget());
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }
}
