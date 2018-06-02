
package mage.abilities.dynamicvalue.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class CardsInAllHandsCount implements DynamicValue {
    
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null)
            {
                count += player.getHand().size();
            }
        }
        return count;
    }

    @Override
    public CardsInAllHandsCount copy() {
        return new CardsInAllHandsCount();
    }

    @Override
    public String getMessage() {
        return "the total number of cards in all players' hands";
    }

    @Override
    public String toString() {
        return "X";
    }
}
