/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.costs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.game.Game;
import mage.target.Targets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public class CostsImpl<T extends Cost> extends ArrayList<T> implements Costs<T> {

    protected String text = null;

    public CostsImpl() {
    }

    public CostsImpl(final CostsImpl<T> costs) {
        for (Cost cost : costs) {
            this.add((T) cost.copy());
        }
        this.text = costs.text;
    }

    @Override
    public UUID getId() {
        throw new RuntimeException("Not supported method");
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        if (text != null) {
            return text;
        }
        if (this.size() == 0) {
            return "";
        }

        StringBuilder sbText = new StringBuilder();
        for (T cost : this) {
            String textCost = cost.getText();
            if (textCost != null && !textCost.isEmpty()) {
                sbText.append(Character.toUpperCase(textCost.charAt(0))).append(textCost.substring(1)).append(", ");
            }
        }
        if (sbText.length() > 1) {
            sbText.setLength(sbText.length() - 2);
        }
        return sbText.toString();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        for (T cost : this) {
            if (!cost.canPay(ability, sourceId, controllerId, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (this.size() > 0) {
            while (!isPaid()) {
                T cost = getFirstUnpaid();
                if (!cost.pay(ability, game, sourceId, controllerId, noMana)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isPaid() {
        for (T cost : this) {
            if (!(cost instanceof VariableManaCost) && !cost.isPaid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearPaid() {
        for (T cost : this) {
            cost.clearPaid();
        }
    }

    @Override
    public void setPaid() {
        for (T cost : this) {
            cost.setPaid();
        }
    }

    @Override
    public Costs<T> getUnpaid() {
        Costs<T> unpaid = new CostsImpl<>();
        for (T cost : this) {
            if (!cost.isPaid()) {
                unpaid.add(cost);
            }
        }
        return unpaid;
    }

    protected T getFirstUnpaid() {
        Costs<T> unpaid = getUnpaid();
        if (unpaid.size() > 0) {
            return unpaid.get(0);
        }
        return null;
    }

    @Override
    public List<VariableCost> getVariableCosts() {
        List<VariableCost> variableCosts = new ArrayList<>();
        for (T cost : this) {
            if (cost instanceof VariableCost) {
                variableCosts.add((VariableCost) cost);
            }
            if (cost instanceof ManaCosts) {
                variableCosts.addAll(((ManaCosts) cost).getVariableCosts());
            }
        }
        return variableCosts;
    }

    @Override
    public Targets getTargets() {
        Targets targets = new Targets();
        for (T cost : this) {
            if (cost.getTargets() != null) {
                targets.addAll(cost.getTargets());
            }
        }
        return targets;
    }

    @Override
    public Costs<T> copy() {
        return new CostsImpl(this);
    }

}
