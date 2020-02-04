package mage.abilities.effects.mana;


import java.util.ArrayList;
import java.util.List;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.choices.ManaChoice;
import mage.game.Game;
import mage.players.Player;

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
        ArrayList<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(2));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Player player = getPlayer(game, source);
        return ManaChoice.chooseTwoDifferentColors(player, game);
    }

    @Override
    public AddManaOfTwoDifferentColorsEffect copy() {
        return new AddManaOfTwoDifferentColorsEffect(this);
    }
}
