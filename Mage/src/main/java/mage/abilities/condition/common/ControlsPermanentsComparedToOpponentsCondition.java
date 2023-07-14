
package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ControlsPermanentsComparedToOpponentsCondition implements Condition {

    private final ComparisonType type;
    private final FilterPermanent filterPermanent;

    public ControlsPermanentsComparedToOpponentsCondition(ComparisonType type, FilterPermanent filterPermanent) {
        this.type = type;
        this.filterPermanent = filterPermanent;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int ownNumber = game.getBattlefield().countAll(filterPermanent, source.getControllerId(), game);
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                if (!ComparisonType.compare(ownNumber, type, game.getBattlefield().countAll(filterPermanent, playerId, game))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        switch (type) {
            case FEWER_THAN: return String.format("you control fewer %s than each opponent", filterPermanent.getMessage());
            case MORE_THAN: return String.format("you control more %s than each opponent", filterPermanent.getMessage());
            default: return "";
        }
    }

}
