
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public enum CardsInAllHandsCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                count += player.getHand().size();
            }
        }
        return count;
    }

    @Override
    public CardsInAllHandsCount copy() {
        return CardsInAllHandsCount.instance;
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
