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
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayerWithCounter;

/**
 *
 * @author LevelX
 */
public class RemoveCounterTargetCost extends CostImpl<RemoveCounterTargetCost> {

    private TargetPermanentOrPlayerWithCounter target;
    private int amount;
    private String name;
    
    public RemoveCounterTargetCost(TargetPermanentOrPlayerWithCounter target) {
        this.target = target;
        this.text = "Remove a counter from " + target.getTargetName();
    }

    public RemoveCounterTargetCost(final RemoveCounterTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        paid = false;
        Player controller = game.getPlayer(controllerId);
        if (target.choose(Outcome.UnboostCreature, controllerId, sourceId, game)) {
            for (UUID targetId: (List<UUID>)target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    if (permanent.getCounters().size() > 0) {
                        String counterName = null;
                        if (permanent.getCounters().size() > 1) {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new HashSet<String>();
                            for (Counter counter : permanent.getCounters().values()) {
                                if (permanent.getCounters().getCount(counter.getName()) > 0) {
                                    choices.add(counter.getName());
                                }
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter to remove from " + permanent.getName());
                            controller.choose(Outcome.UnboostCreature, choice, game);
                            counterName = choice.getChoice();
                        } else {
                            for (Counter counter : permanent.getCounters().values()) {
                                if (counter.getCount() > 0) {
                                    counterName = counter.getName();
                                }
                            }
                        }
                        if (counterName != null) {
                            permanent.removeCounters(counterName, 1, game);
                            if (permanent.getCounters().getCount(counterName) == 0 ){
                                permanent.getCounters().removeCounter(counterName);
                            }
                            this.paid = true;
                            game.informPlayers(controller.getName() + " removes a counter from " + permanent.getName());
                        }
                    }
                } else {
                    // TODO: Remove counter from Player
                }
            }
        }
        target.clearChosen();
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    @Override
    public RemoveCounterTargetCost copy() {
        return new RemoveCounterTargetCost(this);
    }
}
