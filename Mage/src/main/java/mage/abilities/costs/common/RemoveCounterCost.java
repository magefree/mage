package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX
 */
public class RemoveCounterCost extends CostImpl {

    protected final TargetPermanent target;
    private final CounterType counterTypeToRemove;
    protected final int countersToRemove;

    public RemoveCounterCost(TargetPermanent target) {
        this(target, null);
    }

    public RemoveCounterCost(TargetPermanent target, CounterType counterTypeToRemove) {
        this(target, counterTypeToRemove, 1);
    }

    public RemoveCounterCost(TargetPermanent target, CounterType counterTypeToRemove, int countersToRemove) {
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

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        int countersRemoved = 0;
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (countersToRemove == 0) { // Can happen when used for X costs where X = 0;
                return paid = true;
            }
            target.clearChosen();
            if (target.choose(Outcome.UnboostCreature, controllerId, source.getSourceId(), game)) {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if (!permanent.getCounters(game).isEmpty() && (counterTypeToRemove == null || permanent.getCounters(game).containsKey(counterTypeToRemove))) {
                            String counterName = null;

                            if (counterTypeToRemove != null) {
                                counterName = counterTypeToRemove.getName();
                            } else if (permanent.getCounters(game).size() > 1 && counterTypeToRemove == null) {
                                Choice choice = new ChoiceImpl(true);
                                Set<String> choices = new HashSet<>();
                                for (Counter counter : permanent.getCounters(game).values()) {
                                    if (permanent.getCounters(game).getCount(counter.getName()) > 0) {
                                        choices.add(counter.getName());
                                    }
                                }
                                choice.setChoices(choices);
                                choice.setMessage("Choose a counter to remove from " + permanent.getLogName());
                                if (!controller.choose(Outcome.UnboostCreature, choice, game)) {
                                    return false;
                                }
                                counterName = choice.getChoice();
                            } else {
                                for (Counter counter : permanent.getCounters(game).values()) {
                                    if (counter.getCount() > 0) {
                                        counterName = counter.getName();
                                    }
                                }
                            }
                            if (counterName != null && !counterName.isEmpty()) {
                                int countersLeft = countersToRemove - countersRemoved;
                                int countersOnPermanent = permanent.getCounters(game).getCount(counterName);
                                int numberOfCountersSelected = 1;
                                if (countersLeft > 1 && countersOnPermanent > 1) {
                                    numberOfCountersSelected = controller.getAmount(1, Math.min(countersLeft, countersOnPermanent),
                                            new StringBuilder("Remove how many counters from ").append(permanent.getIdName()).toString(), game);
                                }
                                permanent.removeCounters(counterName, numberOfCountersSelected, source, game);
                                countersRemoved += numberOfCountersSelected;
                                if (!game.isSimulation()) {
                                    game.informPlayers(new StringBuilder(controller.getLogName())
                                            .append(" removes ").append(numberOfCountersSelected == 1 ? "a" : numberOfCountersSelected).append(' ')
                                            .append(counterName).append(numberOfCountersSelected == 1 ? " counter from " : " counters from ")
                                            .append(permanent.getName()).toString());
                                }
                                if (countersRemoved == countersToRemove) {
                                    this.paid = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(source.getSourceId(), controllerId, game);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("remove ");
        if (counterTypeToRemove != null) {
            sb.append(CardUtil.numberToText(countersToRemove, counterTypeToRemove.getArticle())).append(' ').append(counterTypeToRemove.getName());
        } else {
            sb.append(CardUtil.numberToText(countersToRemove, "a"));
        }
        sb.append(countersToRemove == 1 ? " counter from " : " counters from ").append(target.getMaxNumberOfTargets() == 1 ? "a " : "").append(target.getTargetName());
        return sb.toString();
    }

    @Override
    public RemoveCounterCost copy() {
        return new RemoveCounterCost(this);
    }
}
