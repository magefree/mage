package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetObject;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX
 */
public class RemoveCounterCost extends CostImpl {

    protected final Target target;
    private final CounterType counterTypeToRemove;
    protected final int countersToRemove;

    public RemoveCounterCost(Target target) {
        this(target, null);
    }

    public RemoveCounterCost(Target target, CounterType counterTypeToRemove) {
        this(target, counterTypeToRemove, 1);
    }

    public RemoveCounterCost(Target target, CounterType counterTypeToRemove, int countersToRemove) {
        this.target = target;
        this.counterTypeToRemove = counterTypeToRemove;
        this.countersToRemove = countersToRemove;

        this.text = setText();
    }

    public RemoveCounterCost(final RemoveCounterCost cost) {
        super(cost);
        this.target = cost.target.copy();
        this.countersToRemove = cost.countersToRemove;
        this.counterTypeToRemove = cost.counterTypeToRemove;
    }

    // TODO: TayamLuminousEnigmaCost can be simplified
    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        int countersRemoved = 0;
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        if (countersToRemove == 0) { // Can happen when used for X costs where X = 0;
            paid = true;
            return paid;
        }
        target.clearChosen();

        Outcome outcome;
        if (target instanceof TargetPermanent) {
            outcome = Outcome.UnboostCreature;
        } else if (target instanceof TargetCard) {  // For Mari, the Killing Quill
            outcome = Outcome.Neutral;
        } else {
            throw new RuntimeException(
                    "Wrong target type provided for RemoveCounterCost. Provided " + target.getClass() + ". " +
                            "From ability " + ability);
        }

        if (!target.choose(outcome, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        for (UUID targetId : target.getTargets()) {
            Card targetObject;
            if (target instanceof TargetPermanent) {
                targetObject = game.getPermanent(targetId);
            } else {  // For Mari, the Killing Quill
                targetObject = game.getCard(targetId);
            }

            if (targetObject == null
                    || targetObject.getCounters(game).isEmpty()
                    || !(counterTypeToRemove == null || targetObject.getCounters(game).containsKey(counterTypeToRemove))) {
                continue;
            }
            String counterName = null;

            if (counterTypeToRemove != null) {  // Counter type specified
                counterName = counterTypeToRemove.getName();
            } else if (targetObject.getCounters(game).size() == 1) {  // Only one counter of creature
                for (Counter counter : targetObject.getCounters(game).values()) {
                    if (counter.getCount() > 0) {
                        counterName = counter.getName();
                    }
                }
            } else {  // Multiple counters, player much choose which type to remove from
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new HashSet<>();
                for (Counter counter : targetObject.getCounters(game).values()) {
                    if (targetObject.getCounters(game).getCount(counter.getName()) > 0) {
                        choices.add(counter.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter to remove from " + targetObject.getLogName());
                if (!controller.choose(Outcome.UnboostCreature, choice, game)) {
                    return false;
                }
                counterName = choice.getChoice();
            }

            if (counterName != null && !counterName.isEmpty()) {
                int countersLeft = countersToRemove - countersRemoved;
                int countersOnPermanent = targetObject.getCounters(game).getCount(counterName);
                int numberOfCountersSelected = 1;
                if (countersLeft > 1 && countersOnPermanent > 1) {
                    numberOfCountersSelected = controller.getAmount(1, Math.min(countersLeft, countersOnPermanent),
                            "Remove how many counters from " + targetObject.getIdName(), game);
                }
                targetObject.removeCounters(counterName, numberOfCountersSelected, source, game);
                countersRemoved += numberOfCountersSelected;
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() +
                            " removes " + (numberOfCountersSelected == 1 ? "a" : numberOfCountersSelected) + ' ' +
                            counterName + (numberOfCountersSelected == 1 ? " counter from " : " counters from ") +
                            targetObject.getName());
                }
                if (countersRemoved == countersToRemove) {
                    this.paid = true;
                    break;
                }
            }
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("remove ");
        if (counterTypeToRemove != null) {
            sb.append(CardUtil.numberToText(countersToRemove, counterTypeToRemove.getArticle()));
            sb.append(' ');
            sb.append(counterTypeToRemove.getName());
        } else {
            sb.append(CardUtil.numberToText(countersToRemove, "a"));
        }
        sb.append(countersToRemove > 1 ? " counters from " : " counter from ");
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append(target.getTargetName());
        } else {
            sb.append(CardUtil.addArticle(target.getTargetName()));
        }
        return sb.toString();
    }

    @Override
    public RemoveCounterCost copy() {
        return new RemoveCounterCost(this);
    }
}
