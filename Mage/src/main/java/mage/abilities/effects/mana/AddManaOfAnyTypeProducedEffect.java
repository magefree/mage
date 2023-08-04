package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class AddManaOfAnyTypeProducedEffect extends ManaEffect {

    public AddManaOfAnyTypeProducedEffect() {
        super();
        staticText = "that player adds one mana of any type that land produced";
    }

    protected AddManaOfAnyTypeProducedEffect(final AddManaOfAnyTypeProducedEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("tappedPermanent");
        if (permanent != null) {
            return game.getPlayer(permanent.getControllerId());
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Mana types = (Mana) this.getValue("mana");
        if (types == null) {
            return netMana;
        }
        if (types.getBlack() > 0) {
            netMana.add(Mana.BlackMana(1));
        }
        if (types.getRed() > 0) {
            netMana.add(Mana.RedMana(1));
        }
        if (types.getBlue() > 0) {
            netMana.add(Mana.BlueMana(1));
        }
        if (types.getGreen() > 0) {
            netMana.add(Mana.GreenMana(1));
        }
        if (types.getWhite() > 0) {
            netMana.add(Mana.WhiteMana(1));
        }
        if (types.getColorless() > 0) {
            netMana.add(Mana.ColorlessMana(1));
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana newMana = new Mana();
        if (game == null) {
            return newMana;
        }
        Permanent permanent = (Permanent) this.getValue("tappedPermanent");
        Mana types = (Mana) this.getValue("mana");
        if (permanent == null || types == null) {
            return newMana;
        }
        Player targetController = game.getPlayer(permanent.getControllerId());
        if (targetController == null) {
            return newMana;
        }

        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick the type of mana to produce");
        if (types.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        if (types.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if (types.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if (types.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if (types.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if (types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }

        if (choice.getChoices().isEmpty()) {
            return newMana;
        }
        if (choice.getChoices().size() != 1
                && !targetController.choose(outcome, choice, game)) {
            return newMana;
        }
        choice.setChoice(choice.getChoices().iterator().next());

        switch (choice.getChoice()) {
            case "White":
                newMana.setWhite(1);
                break;
            case "Blue":
                newMana.setBlue(1);
                break;
            case "Black":
                newMana.setBlack(1);
                break;
            case "Red":
                newMana.setRed(1);
                break;
            case "Green":
                newMana.setGreen(1);
                break;
            case "Colorless":
                newMana.setColorless(1);
                break;
        }
        return newMana;
    }

    @Override
    public AddManaOfAnyTypeProducedEffect copy() {
        return new AddManaOfAnyTypeProducedEffect(this);
    }
}
