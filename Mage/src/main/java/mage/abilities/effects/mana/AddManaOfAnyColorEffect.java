package mage.abilities.effects.mana;

import java.util.ArrayList;
import java.util.List;
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

    protected final int amount;
    protected final ArrayList<Mana> netMana = new ArrayList<>();
    protected final boolean setFlag;

    public AddManaOfAnyColorEffect() {
        this(1);
    }

    public AddManaOfAnyColorEffect(int amount) {
        this(amount, false);
    }

    public AddManaOfAnyColorEffect(int amount, boolean setFlag) {
        super(new Mana(0, 0, 0, 0, 0, 0, amount, 0));
        this.amount = amount;
        netMana.add(Mana.GreenMana(amount));
        netMana.add(Mana.BlueMana(amount));
        netMana.add(Mana.BlackMana(amount));
        netMana.add(Mana.WhiteMana(amount));
        netMana.add(Mana.RedMana(amount));
        this.staticText = "add " + CardUtil.numberToText(amount) + " mana of any " + (amount > 1 ? "one " : "") + "color";
        this.setFlag = setFlag;
    }

    public AddManaOfAnyColorEffect(final AddManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.netMana.addAll(effect.netMana);
        this.setFlag = effect.setFlag;
    }

    @Override
    public AddManaOfAnyColorEffect copy() {
        return new AddManaOfAnyColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return netMana;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            String mes = String.format("Select color of %d mana to add it", this.amount);
            ChoiceColor choice = new ChoiceColor(true, mes, game.getObject(source.getSourceId()));
            if (controller.choose(outcome, choice, game)) {
                if (choice.getColor() != null) {
                    Mana mana = choice.getMana(amount);
                    mana.setFlag(setFlag);
                    return mana;
                }
            }
        }
        return null;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Mana getManaTemplate() {
        return new Mana(0, 0, 0, 0, 0, 0, amount, 0);
    }

}
