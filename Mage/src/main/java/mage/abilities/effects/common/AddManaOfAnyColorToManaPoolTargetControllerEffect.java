package mage.abilities.effects.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * Created by Galatolol
 */
public class AddManaOfAnyColorToManaPoolTargetControllerEffect extends ManaEffect {

    public AddManaOfAnyColorToManaPoolTargetControllerEffect(String textManaPoolOwner) {
        super();
        this.staticText = (textManaPoolOwner.equals("his or her")?"that player adds ":"add ") + "one mana of any color" + " to " + textManaPoolOwner + " mana pool";
    }

    public AddManaOfAnyColorToManaPoolTargetControllerEffect(final AddManaOfAnyColorToManaPoolTargetControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!player.choose(outcome, choice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            Mana mana = choice.getMana(1);
            if (mana != null) {
                checkToFirePossibleEvents(mana, game, source);
                player.getManaPool().addMana(mana, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public AddManaOfAnyColorToManaPoolTargetControllerEffect copy() {
        return new AddManaOfAnyColorToManaPoolTargetControllerEffect(this);
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}

