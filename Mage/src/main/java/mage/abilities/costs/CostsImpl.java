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
        if (this.isEmpty()) {
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
        return pay(ability, game, sourceId, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (this.size() > 0) {
            while (!isPaid()) {
                T cost = getFirstUnpaid();
                if (!cost.pay(ability, game, sourceId, controllerId, noMana, costToPay)) {
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
