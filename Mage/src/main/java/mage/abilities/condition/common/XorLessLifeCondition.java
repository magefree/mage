
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

import java.util.UUID;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class XorLessLifeCondition implements Condition {

    public enum CheckType { AN_OPPONENT, CONTROLLER, TARGET_OPPONENT, EACH_PLAYER }

    private final CheckType type;
    private final int amount;

    public XorLessLifeCondition ( CheckType type , int amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;

        switch ( this.type ) {
            case AN_OPPONENT:
                for ( UUID opponentUUID : game.getOpponents(source.getControllerId()) ) {
                    conditionApplies |= game.getPlayer(opponentUUID).getLife() <= amount;
                }
                break;
            case CONTROLLER:
                conditionApplies = game.getPlayer(source.getControllerId()).getLife() <= amount;
                break;
            case TARGET_OPPONENT:
                //TODO: Implement this.
                break;
            case EACH_PLAYER:
                int maxLife = 0;
                PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
                for ( UUID pid : playerList ) {
                    Player p = game.getPlayer(pid);
                    if (p != null) {
                        if (maxLife < p.getLife()) {
                            maxLife = p.getLife();
                        }
                    }
                }
                conditionApplies = maxLife <= amount;
                break;
        }

        return conditionApplies;
    }

}
