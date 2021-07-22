package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

import java.util.ArrayList;
import java.util.List;

public class MonoHybridManaCost extends ManaCostImpl {

    private final ColoredManaSymbol manaColor;
    private int manaGeneric;

    public MonoHybridManaCost(ColoredManaSymbol manaColor) {
        this(manaColor, 2);
    }

    public MonoHybridManaCost(ColoredManaSymbol manaColor, int genericAmount) {
        this.manaColor = manaColor;
        this.manaGeneric = genericAmount;
        this.cost = new Mana(manaColor);
        this.cost.add(Mana.GenericMana(genericAmount));
        addColoredOption(manaColor);
        options.add(Mana.GenericMana(genericAmount));
    }

    public MonoHybridManaCost(MonoHybridManaCost manaCost) {
        super(manaCost);
        this.manaColor = manaCost.manaColor;
        this.manaGeneric = manaCost.manaGeneric;
    }

    @Override
    public int manaValue() {
        // from wiki: A card with monocolored hybrid mana symbols in its mana cost has a converted mana cost equal to
        // the highest possible cost it could be played for. Its converted mana cost never changes.
        return Math.max(manaGeneric, 1);
    }

    @Override
    public boolean isPaid() {
        if (paid || isColoredPaid(this.manaColor)) {
            return true;
        }
        return isColorlessPaid(this.manaGeneric);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        if (!assignColored(ability, game, pool, manaColor, costToPay)) {
            assignGeneric(ability, game, pool, manaGeneric, null, costToPay);
        }
    }

    @Override
    public String getText() {
        return "{" + manaGeneric + "/" + manaColor.toString() + '}';
    }

    @Override
    public MonoHybridManaCost getUnpaid() {
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
        return testMana.count() > 0;
    }

    @Override
    public MonoHybridManaCost copy() {
        return new MonoHybridManaCost(this);
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
        manaList.add(Mana.GenericMana(manaGeneric));
        return manaList;
    }
}
