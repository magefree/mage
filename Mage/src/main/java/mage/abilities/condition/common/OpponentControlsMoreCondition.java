

package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */

public class OpponentControlsMoreCondition implements Condition {

    private final FilterPermanent filter;

    public OpponentControlsMoreCondition(FilterPermanent filter) {
        this.filter = filter;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int numLands = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            if (numLands < game.getBattlefield().countAll(filter, opponentId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent controls more " + filter.getMessage() +" than you";
    }
}
