package mage.abilities.effects.mana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

public class BasicManaEffect extends ManaEffect {

    private final Mana manaTemplate; // This field must not become directly accessible outside this class
    private final DynamicValue netAmount;

    public BasicManaEffect(Mana mana) {
        this(mana, null);
    }

    public BasicManaEffect(Mana mana, DynamicValue netAmount) {
        super();
        this.manaTemplate = mana;
        staticText = "add " + mana.toString();
        this.netAmount = netAmount;
    }

    public BasicManaEffect(ConditionalMana conditionalMana) {
        this(conditionalMana, null);
    }

    public BasicManaEffect(ConditionalMana conditionalMana, DynamicValue netAmount) {
        super();
        this.manaTemplate = conditionalMana;
        staticText = "add " + manaTemplate.toString() + " " + conditionalMana.getDescription();
        this.netAmount = netAmount;
    }

    protected BasicManaEffect(final BasicManaEffect effect) {
        super(effect);
        this.manaTemplate = effect.manaTemplate; // Not copying for performance reasons. Never modified within the class.
        this.netAmount = effect.netAmount;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState() && netAmount != null) {
            // calculate the maximum available mana
            int count = netAmount.calculate(game, source, this);
            Mana computedMana = manaTemplate.copy(); // Copy to get condition
            if (count > 0) {
                if (manaTemplate.getBlack() > 0) {
                    computedMana.setBlack(count * manaTemplate.getBlack());
                }
                if (manaTemplate.getBlue() > 0) {
                    computedMana.setBlue(count * manaTemplate.getBlue());
                }
                if (manaTemplate.getGreen() > 0) {
                    computedMana.setGreen(count * manaTemplate.getGreen());
                }
                if (manaTemplate.getRed() > 0) {
                    computedMana.setRed(count * manaTemplate.getRed());
                }
                if (manaTemplate.getWhite() > 0) {
                    computedMana.setWhite(count * manaTemplate.getWhite());
                }
                if (manaTemplate.getColorless() > 0) {
                    computedMana.setColorless(count * manaTemplate.getColorless());
                }
                if (manaTemplate.getAny() > 0) {
                    throw new IllegalArgumentException("BasicManaEffect does not support {Any} mana!");
                }
                if (manaTemplate.getGeneric() > 0) {
                    computedMana.setGeneric(count * manaTemplate.getGeneric());
                }
            }
            return new ArrayList<>(Arrays.asList(computedMana));
        }
        return super.getNetMana(game, source);
    }

    @Override
    public BasicManaEffect copy() {
        return new BasicManaEffect(this);
    }

    public Mana getManaTemplate() {
        return manaTemplate.copy(); // Copy is needed here to prevent unintentional modification
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return manaTemplate.copy();
    }

}
