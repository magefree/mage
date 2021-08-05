package mage.filter.predicate.mageobject;

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
        Player owner = game.getPlayer(game.getOwnerId(input));
        return owner != null && game.isCommanderObject(owner, input);
    }

    @Override
    public String toString() {
        return "Commander";
    }
}
