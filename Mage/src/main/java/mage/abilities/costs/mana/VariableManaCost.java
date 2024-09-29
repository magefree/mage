package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostType;
import mage.constants.ColoredManaSymbol;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.ManaPool;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public final class VariableManaCost extends ManaCostImpl implements VariableCost {

    // variable mana cost usage on 2019-06-20:
    // 1. as X value in spell/ability cast (announce X, set VariableManaCost as paid and add generic mana to pay instead)
    // 2. as X value in direct pay (X already announced, cost is unpaid, need direct pay)

    protected VariableCostType costType;
    protected int xInstancesCount; // number of {X} instances in cost like {X} or {X}{X}
    protected int xValue = 0; // final X value after announce and replace events
    protected int xPay = 0; // final/total need pay after announce and replace events (example: {X}{X}, X=3, xPay = 6)
    protected boolean wasAnnounced = false;

    protected FilterMana filter; // mana filter that can be used for that cost
    protected int minX = 0;
    protected int maxX = Integer.MAX_VALUE;

    public VariableManaCost(VariableCostType costType) {
        this(costType, 1);
    }

    public VariableManaCost(VariableCostType costType, int xInstancesCount) {
        this.costType = costType;
        this.xInstancesCount = xInstancesCount;
        this.cost = new Mana();
        options.add(new Mana());
    }

    protected VariableManaCost(final VariableManaCost manaCost) {
        super(manaCost);
        this.costType = manaCost.costType;
        this.xInstancesCount = manaCost.xInstancesCount;
        this.xValue = manaCost.xValue;
        this.xPay = manaCost.xPay;
        this.wasAnnounced = manaCost.wasAnnounced;
        if (manaCost.filter != null) {
            this.filter = manaCost.filter.copy();
        }
        this.minX = manaCost.minX;
        this.maxX = manaCost.maxX;
    }

    @Override
    public int manaValue() {
        return 0;
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        // X mana cost always pays as generic mana
        this.assignGeneric(ability, game, pool, xPay, filter, costToPay);
    }

    @Override
    public String getText() {
        if (xInstancesCount > 1) {
            StringBuilder symbol = new StringBuilder(xInstancesCount);
            for (int i = 0; i < xInstancesCount; i++) {
                symbol.append("{X}");
            }
            return symbol.toString();
        } else {
            return "{X}";
        }
    }

    @Override
    public boolean isPaid() {
        if (!wasAnnounced) return false;
        if (paid) return true;

        return this.isColorlessPaid(xPay);
    }

    @Override
    public VariableManaCost getUnpaid() {
        return this;
    }

    @Override
    public int getAmount() {
        // must return X value
        return this.xValue;
    }

    @Override
    public void setAmount(int xValue, int xPay, boolean isPayed) {
        // xPay is total pay value (X * instances)
        this.xValue = xValue;
        this.xPay = xPay;
        if (isPayed) {
            payment.setGeneric(xPay);
        }
        this.wasAnnounced = true;
    }

    @Override
    public boolean testPay(Mana testMana) {
        return true; // TODO: need rework to generic mana style?
    }

    @Override
    public VariableManaCost copy() {
        return new VariableManaCost(this);
    }

    public int getXInstancesCount() {
        return this.xInstancesCount;
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
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getActionText() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public FilterMana getFilter() {
        return filter;
    }

    public void setFilter(FilterMana filter) {
        this.filter = filter;
    }

    @Override
    public VariableCostType getCostType() {
        return this.costType;
    }

    @Override
    public void setCostType(VariableCostType costType) {
        this.costType = costType;
    }
}
