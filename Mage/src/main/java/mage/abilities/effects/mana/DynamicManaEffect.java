package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.choices.ChoiceColor;
import mage.constants.MultiAmountType;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mage.constants.ManaType;

/**
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
     * @param mana
     * @param amount
     * @param text
     * @param oneChoice is all mana from the same colour or if false the
     *                  player can choose different colours
     * @param netAmount a dynamic value that calculates the possible available
     *                  mana (e.g. if you have to pay by removing counters from source)
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
        return "add " + baseMana.toString() + " for each " + amount.getMessage();
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
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
        netMana.add(computedMana);
        return netMana;
    }

    @Override
    public Set<ManaType> getProducableManaTypes(Game game, Ability source) {
        return ManaType.getManaTypesFromManaList(baseMana);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana computedMana = new Mana();
        if (game == null) {
            return computedMana;
        }
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
            if (controller != null && count > 0) {
                if (oneChoice || count == 1) {
                    ChoiceColor choice = new ChoiceColor(true);
                    controller.choose(outcome, choice, game);
                    if (choice.getChoice() == null) {
                        return computedMana;
                    }
                    computedMana.add(choice.getMana(count));
                } else {
                    List<String> manaStrings = new ArrayList<>(5);
                    manaStrings.add("W");
                    manaStrings.add("U");
                    manaStrings.add("B");
                    manaStrings.add("R");
                    manaStrings.add("G");
                    List<Integer> choices = controller.getMultiAmount(this.outcome, manaStrings, count, count, MultiAmountType.MANA, game);
                    computedMana.add(new Mana(choices.get(0), choices.get(1), choices.get(2), choices.get(3), choices.get(4), 0, 0, 0));
                }
            }
        } else {
            computedMana.setGeneric(count);
        }
        return computedMana;
    }

}
