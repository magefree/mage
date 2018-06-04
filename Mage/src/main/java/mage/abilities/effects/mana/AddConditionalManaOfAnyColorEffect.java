
package mage.abilities.effects.mana;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.choices.ChoiceColor;
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
    private final ConditionalManaBuilder manaBuilder;
    private final boolean oneChoice;

    public AddConditionalManaOfAnyColorEffect(int amount, ConditionalManaBuilder manaBuilder) {
        this(new StaticValue(amount), manaBuilder);
    }

    public AddConditionalManaOfAnyColorEffect(DynamicValue amount, ConditionalManaBuilder manaBuilder) {
        this(amount, manaBuilder, true);
    }

    public AddConditionalManaOfAnyColorEffect(DynamicValue amount, ConditionalManaBuilder manaBuilder, boolean oneChoice) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        this.oneChoice = oneChoice;
        //
        staticText = "Add "
                + (amount instanceof StaticValue ? (CardUtil.numberToText(((StaticValue) amount).toString())) : "")
                + " mana "
                + (oneChoice || (amount instanceof StaticValue && (((StaticValue) amount).toString()).equals("1"))
                ? "of any" + (amount instanceof StaticValue && (((StaticValue) amount).toString()).equals("1") ? "" : " one") + " color"
                : "in any combination of colors")
                + ". " + manaBuilder.getRule();
    }

    public AddConditionalManaOfAnyColorEffect(final AddConditionalManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaBuilder = effect.manaBuilder;
        this.oneChoice = effect.oneChoice;
    }

    @Override
    public AddConditionalManaOfAnyColorEffect copy() {
        return new AddConditionalManaOfAnyColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = getMana(game, source);
            if (mana != null) {
                checkToFirePossibleEvents(mana, game, source);
                controller.getManaPool().addMana(mana, game, source);
            } else {
                logger.error("There was no mana created: " + source.getSourceObject(game).getName() + " - Ability: " + source.getRule());
            }
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }
        ConditionalMana mana = null;
        int value = amount.calculate(game, source, this);
        ChoiceColor choice = new ChoiceColor(true);
        for (int i = 0; i < value; i++) {
            if (choice.getChoice() == null) {
                controller.choose(outcome, choice, game);
            }
            if (choice.getChoice() == null) {
                return null;
            }
            if (oneChoice) {
                mana = new ConditionalMana(manaBuilder.setMana(choice.getMana(value), source, game).build());
                break;
            } else {
                if (mana == null) {
                    mana = new ConditionalMana(manaBuilder.setMana(choice.getMana(1), source, game).build());
                } else {
                    mana.add(choice.getMana(1));
                }
                choice.clearChoice();
            }
        }

        return mana;

    }
}
