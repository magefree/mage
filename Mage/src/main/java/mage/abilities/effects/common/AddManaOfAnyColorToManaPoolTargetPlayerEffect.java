package mage.abilities.effects.common;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * Created by Galatolol
 */
public class AddManaOfAnyColorToManaPoolTargetPlayerEffect extends ManaEffect {

    public AddManaOfAnyColorToManaPoolTargetPlayerEffect(String textManaPoolOwner) {
        super();
        this.staticText = (textManaPoolOwner.equals("their") ? "that player adds " : "add ") + "one mana of any color" + " to " + textManaPoolOwner + " mana pool";
    }

    public AddManaOfAnyColorToManaPoolTargetPlayerEffect(final AddManaOfAnyColorToManaPoolTargetPlayerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
        Player player = game.getPlayer(playerId);
        ChoiceColor choice = new ChoiceColor();
        if (player != null && player.choose(outcome, choice, game)) {
            Mana mana = choice.getMana(1);
            checkToFirePossibleEvents(mana, game, source);
            player.getManaPool().addMana(mana, game, source);
            return true;
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
