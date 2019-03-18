
package mage.abilities.costs.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

public class MonoHybridManaCost extends ManaCostImpl {

    private final ColoredManaSymbol mana;
    private int mana2 = 2;

    public MonoHybridManaCost(ColoredManaSymbol mana) {
        this.mana = mana;
        this.cost = new Mana(mana);
        this.cost.add(Mana.GenericMana(2));
        addColoredOption(mana);
        options.add(Mana.GenericMana(2));
    }

    public MonoHybridManaCost(MonoHybridManaCost manaCost) {
        super(manaCost);
        this.mana = manaCost.mana;
        this.mana2 = manaCost.mana2;
    }

    @Override
    public int convertedManaCost() {
        return 2;
    }

    @Override
    public boolean isPaid() {
        if (paid || isColoredPaid(this.mana)) {
            return true;
        }
        return isColorlessPaid(this.mana2);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        if (!assignColored(ability, game, pool, mana, costToPay)) {
            assignGeneric(ability, game, pool, mana2, costToPay);
        }
    }

    @Override
    public String getText() {
        return "{2/" + mana.toString() + '}';
    }

    @Override
    public MonoHybridManaCost getUnpaid() {
        return this;
    }

    @Override
    public boolean testPay(Mana testMana) {
        switch (mana) {
            case B:
                if (testMana.getBlack() > 0) {
                    return true;
                }
            case U:
                if (testMana.getBlue() > 0) {
                    return true;
                }
            case R:
                if (testMana.getRed() > 0) {
                    return true;
                }
            case W:
                if (testMana.getWhite() > 0) {
                    return true;
                }
            case G:
                if (testMana.getGreen() > 0) {
                    return true;
                }
        }
        return testMana.count() > 0;
    }

    @Override
    public MonoHybridManaCost copy() {
        return new MonoHybridManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return mana == coloredManaSymbol;
    }

    public ColoredManaSymbol getManaColor() {
        return mana;
    }

    @Override
    public List<Mana> getManaOptions() {
        List<Mana> manaList = new ArrayList<>();
        manaList.add(new Mana(mana));
        manaList.add(Mana.GenericMana(2));
        return manaList;
    }
}
