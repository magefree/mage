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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.counters.Counter;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RemoveVariableCountersSourceCost extends CostImpl<RemoveVariableCountersSourceCost> implements VariableCost {

    protected int amountPaid = 0;
    protected int minimalCountersToPay = 0;
    private String name;

    public RemoveVariableCountersSourceCost(Counter counter, int minimalCountersToPay) {
        this.minimalCountersToPay = minimalCountersToPay;
        this.name = counter.getName();
        this.text = "Remove X " + name + " counter from {this}";
    }

    public RemoveVariableCountersSourceCost(Counter counter) {
        this(counter, 0);
    }

    public RemoveVariableCountersSourceCost(final RemoveVariableCountersSourceCost cost) {
        super(cost);
        this.amountPaid = cost.amountPaid;
        this.minimalCountersToPay = cost.minimalCountersToPay;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent.getCounters().getCount(name) >= minimalCountersToPay) {
            return true;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(sourceId);
        Player player = game.getPlayer(permanent.getControllerId());
        this.amountPaid = player.getAmount(minimalCountersToPay, permanent.getCounters().getCount(name), "Choose X counters to remove", game);
        if (this.amountPaid >= minimalCountersToPay) {
            permanent.removeCounters(name, amountPaid, game);
            this.paid = true;
        }
        game.informPlayers(player.getName() + " removes " + this.amountPaid + " " + name + " counter from " + permanent.getName());
        return paid;
    }

    @Override
    public void clearPaid() {
        paid = false;
        amountPaid = 0;
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }

    @Override
    public void setFilter(FilterMana filter) {
    }

    @Override
    public RemoveVariableCountersSourceCost copy() {
        return new RemoveVariableCountersSourceCost(this);
    }

}

