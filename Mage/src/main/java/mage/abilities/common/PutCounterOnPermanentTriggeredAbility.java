package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * "Whenever you put one or more [] counters on a []" triggered ability
 *
 * @author PurpleCrowbar
 */
public class PutCounterOnPermanentTriggeredAbility extends TriggeredAbilityImpl {

    private final CounterType counterType; // when null, any counter type is accepted
    private final FilterPermanent filter;
    private final boolean setTargetPointer;

    public PutCounterOnPermanentTriggeredAbility(Effect effect, CounterType counterType, FilterPermanent filter) {
        this(effect, counterType, filter, false, false);
    }

    public PutCounterOnPermanentTriggeredAbility(Effect effect, CounterType counterType, FilterPermanent filter,
                                                 boolean setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.counterType = counterType;
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever you put one or more "
                + (this.counterType == null ? "" : this.counterType.getName() + " ")
                + "counters on " + CardUtil.addArticle(filter.getMessage()) + ", ");
    }

    protected PutCounterOnPermanentTriggeredAbility(final PutCounterOnPermanentTriggeredAbility ability) {
        super(ability);
        this.counterType = ability.counterType;
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public PutCounterOnPermanentTriggeredAbility copy() {
        return new PutCounterOnPermanentTriggeredAbility(this);
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
