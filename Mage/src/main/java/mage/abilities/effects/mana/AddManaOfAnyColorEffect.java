
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddManaOfAnyColorEffect extends BasicManaEffect {

    protected int amount;

    public AddManaOfAnyColorEffect() {
        this(1);
    }

    public AddManaOfAnyColorEffect(final int amount) {
        super(new Mana(0, 0, 0, 0, 0, 0, amount, 0));
        this.amount = amount;
        this.staticText = new StringBuilder("add ")
                .append(CardUtil.numberToText(amount))
                .append(" mana of any ")
                .append(amount > 1 ? "one " : "")
                .append("color").toString();
    }

    public AddManaOfAnyColorEffect(final AddManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AddManaOfAnyColorEffect copy() {
        return new AddManaOfAnyColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            String mes = String.format("Select color of %d mana to add it", this.amount);
            ChoiceColor choice = new ChoiceColor(true, mes, game.getObject(source.getSourceId()));
            if (controller.choose(outcome, choice, game)) {
                if (choice.getColor() == null) {
                    return false;
                }
                Mana createdMana = choice.getMana(amount);
                if (createdMana != null) {
                    checkToFirePossibleEvents(createdMana, game, source);
                    controller.getManaPool().addMana(createdMana, game, source);
                }
                return true;
            }
        }
        return false;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Mana getManaTemplate() {
        return new Mana(0, 0, 0, 0, 0, 0, amount, 0);
    }

}
