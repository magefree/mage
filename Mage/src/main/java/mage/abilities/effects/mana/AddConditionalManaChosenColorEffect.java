package mage.abilities.effects.mana;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author Susucr
 */
public class AddConditionalManaChosenColorEffect extends ManaEffect {

    private ObjectColor chosenColorInfo = null;
    private final ConditionalManaBuilder manaBuilder;
    private final DynamicValue amount;

    public AddConditionalManaChosenColorEffect(int amount, ConditionalManaBuilder manaBuilder) {
        this(StaticValue.get(amount), manaBuilder);
    }

    public AddConditionalManaChosenColorEffect(DynamicValue amount, ConditionalManaBuilder manaBuilder) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        String value = (amount instanceof StaticValue
                ? CardUtil.numberToText(((StaticValue) amount).getValue())
                : amount.toString());
        staticText = "Add " + value + " mana of the chosen color. " + manaBuilder.getRule();
    }

    private AddConditionalManaChosenColorEffect(final AddConditionalManaChosenColorEffect effect) {
        super(effect);
        this.chosenColorInfo = effect.chosenColorInfo;
        this.manaBuilder = effect.manaBuilder;
        this.amount = effect.amount;
    }

    @Override
    public AddConditionalManaChosenColorEffect copy() {
        return new AddConditionalManaChosenColorEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            int value = amount.calculate(game, source, this);
            if (color != null && value > 0) {
                this.chosenColorInfo = color;
                return manaBuilder.setMana(
                        new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)), value),
                        source, game
                ).build();
            }
        }
        return new Mana();
    }
}
