package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * "Whenever you put one or more counters on a creature " triggered ability
 *
 * @author PurpleCrowbar
 */
public class PutCounterOnCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final Counter counterType; // when null, any counter type is accepted
    protected FilterPermanent filter;
    private final boolean setTargetPointer;

    public PutCounterOnCreatureTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, null, new FilterCreaturePermanent(), optional, false);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, Counter counter, boolean optional) {
        this(effect, counter, new FilterCreaturePermanent(), optional, false);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(effect, null, filter, optional, false);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, Counter counter, FilterPermanent filter, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.counterType = counter;
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public PutCounterOnCreatureTriggeredAbility(final PutCounterOnCreatureTriggeredAbility ability) {
        super(ability);
        this.counterType = ability.counterType;
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public PutCounterOnCreatureTriggeredAbility copy() {
        return new PutCounterOnCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !filter.match(permanent, controllerId, this, game)) {
            return false;
        }
        if (setTargetPointer) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        getEffects().setValue("countersAdded", event.getAmount());
        return counterType == null || event.getData().equals(counterType.getName());
    }

    @Override
    public String getTriggerPhrase() {
        if (counterType == null) {
            return "Whenever you put one or more counters on a " + filter.getMessage() + ", " ;
        }
        else {
            return "Whenever you put one or more " + counterType.getName() + " counters on a " + filter.getMessage() + ", " ;
        }
    }
}
