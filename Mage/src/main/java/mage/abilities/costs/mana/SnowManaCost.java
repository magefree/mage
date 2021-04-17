package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.constants.SuperType;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.players.ManaPool;

public class SnowManaCost extends ManaCostImpl {

    private static final FilterObject filter = new FilterObject("Snow object");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public SnowManaCost() {
        this.cost = Mana.GenericMana(1);
        this.options.addMana(Mana.GenericMana(1));
        this.setSourceFilter(filter);
    }

    public SnowManaCost(SnowManaCost manaCost) {
        super(manaCost);
    }

    @Override
    public int manaValue() {
        return 1;
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        this.assignGeneric(ability, game, pool, 1, null, costToPay);
    }

    @Override
    public boolean isPaid() {
        if (paid) {
            return true;
        }
        return this.isColorlessPaid(1);
    }

    @Override
    public String getText() {
        return "{S}";
    }

    @Override
    public SnowManaCost getUnpaid() {
        return this;
    }

    @Override
    public boolean testPay(Mana testMana) {
        return testMana.count() > 0;
    }

    @Override
    public SnowManaCost copy() {
        return new SnowManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return false;
    }

}
