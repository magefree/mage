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

package mage.abilities.costs.mana;

import mage.Constants.ColoredManaSymbol;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.mana.ManaOptions;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Targets;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ManaCostsImpl<T extends ManaCost> extends ArrayList<T> implements ManaCosts<T> {

    protected UUID id;

    private static Map<String, ManaCosts> costs = new HashMap<String, ManaCosts>();

    public ManaCostsImpl() {
        this.id = UUID.randomUUID();
    }

    public ManaCostsImpl(String mana) {
        this.id = UUID.randomUUID();
        load(mana);
    }

    public ManaCostsImpl(final ManaCostsImpl<T> costs) {
        this.id = costs.id;
        for (T cost : costs) {
            this.add((T) cost.copy());
        }
    }

    @Override
    public boolean add(ManaCost cost) {
        if (cost instanceof ManaCosts) {
            for (ManaCost manaCost : (ManaCosts<T>) cost) {
                super.add((T) manaCost);
            }
            return true;
        } else {
            return super.add((T) cost);
        }
    }

    @Override
    public int convertedManaCost() {
        int total = 0;
        for (ManaCost cost : this) {
            total += cost.convertedManaCost();
        }
        return total;
    }

    @Override
    public Mana getMana() {
        Mana mana = new Mana();
        for (ManaCost cost : this) {
            mana.add(cost.getMana());
        }
        return mana;
    }

    @Override
    public Mana getPayment() {
        Mana manaTotal = new Mana();
        for (ManaCost cost : this) {
            manaTotal.add(cost.getPayment());
        }
        return manaTotal;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (this.size() == 0 || noMana) {
            setPaid();
            return true;
        }

        Player player = game.getPlayer(controllerId);
        assignPayment(game, ability, player.getManaPool());
        while (!isPaid()) {
            if (player.playMana(this.getUnpaid(), game))
                assignPayment(game, ability, player.getManaPool());
            else
                return false;
        }
        for (ManaCost cost : this.getUnpaidVariableCosts()) {
            VariableManaCost vCost = (VariableManaCost) cost;
            while (!vCost.isPaid()) {
                if (player.playXMana(vCost, (ManaCosts<ManaCost>) this, game))
                    vCost.assignPayment(game, ability, player.getManaPool());
                else
                    return false;
            }
        }
        return true;
    }

    @Override
    public ManaCosts<T> getUnpaid() {
        ManaCosts<T> unpaid = new ManaCostsImpl<T>();
        for (T cost : this) {
            if (!(cost instanceof VariableManaCost) && !cost.isPaid())
                unpaid.add((T) cost.getUnpaid());
        }
        return unpaid;
    }

    @Override
    public ManaCosts<T> getUnpaidVariableCosts() {
        ManaCosts<T> unpaid = new ManaCostsImpl<T>();
        for (ManaCost cost : this) {
            if (cost instanceof VariableManaCost && !cost.isPaid())
                unpaid.add((T) cost.getUnpaid());
        }
        return unpaid;
    }


    @Override
    public List<VariableCost> getVariableCosts() {
        List<VariableCost> variableCosts = new ArrayList<VariableCost>();
        for (ManaCost cost : this) {
            if (cost instanceof VariableCost)
                variableCosts.add((VariableCost) cost);
        }
//		if (variableCosts.size() == 0) {
//			// add empty cost (#Issue 210)
//			variableCosts.add(new VariableManaCost());
//		}
        return variableCosts;
    }

    @Override
    public int getX() {
        int amount = 0;
        List<VariableCost> variableCosts = getVariableCosts();
        if (!variableCosts.isEmpty())
            amount = variableCosts.get(0).getAmount();
        return amount;
    }

    @Override
    public void setPayment(Mana mana) {
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool) {
        //attempt to pay colored costs first

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof ColoredManaCost) {
                cost.assignPayment(game, ability, pool);
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof HybridManaCost) {
                cost.assignPayment(game, ability, pool);
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof MonoHybridManaCost) {
                cost.assignPayment(game, ability, pool);
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof GenericManaCost) {
                cost.assignPayment(game, ability, pool);
            }
        }

        for (ManaCost cost : this) {
            if (!cost.isPaid() && cost instanceof VariableManaCost) {
                cost.assignPayment(game, ability, pool);
            }
        }
    }

    @Override
    public void load(String mana) {
        this.clear();
        if (costs.containsKey(mana)) {
            ManaCosts<T> savedCosts = costs.get(mana);
            for (ManaCost cost : savedCosts) {
                this.add((T) cost.copy());
            }
        } else {
            if (mana == null || mana.length() == 0)
                return;
            String[] symbols = mana.split("^\\{|\\}\\{|\\}$");
            for (String symbol : symbols) {
                if (symbol.length() > 0) {
                    if (symbol.length() == 1 || isNumeric(symbol)) {
                        if (Character.isDigit(symbol.charAt(0))) {
                            this.add((T) new GenericManaCost(Integer.valueOf(symbol)));
                        } else {
                            if (!symbol.equals("X"))
                                this.add((T) new ColoredManaCost(ColoredManaSymbol.lookup(symbol.charAt(0))));
                            else
                                this.add((T) new VariableManaCost());
                            //TODO: handle multiple {X} and/or {Y} symbols
                        }
                    } else {
                        if (Character.isDigit(symbol.charAt(0))) {
                            this.add((T) new MonoHybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(2))));
                        } else if (symbol.contains("P")) {
                            this.add((T) new PhyrexianManaCost(ColoredManaSymbol.lookup(symbol.charAt(0))));
                        } else {
                            this.add((T) new HybridManaCost(ColoredManaSymbol.lookup(symbol.charAt(0)), ColoredManaSymbol.lookup(symbol.charAt(2))));
                        }
                    }
                }
            }
            costs.put(mana, this.copy());
        }
    }

    private boolean isNumeric(String symbol) {
        try {
            Integer.parseInt(symbol);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<String> getSymbols() {
        List<String> symbols = new ArrayList<String>();
        for (ManaCost cost : this) {
            symbols.add(cost.getText());
        }
        return symbols;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getText() {
        if (this.size() == 0)
            return "";

        StringBuilder sbText = new StringBuilder();
        for (ManaCost cost : this) {
            sbText.append(cost.getText());
        }
        return sbText.toString();
    }

    @Override
    public ManaOptions getOptions() {
        ManaOptions options = new ManaOptions();
        for (ManaCost cost : this) {
            options.addMana(cost.getOptions());
        }
        return options;
    }

    @Override
    public boolean testPay(Mana testMana) {
        for (ManaCost cost : this) {
            if (cost.testPay(testMana))
                return true;
        }
        return false;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        for (T cost : this) {
            if (!cost.canPay(sourceId, controllerId, game))
                return false;
        }
        return true;
    }

    @Override
    public boolean isPaid() {
        for (T cost : this) {
            if (!((T) cost instanceof VariableManaCost) && !cost.isPaid())
                return false;
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
    public Targets getTargets() {
        Targets targets = new Targets();
        for (T cost : this) {
            targets.addAll(cost.getTargets());
        }
        return targets;
    }

    @Override
    public ManaCosts<T> copy() {
        return new ManaCostsImpl(this);
    }

    @Override
    public Filter getSourceFilter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSourceFilter(Filter filter) {
        for (T cost : this) {
            cost.setSourceFilter(filter);
        }
    }

}
