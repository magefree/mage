package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
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

    public AddManaOfAnyTypeProducedEffect(final AddManaOfAnyTypeProducedEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            return game.getPlayer(permanent.getControllerId());
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Mana types = (Mana) this.getValue("mana"); // TODO: will not work until TriggeredManaAbility fix (see TriggeredManaAbilityMustGivesExtraManaOptions test)
        if (types != null) {
            netMana.add(types.copy());
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana newMana = new Mana();
        if (game != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                Player targetController = game.getPlayer(permanent.getControllerId());
                Mana types = (Mana) this.getValue("mana");
                if (targetController == null || types == null) {
                    return newMana;
                }

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

                if (!choice.getChoices().isEmpty()) {
                    if (choice.getChoices().size() == 1) {
                        choice.setChoice(choice.getChoices().iterator().next());
                    } else {
                        if (!targetController.choose(outcome, choice, game)) {
                            return newMana;
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
            }
        }
        return newMana;
    }

    @Override
    public AddManaOfAnyTypeProducedEffect copy() {
        return new AddManaOfAnyTypeProducedEffect(this);
    }

}
