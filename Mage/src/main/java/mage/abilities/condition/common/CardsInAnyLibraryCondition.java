
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class CardsInAnyLibraryCondition implements Condition {

    protected final ComparisonType type;
    protected final int value;

    public CardsInAnyLibraryCondition(ComparisonType type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public final boolean apply(Game game, Ability source) {

        boolean libraryWith20OrFewerCards = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && ComparisonType.compare(player.getLibrary().size(), type, value)) {
                    libraryWith20OrFewerCards = true;
                    break;
                }
            }
        }
        return libraryWith20OrFewerCards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("a library has ");
        switch (type) {
            case MORE_THAN:
                sb.append(value + 1).append(" or more cards in it ");
                break;
            case EQUAL_TO:
                sb.append(value).append(" cards in it ");
                break;
            case FEWER_THAN:
                sb.append(value - 1).append(" or fewer cards in it ");
                break;
        }
        return sb.toString();
    }
}
