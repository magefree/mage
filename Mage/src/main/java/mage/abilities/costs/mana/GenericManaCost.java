package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

public class GenericManaCost extends ManaCostImpl {

    protected int mana;

    /**
     * warning, use ManaUtil.createManaCost to create generic cost
     */
    public GenericManaCost(int mana) {
        this.mana = mana;
        this.cost = Mana.GenericMana(mana);
        this.options.addMana(Mana.GenericMana(mana));
    }

    protected GenericManaCost(GenericManaCost manaCost) {
        super(manaCost);
        this.mana = manaCost.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public int manaValue() {
        return mana;
    }

    @Override
    public boolean isPaid() {
        if (paid) {
            return true;
        }
        return this.isColorlessPaid(mana);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costsToPay) {
        this.assignGeneric(ability, game, pool, mana, null, costsToPay);
    }

    @Override
    public String getText() {
        return '{' + Integer.toString(mana) + '}';
    }

    @Override
    public GenericManaCost getUnpaid() {
        GenericManaCost unpaid = new GenericManaCost(mana - this.payment.count());
        if (sourceFilter != null) {
            unpaid.setSourceFilter(sourceFilter);
        }
        return unpaid;
    }

    @Override
    public boolean testPay(Mana testMana) {
        return testMana.count() > 0;
    }

    @Override
    public GenericManaCost copy() {
        return new GenericManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return false;
    }
}
