package mage.abilities.effects.mana;


import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ManaChoice;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

public class AddManaOfTwoDifferentColorsEffect extends ManaEffect {

    public AddManaOfTwoDifferentColorsEffect() {
        super();
        staticText = "Add two mana of different colors.";
    }

    private AddManaOfTwoDifferentColorsEffect(final AddManaOfTwoDifferentColorsEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(2));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player player = getPlayer(game, source);
            return ManaChoice.chooseTwoDifferentColors(player, game);
        } else {
            return new Mana();
        }
    }

    @Override
    public AddManaOfTwoDifferentColorsEffect copy() {
        return new AddManaOfTwoDifferentColorsEffect(this);
    }
}
