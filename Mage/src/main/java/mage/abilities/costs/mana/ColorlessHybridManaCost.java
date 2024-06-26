package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

import java.util.ArrayList;
import java.util.List;

public class ColorlessHybridManaCost extends ManaCostImpl {

    private final ColoredManaSymbol manaColor;

    public ColorlessHybridManaCost(ColoredManaSymbol manaColor) {
        this.manaColor = manaColor;
        this.cost = new Mana(manaColor);
        this.cost.add(Mana.ColorlessMana(1));
        addColoredOption(manaColor);
        options.add(Mana.ColorlessMana(1));
    }

    public ColorlessHybridManaCost(ColorlessHybridManaCost manaCost) {
        super(manaCost);
        this.manaColor = manaCost.manaColor;
    }

    @Override
    public int manaValue() {
        return 1;
    }

    @Override
    public boolean isPaid() {
        return paid || isColoredPaid(this.manaColor) || isColorlessPaid(1);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        // Assign colorless first in an attempt to avoid things like pain lands and talismans causing damage
        if (!assignColorless(ability, game, pool, 1, costToPay)) {
            assignColored(ability, game, pool, manaColor, costToPay);
        }
    }

    @Override
    public String getText() {
        return "{C/" + manaColor.toString() + '}';
    }

    @Override
    public ColorlessHybridManaCost getUnpaid() {
        return this;
    }

    @Override
    public boolean testPay(Mana testMana) {
        switch (manaColor) {
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
        return testMana.getColorless() > 0;
    }

    @Override
    public ColorlessHybridManaCost copy() {
        return new ColorlessHybridManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return manaColor == coloredManaSymbol;
    }

    public ColoredManaSymbol getManaColor() {
        return manaColor;
    }

    @Override
    public List<Mana> getManaOptions() {
        List<Mana> manaList = new ArrayList<>();
        manaList.add(new Mana(manaColor));
        manaList.add(Mana.ColorlessMana(1));
        return manaList;
    }
}
