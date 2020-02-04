
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.ManaEffect;
import mage.choices.ChoiceColor;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author North
 */
public class DynamicManaEffect extends ManaEffect {

    private Mana baseMana;
    private final DynamicValue amount;
    private final DynamicValue netAmount;
    private String text;
    private boolean oneChoice;

    public DynamicManaEffect(Mana mana, DynamicValue amount) {
        this(mana, amount, null);
    }

    public DynamicManaEffect(Mana mana, DynamicValue amount, String text) {
        this(mana, amount, text, false);
    }

    public DynamicManaEffect(Mana mana, DynamicValue amount, String text, boolean oneChoice) {
        this(mana, amount, text, oneChoice, null);
    }

    /**
     *
     * @param mana
     * @param amount
     * @param text
     * @param oneChoice is all mana from the same colour or if false the
     * player can choose different colours
     * @param netAmount a dynamic value that calculates the possible available
     * mana (e.g. if you have to pay by removing counters from source)
     */
    public DynamicManaEffect(Mana mana, DynamicValue amount, String text, boolean oneChoice, DynamicValue netAmount) {
        super();
        this.baseMana = mana;
        this.amount = amount;
        this.text = text;
        this.oneChoice = oneChoice;
        this.netAmount = netAmount;
    }

    public DynamicManaEffect(final DynamicManaEffect effect) {
        super(effect);
        this.baseMana = effect.baseMana.copy();
        this.amount = effect.amount.copy();
        this.text = effect.text;
        this.oneChoice = effect.oneChoice;
        if (effect.netAmount != null) {
            this.netAmount = effect.netAmount.copy();
        } else {
            this.netAmount = null;
        }
    }

    @Override
    public DynamicManaEffect copy() {
        return new DynamicManaEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (text != null && !text.isEmpty()) {
            return text;
        }
        return super.getText(mode) + " for each " + amount.getMessage();
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        Mana computedMana = new Mana();
        int count;
        if (netAmount != null) {
            // calculate the maximum available mana
            count = netAmount.calculate(game, source, this);
        } else {
            count = amount.calculate(game, source, this);
        }

        if (baseMana.getBlack() > 0) {
            computedMana.setBlack(count);
        } else if (baseMana.getBlue() > 0) {
            computedMana.setBlue(count);
        } else if (baseMana.getGreen() > 0) {
            computedMana.setGreen(count);
        } else if (baseMana.getRed() > 0) {
            computedMana.setRed(count);
        } else if (baseMana.getWhite() > 0) {
            computedMana.setWhite(count);
        } else if (baseMana.getColorless() > 0) {
            computedMana.setColorless(count);
        } else if (baseMana.getAny() > 0) {
            computedMana.setAny(count);
        } else {
            computedMana.setGeneric(count);
        }
        List<Mana> netMana = new ArrayList<>();
        netMana.add(computedMana);
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana computedMana = new Mana();
        int count = amount.calculate(game, source, this);

        if (baseMana.getBlack() > 0) {
            computedMana.setBlack(count);
        } else if (baseMana.getBlue() > 0) {
            computedMana.setBlue(count);
        } else if (baseMana.getGreen() > 0) {
            computedMana.setGreen(count);
        } else if (baseMana.getRed() > 0) {
            computedMana.setRed(count);
        } else if (baseMana.getWhite() > 0) {
            computedMana.setWhite(count);
        } else if (baseMana.getColorless() > 0) {
            computedMana.setColorless(count);
        } else if (baseMana.getAny() > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                ChoiceColor choiceColor = new ChoiceColor(true);
                for (int i = 0; i < count; i++) {
                    if (!choiceColor.isChosen()) {
                        if (!controller.choose(Outcome.Benefit, choiceColor, game)) {
                            return computedMana;
                        }
                    }
                    choiceColor.increaseMana(computedMana);
                    if (!oneChoice) {
                        choiceColor.clearChoice();
                    }
                }
            }
        } else {
            computedMana.setGeneric(count);
        }
        return computedMana;
    }

}
