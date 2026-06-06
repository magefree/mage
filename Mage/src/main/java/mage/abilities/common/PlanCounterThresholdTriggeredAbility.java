package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Optional;

/**
 * "When the Nth plan counter is put on {this}, sacrifice it. When you do, [effect]."
 * triggered ability used by Plan-subtype enchantments.
 *
 * @author muz
 */
public class PlanCounterThresholdTriggeredAbility extends TriggeredAbilityImpl {

    private final int threshold;

    /**
     * @param threshold the plan counter count at which this ability fires
     * @param reflexive the reflexive triggered ability to fire after sacrificing
     */
    public PlanCounterThresholdTriggeredAbility(int threshold, ReflexiveTriggeredAbility reflexive) {
        super(Zone.ALL, new DoWhenCostPaid(reflexive, new SacrificeSourceCost().setText("sacrifice it"), "Sacrifice {this}?", false), false);
        this.threshold = threshold;
        setTriggerPhrase("When the " + CardUtil.numberToOrdinalText(threshold) + " plan counter is put on {this}, ");
    }

    private PlanCounterThresholdTriggeredAbility(final PlanCounterThresholdTriggeredAbility ability) {
        super(ability);
        this.threshold = ability.threshold;
    }

    @Override
    public PlanCounterThresholdTriggeredAbility copy() {
        return new PlanCounterThresholdTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId()) || !event.getData().equals(CounterType.PLAN.getName())) {
            return false;
        }
        int amountAdded = event.getAmount();
        Permanent sourcePermanent = Optional
                .ofNullable(game.getPermanent(getSourceId()))
                .orElse(game.getPermanentEntering(getSourceId()));
        int planCounters = sourcePermanent != null
                ? sourcePermanent.getCounters(game).getCount(CounterType.PLAN)
                : amountAdded;
        return planCounters - amountAdded < threshold && threshold <= planCounters;
    }
}
