
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AddManaOfAnyTypeProducedEffect extends ManaEffect {

    public AddManaOfAnyTypeProducedEffect() {
        super();
        staticText = "that player adds one mana of any type that land produced";
    }

    public AddManaOfAnyTypeProducedEffect(final AddManaOfAnyTypeProducedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player targetController = game.getPlayer(permanent.getControllerId());
            if (targetController == null) {
                return false;
            }
            checkToFirePossibleEvents(getMana(game, source), game, source);
            targetController.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {
            return null;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player targetController = game.getPlayer(permanent.getControllerId());
            if (targetController == null) {
                return null;
            }
            Mana types = (Mana) this.getValue("mana");
            Choice choice = new ChoiceColor(true);
            choice.getChoices().clear();
            choice.setMessage("Pick the type of mana to produce");
            if (types.getBlack() > 0) {
                choice.getChoices().add("Black");
            }
            if (types.getRed() > 0) {
                choice.getChoices().add("Red");
            }
            if (types.getBlue() > 0) {
                choice.getChoices().add("Blue");
            }
            if (types.getGreen() > 0) {
                choice.getChoices().add("Green");
            }
            if (types.getWhite() > 0) {
                choice.getChoices().add("White");
            }
            if (types.getColorless() > 0) {
                choice.getChoices().add("Colorless");
            }
            Mana newMana = new Mana();
            if (!choice.getChoices().isEmpty()) {
                if (choice.getChoices().size() == 1) {
                    choice.setChoice(choice.getChoices().iterator().next());
                } else {
                    if (!targetController.choose(outcome, choice, game)) {
                        return null;
                    }
                }

                switch (choice.getChoice()) {
                    case "Black":
                        newMana.setBlack(1);
                        break;
                    case "Blue":
                        newMana.setBlue(1);
                        break;
                    case "Red":
                        newMana.setRed(1);
                        break;
                    case "Green":
                        newMana.setGreen(1);
                        break;
                    case "White":
                        newMana.setWhite(1);
                        break;
                    case "Colorless":
                        newMana.setColorless(1);
                        break;
                }
            }
            return newMana;
        }
        return null;
    }

    @Override
    public AddManaOfAnyTypeProducedEffect copy() {
        return new AddManaOfAnyTypeProducedEffect(this);
    }

}
