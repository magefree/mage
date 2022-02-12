
package mage.filter.predicate.other;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PlayerCanGainLifePredicate implements ObjectSourcePlayerPredicate<Player> {

//    public PlayerCanGainLifePredicate() {
//    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player player = input.getObject();
        if(player != null) {
            return player.isCanGainLife();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player can gain life";
    }
}
