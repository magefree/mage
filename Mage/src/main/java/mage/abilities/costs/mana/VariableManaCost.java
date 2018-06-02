
package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.constants.ColoredManaSymbol;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.ManaPool;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class VariableManaCost extends ManaCostImpl implements VariableCost {

    protected int multiplier;
    protected FilterMana filter;
    protected int minX = 0;
    protected int maxX = Integer.MAX_VALUE;

    public VariableManaCost() {
        this(1);
    }

    public VariableManaCost(int multiplier) {
        this.multiplier = multiplier;
        this.cost = new Mana();
        options.add(new Mana());
    }

    public VariableManaCost(final VariableManaCost manaCost) {
        super(manaCost);
        this.multiplier = manaCost.multiplier;
        if (manaCost.filter != null) {
            this.filter = manaCost.filter.copy();
        }
        this.minX = manaCost.minX;
        this.maxX = manaCost.maxX;
    }

    @Override
    public int convertedManaCost() {
        return 0;
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        payment.add(pool.getMana(filter));
        payment.add(pool.getAllConditionalMana(ability, game, filter));
        pool.payX(ability, game, filter);
    }

    @Override
    public String getText() {
        if (multiplier > 1) {
            StringBuilder symbol = new StringBuilder(multiplier);
            for (int i = 0; i < multiplier; i++) {
                symbol.append("{X}");
            }
            return symbol.toString();
        } else {
            return "{X}";
        }
    }

    @Override
    public VariableManaCost getUnpaid() {
        return this;
    }

    @Override
    public int getAmount() {
        return payment.count() / multiplier;
    }

    @Override
    public void setAmount(int amount) {
        payment.setGeneric(amount);
    }

    @Override
    public boolean testPay(Mana testMana) {
        return true;
    }

    @Override
    public VariableManaCost copy() {
        return new VariableManaCost(this);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return false;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getActionText() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public FilterMana getFilter() {
        return filter;
    }

    public void setFilter(FilterMana filter) {
        this.filter = filter;
    }
}
