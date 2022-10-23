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
    private final FilterPermanent filter;
    private final boolean setTargetPointer;

    public PutCounterOnCreatureTriggeredAbility(Effect effect) {
        this(effect, (Counter) null);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, Counter counter) {
        this(effect, counter, new FilterCreaturePermanent());
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, null, filter);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, Counter counter, FilterPermanent filter) {
        this(effect, counter, filter, false);
    }

    public PutCounterOnCreatureTriggeredAbility(Effect effect, Counter counter, FilterPermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect);
        this.counterType = counter;
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        if (counter == null) {
            setTriggerPhrase("Whenever you put one or more counters on a " + filter.getMessage() + ", ");
        }
        else {
            setTriggerPhrase("Whenever you put one or more " + counter.getName() + " counters on a " + filter.getMessage() + ", ");
        }
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
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        if (permanent == null || !filter.match(permanent, controllerId, this, game)) {
            return false;
        }
        if (counterType != null && !event.getData().equals(counterType.getName())) {
            return false;
        }
        if (setTargetPointer) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        getEffects().setValue("countersAdded", event.getAmount());
        return true;
    }
}
