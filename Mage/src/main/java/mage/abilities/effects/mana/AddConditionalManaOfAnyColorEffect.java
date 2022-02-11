package mage.abilities.effects.mana;

import java.util.ArrayList;
import java.util.List;
import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.choices.ChoiceColor;
import mage.constants.MultiAmountType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 * @author noxx
 */
public class AddConditionalManaOfAnyColorEffect extends ManaEffect {

    private static final Logger logger = Logger.getLogger(AddConditionalManaOfAnyColorEffect.class);

    private final DynamicValue amount;
    private final DynamicValue netAmount;
    private final ConditionalManaBuilder manaBuilder;
    private final boolean oneChoice;

    public AddConditionalManaOfAnyColorEffect(int amount, ConditionalManaBuilder manaBuilder) {
        this(StaticValue.get(amount), StaticValue.get(amount), manaBuilder);
    }

    public AddConditionalManaOfAnyColorEffect(DynamicValue amount, DynamicValue netAmount, ConditionalManaBuilder manaBuilder) {
        this(amount, netAmount, manaBuilder, true);
    }

    public AddConditionalManaOfAnyColorEffect(DynamicValue amount, DynamicValue netAmount, ConditionalManaBuilder manaBuilder, boolean oneChoice) {
        super();
        this.amount = amount;
        this.netAmount = netAmount;
        this.manaBuilder = manaBuilder;
        this.oneChoice = oneChoice;
        //
        staticText = "Add "
                + (amount instanceof StaticValue ? (CardUtil.numberToText(amount.toString())) : "X")
                + " mana "
                + (oneChoice || (amount instanceof StaticValue && (amount.toString()).equals("1"))
                ? "of any" + (amount instanceof StaticValue && (amount.toString()).equals("1") ? "" : " one") + " color"
                : "in any combination of colors")
                + ". " + manaBuilder.getRule();
    }

    public AddConditionalManaOfAnyColorEffect(final AddConditionalManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.netAmount = effect.netAmount;
        this.manaBuilder = effect.manaBuilder;
        this.oneChoice = effect.oneChoice;
    }

    @Override
    public AddConditionalManaOfAnyColorEffect copy() {
        return new AddConditionalManaOfAnyColorEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            if (game.inCheckPlayableState()) {
                int amountAvailableMana = netAmount.calculate(game, source, this);
                if (amountAvailableMana > 0) {
                    netMana.add(manaBuilder.setMana(Mana.AnyMana(amountAvailableMana), source, game).build());
                }
            } else {
                int amountOfManaLeft = amount.calculate(game, source, this);
                if (amountOfManaLeft > 0) {
                    netMana.add(Mana.AnyMana(amountOfManaLeft));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int value = amount.calculate(game, source, this);
                if (value > 0) {
                    if (oneChoice || value == 1) {
                        ChoiceColor choice = new ChoiceColor(true);
                        controller.choose(outcome, choice, game);
                        if (choice.getChoice() == null) {
                            return null;
                        }
                        return new ConditionalMana(manaBuilder.setMana(choice.getMana(value), source, game).build());
                    }
                    List<String> manaStrings = new ArrayList<>(5);
                    manaStrings.add("W");
                    manaStrings.add("U");
                    manaStrings.add("B");
                    manaStrings.add("R");
                    manaStrings.add("G");
                    List<Integer> choices = controller.getMultiAmount(this.outcome, manaStrings, 0, value, MultiAmountType.MANA, game);
                    Mana mana = new Mana(choices.get(0), choices.get(1), choices.get(2), choices.get(3), choices.get(4), 0, 0, 0);
                    return new ConditionalMana(manaBuilder.setMana(mana, source, game).build());
                }
            }
        }
        return new Mana();
    }
}
