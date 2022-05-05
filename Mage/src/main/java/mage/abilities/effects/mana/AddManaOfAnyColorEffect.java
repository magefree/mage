package mage.abilities.effects.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddManaOfAnyColorEffect extends BasicManaEffect {

    protected final int amount;
    protected final DynamicValue netAmount;
    protected final ArrayList<Mana> netMana = new ArrayList<>();
    protected final boolean setFlag;

    public AddManaOfAnyColorEffect() {
        this(1);
    }

    public AddManaOfAnyColorEffect(int amount) {
        this(amount, false);
    }

    public AddManaOfAnyColorEffect(int amount, boolean setFlag) {
        this(amount, null, setFlag);
    }

    public AddManaOfAnyColorEffect(int amount, DynamicValue netAmount, boolean setFlag) {
        super(new Mana(0, 0, 0, 0, 0, 0, amount, 0));
        this.amount = amount;
        this.netAmount = netAmount;
        netMana.add(Mana.AnyMana(amount));
        this.staticText = "add " + CardUtil.numberToText(amount) + " mana of any " + (amount > 1 ? "one " : "") + "color";
        this.setFlag = setFlag;
    }

    public AddManaOfAnyColorEffect(final AddManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.netMana.addAll(effect.netMana);
        this.setFlag = effect.setFlag;
        if (effect.netAmount == null) {
            this.netAmount = null;
        } else {
            this.netAmount = effect.netAmount.copy();
        }

    }

    @Override
    public AddManaOfAnyColorEffect copy() {
        return new AddManaOfAnyColorEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            if (netAmount != null) {
                int count = netAmount.calculate(game, source, this);
                Mana mana = new Mana();
                mana.setAny(count * amount);
                ArrayList<Mana> possibleNetMana = new ArrayList<>();
                possibleNetMana.add(mana);
                return possibleNetMana;
            }
        }
        return new ArrayList<>(this.netMana);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                String mes = String.format("Select a color of mana to add %d of it", this.amount);
                if (mes != null) {
                    ChoiceColor choice = new ChoiceColor(true, mes, game.getObject(source));
                    if (controller.choose(outcome, choice, game)) {
                        if (choice.getColor() != null) {
                            Mana mana = choice.getMana(amount);
                            mana.setFlag(setFlag);
                            return mana;
                        }
                    }
                }
            }
        }
        return new Mana();
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Mana getManaTemplate() {
        return Mana.AnyMana(amount);
    }
}
