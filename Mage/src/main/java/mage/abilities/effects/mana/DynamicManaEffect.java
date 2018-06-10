
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.choices.ChoiceColor;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class DynamicManaEffect extends BasicManaEffect {

    private final DynamicValue amount;
    private final DynamicValue netAmount;
    private String text = null;
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
     * @param oneChoice is all manaTemplate from the same colour or if false the
     * player can choose different colours
     * @param netAmount a dynamic value that calculates the possible available
     * manaTemplate (e.g. if you have to pay by removing counters from source)
     */
    public DynamicManaEffect(Mana mana, DynamicValue amount, String text, boolean oneChoice, DynamicValue netAmount) {
        super(mana);
        this.amount = amount;
        this.text = text;
        this.oneChoice = oneChoice;
        this.netAmount = netAmount;
    }

    public DynamicManaEffect(final DynamicManaEffect effect) {
        super(effect);
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
    public boolean apply(Game game, Ability source) {
        checkToFirePossibleEvents(getMana(game, source), game, source);
        game.getPlayer(source.getControllerId()).getManaPool().addMana(getMana(game, source), game, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (text != null && !text.isEmpty()) {
            return text;
        }
        return super.getText(mode) + " for each " + amount.getMessage();
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana computedMana = new Mana();
        int count;
        if (netMana && netAmount != null) {
            // calculate the maximum available manaTemplate
            count = netAmount.calculate(game, source, this);
        } else {
            count = amount.calculate(game, source, this);
        }

        if (manaTemplate.getBlack() > 0) {
            computedMana.setBlack(count);
        } else if (manaTemplate.getBlue() > 0) {
            computedMana.setBlue(count);
        } else if (manaTemplate.getGreen() > 0) {
            computedMana.setGreen(count);
        } else if (manaTemplate.getRed() > 0) {
            computedMana.setRed(count);
        } else if (manaTemplate.getWhite() > 0) {
            computedMana.setWhite(count);
        } else if (manaTemplate.getColorless() > 0) {
            computedMana.setColorless(count);
        } else if (manaTemplate.getAny() > 0) {
            if (netMana) {
                computedMana.setAny(count);
            } else {
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
            }
        } else {
            computedMana.setGeneric(count);
        }
        return computedMana;
    }

}
