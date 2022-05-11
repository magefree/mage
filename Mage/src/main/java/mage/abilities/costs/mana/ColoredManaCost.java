
package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

public class ColoredManaCost extends ManaCostImpl {

    protected ColoredManaSymbol mana;

    public ColoredManaCost(ColoredManaSymbol mana) {
        this.mana = mana;
        this.cost = new Mana(mana);
        addColoredOption(mana);
    }

    public ColoredManaCost(ColoredManaCost manaCost) {
        super(manaCost);
        this.mana = manaCost.mana;
    }

    @Override
    public int manaValue() {
        return 1;
    }

    @Override
    public boolean isPaid() {
        if (paid) {
            return true;
        }
        return this.isColoredPaid(mana);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        this.assignColored(ability, game, pool, mana, costToPay);
    }

    @Override
    public String getText() {
        return '{' + mana.toString() + (this.phyrexian ? "/P" : "") + '}';
    }

    @Override
    public ColoredManaCost getUnpaid() {
        return this;
    }

    @Override
    public boolean testPay(Mana testMana) {
        if (testMana.getAny() > 0) {
            return true;
        }
        switch (mana) {
            case B:
                return testMana.getBlack() > 0;
            case U:
                return testMana.getBlue() > 0;
            case R:
                return testMana.getRed() > 0;
            case W:
                return testMana.getWhite() > 0;
            case G:
                return testMana.getGreen() > 0;
        }
        return false;
    }

    @Override
    public ColoredManaCost copy() {
        return new ColoredManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return mana == coloredManaSymbol;
    }

}
