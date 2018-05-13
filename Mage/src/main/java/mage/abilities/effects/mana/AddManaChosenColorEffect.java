/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.mana;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AddManaChosenColorEffect extends ManaEffect {

    public AddManaChosenColorEffect() {
        super();
        staticText = "Add one mana of the chosen color";
    }

    public AddManaChosenColorEffect(final AddManaChosenColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(getMana(game, source), game, source);
        }
        return true;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
        } else {
            return null;
        }
    }

    @Override
    public AddManaChosenColorEffect copy() {
        return new AddManaChosenColorEffect(this);
    }
}
