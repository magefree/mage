/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.effects.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class AddManaToManaPoolEffect extends OneShotEffect {

    protected Mana mana;
    
    public AddManaToManaPoolEffect(Mana mana) {
        super(Outcome.PutManaInPool);
        this.mana = mana;
        this.staticText = "add " + mana.toString() + " to that player's mana pool";
    }

    public AddManaToManaPoolEffect(final AddManaToManaPoolEffect effect) {
        super(effect);
        this.mana = effect.mana;
    }

    @Override
    public AddManaToManaPoolEffect copy() {
        return new AddManaToManaPoolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
