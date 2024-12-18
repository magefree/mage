package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LoneFox
 */
public class DistributeCountersEffect extends OneShotEffect {

    private final CounterType counterType;
    private boolean removeAtEndOfTurn = false;

    /**
     * Distribute +1/+1 counters among targets
     */
    public DistributeCountersEffect() {
        this(CounterType.P1P1);
    }

    public DistributeCountersEffect(CounterType counterType) {
        super(Outcome.BoostCreature);
        this.counterType = counterType;
    }

    protected DistributeCountersEffect(final DistributeCountersEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.removeAtEndOfTurn = effect.removeAtEndOfTurn;
    }

    @Override
    public DistributeCountersEffect copy() {
        return new DistributeCountersEffect(this);
    }

    public DistributeCountersEffect withRemoveAtEndOfTurn() {
        this.removeAtEndOfTurn = true;
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.addCounters(counterType.createInstance(multiTarget.getTargetAmount(target)), source.getControllerId(), source, game);
                }
            }

            if (removeAtEndOfTurn) {
                DelayedTriggeredAbility ability = new AtTheBeginOfNextCleanupDelayedTriggeredAbility(
                        new RemoveCountersAtEndOfTurn(counterType));
                ability.getTargets().addAll(source.getTargets());
                game.addDelayedTriggeredAbility(ability, source);
            }

            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        Target target = mode.getTargets().get(0);
        if (!(target instanceof TargetAmount)) {
            throw new IllegalStateException("Must use TargetAmount");
        }
        TargetAmount targetAmount = (TargetAmount) target;
        DynamicValue amount = targetAmount.getAmount();

        String name = counterType.getName();
        String number = (amount instanceof StaticValue) ? CardUtil.numberToText(((StaticValue) amount).getValue()) : amount.toString();
        String text = "distribute " + number + ' ' + name + " counters among " + targetAmount.getDescription();
        if (removeAtEndOfTurn) {
            text += ". For each " + name + " counter you put on a creature this way, remove a "
                    + name + " counter from that creature at the beginning of the next cleanup step.";
        }
        return text;
    }
}

class RemoveCountersAtEndOfTurn extends OneShotEffect {

    private final CounterType counterType;

    RemoveCountersAtEndOfTurn(CounterType counterType) {
        super(Outcome.Detriment);
        this.counterType = counterType;
        String name = counterType.getName();
        staticText = "For each " + name + " counter you put on a creature this way, remove a "
                + name + " counter from that creature at the beginning of the next cleanup step.";
    }

    protected RemoveCountersAtEndOfTurn(final RemoveCountersAtEndOfTurn effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public RemoveCountersAtEndOfTurn copy() {
        return new RemoveCountersAtEndOfTurn(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.removeCounters(counterType.getName(), multiTarget.getTargetAmount(target), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
