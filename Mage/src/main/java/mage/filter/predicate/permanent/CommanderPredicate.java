package mage.filter.predicate.permanent;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public enum CommanderPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        Player owner = game.getPlayer(game.getOwnerId(input.getId()));
        return owner != null && game.getCommandersIds(owner).contains(input.getId());
    }

    @Override
    public String toString() {
        return "Commander";
    }
}
