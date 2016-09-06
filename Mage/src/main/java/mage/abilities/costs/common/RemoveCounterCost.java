/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.costs.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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

/**
 *
 * @author LevelX
 */
public class RemoveCounterCost extends CostImpl {

    private TargetPermanent target;
    private String name;
    private CounterType counterTypeToRemove;
    private int countersToRemove;

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
        this.name = cost.name;
        this.countersToRemove = cost.countersToRemove;
        this.counterTypeToRemove = cost.counterTypeToRemove;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        int countersRemoved = 0;
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (countersToRemove == 0) { // Can happen when used for X costs where X = 0;
                return paid = true;
            }
            target.clearChosen();
            if (target.choose(Outcome.UnboostCreature, controllerId, sourceId, game)) {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if (permanent.getCounters(game).size() > 0 && (counterTypeToRemove == null || permanent.getCounters(game).containsKey(counterTypeToRemove))) {
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
                                controller.choose(Outcome.UnboostCreature, choice, game);
                                counterName = choice.getChoice();
                            } else {
                                for (Counter counter : permanent.getCounters(game).values()) {
                                    if (counter.getCount() > 0) {
                                        counterName = counter.getName();
                                    }
                                }
                            }
                            if (counterName != null) {
                                int countersLeft = countersToRemove - countersRemoved;
                                int countersOnPermanent = permanent.getCounters(game).getCount(counterName);
                                int numberOfCountersSelected = 1;
                                if (countersLeft > 1 && countersOnPermanent > 1) {
                                    numberOfCountersSelected = controller.getAmount(1, Math.min(countersLeft, countersOnPermanent),
                                            new StringBuilder("Remove how many counters from ").append(permanent.getIdName()).toString(), game);
                                }
                                permanent.removeCounters(counterName, numberOfCountersSelected, game);
                                if (permanent.getCounters(game).getCount(counterName) == 0) {
                                    // this removes only the item with number = 0 from the collection
                                    permanent.getCounters(game).removeCounter(counterName);
                                }
                                countersRemoved += numberOfCountersSelected;
                                if (!game.isSimulation()) {
                                    game.informPlayers(new StringBuilder(controller.getLogName())
                                            .append(" removes ").append(numberOfCountersSelected == 1 ? "a" : numberOfCountersSelected).append(" ")
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
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Remove ");
        sb.append(CardUtil.numberToText(countersToRemove, "a")).append(" ");
        if (counterTypeToRemove != null) {
            sb.append(counterTypeToRemove.getName());
        }
        sb.append(countersToRemove == 1 ? " counter from " : " counters from ").append(target.getMaxNumberOfTargets() == 1 ? "a " : "").append(target.getTargetName());
        return sb.toString();
    }

    @Override
    public RemoveCounterCost copy() {
        return new RemoveCounterCost(this);
    }
}
