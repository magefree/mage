
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LoneFox
 */
public class DistributeCountersEffect extends OneShotEffect {

    private final CounterType counterType;
    private final int amount;
    private final boolean removeAtEndOfTurn;
    private final String targetDescription;

    public DistributeCountersEffect(CounterType counterType, int amount, String targetDescription) {
        this(counterType, amount, false, targetDescription);
    }

    public DistributeCountersEffect(CounterType counterType, int amount, boolean removeAtEndOfTurn, String targetDescription) {
        super(Outcome.BoostCreature);
        this.counterType = counterType;
        this.amount = amount;
        this.removeAtEndOfTurn = removeAtEndOfTurn;
        this.targetDescription = targetDescription;
    }

    public DistributeCountersEffect(final DistributeCountersEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
        this.removeAtEndOfTurn = effect.removeAtEndOfTurn;
        this.targetDescription = effect.targetDescription;
    }

    @Override
    public DistributeCountersEffect copy() {
        return new DistributeCountersEffect(this);
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
        if (!staticText.isEmpty()) {
            return staticText;
        }

        String name = counterType.getName();
        String text = "distribute " + CardUtil.numberToText(amount) + ' ' + name + " counters among " + targetDescription;
        if (removeAtEndOfTurn) {
            text += " For each " + name + " counter you put on a creature this way, remove a "
                    + name + " counter from that creature at the beginning of the next cleanup step.";
        }
        return text;
    }
}

class RemoveCountersAtEndOfTurn extends OneShotEffect {

    private final CounterType counterType;

    public RemoveCountersAtEndOfTurn(CounterType counterType) {
        super(Outcome.Detriment);
        this.counterType = counterType;
        String name = counterType.getName();
        staticText = "For each " + name + " counter you put on a creature this way, remove a "
                + name + " counter from that creature at the beginning of the next cleanup step.";
    }

    public RemoveCountersAtEndOfTurn(final RemoveCountersAtEndOfTurn effect) {
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
