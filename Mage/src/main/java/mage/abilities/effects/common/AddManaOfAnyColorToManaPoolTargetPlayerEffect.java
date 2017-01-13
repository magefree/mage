package mage.abilities.effects.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * Created by Galatolol
 */
public class AddManaOfAnyColorToManaPoolTargetPlayerEffect extends ManaEffect {

    public AddManaOfAnyColorToManaPoolTargetPlayerEffect(String textManaPoolOwner) {
        super();
        this.staticText = (textManaPoolOwner.equals("his or her")?"that player adds ":"add ") + "one mana of any color" + " to " + textManaPoolOwner + " mana pool";
    }

    public AddManaOfAnyColorToManaPoolTargetPlayerEffect(final AddManaOfAnyColorToManaPoolTargetPlayerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
        Player player = game.getPlayer(playerId);
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
    public AddManaOfAnyColorToManaPoolTargetPlayerEffect copy() {
        return new AddManaOfAnyColorToManaPoolTargetPlayerEffect(this);
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}

