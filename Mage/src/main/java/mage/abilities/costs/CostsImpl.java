package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.game.Game;
import mage.target.Targets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class CostsImpl<T extends Cost> extends ArrayList<T> implements Costs<T> {

    protected String text = null;

    public CostsImpl() {
    }

    protected CostsImpl(final CostsImpl<T> costs) {
        this.ensureCapacity(costs.size());
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
    public CostsImpl<T> setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String getText() {
        if (text != null) {
            return text;
        }
        if (this.isEmpty()) {
            return "";
        }

        StringBuilder sbText = new StringBuilder();
        for (T cost : this) {
            String textCost = cost.getText();
            if (textCost != null && !textCost.isEmpty()) {
                if (textCost.startsWith("and")) {
                    if (sbText.length() > 1) {
                        // Remove "," from previous cost
                        sbText.deleteCharAt(sbText.length() - 2);
                    }
                    sbText.append(textCost).append(", ");
                } else {
                    sbText.append(Character.toUpperCase(textCost.charAt(0))).append(textCost.substring(1)).append(", ");
                }
            }
        }
        if (sbText.length() > 1) {
            sbText.setLength(sbText.length() - 2);
        }
        return sbText.toString();
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        for (T cost : this) {
            if (!cost.canPay(ability, source, controllerId, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana) {
        return pay(ability, game, source, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (this.size() > 0) {
            while (!isPaid()) {
                T cost = getFirstUnpaid();
                if (!cost.pay(ability, game, source, controllerId, noMana, costToPay)) {
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
        if (!unpaid.isEmpty()) {
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
        Targets res = new Targets();
        for (T cost : this) {
            res.addAll(cost.getTargets());
        }
        return res.withReadOnly();
    }

    @Override
    public CostsImpl<T> copy() {
        return new CostsImpl(this);
    }
}
