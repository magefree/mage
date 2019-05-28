/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public enum CommanderPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        Player owner = game.getPlayer(input.getOwnerId());
        return owner != null
                && game.getCommandersIds(owner).contains(input.getId());
    }

    @Override
    public String toString() {
        return "Commander";
    }
}
