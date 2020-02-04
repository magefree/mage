
package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author North
 */
public class DefendingPlayerControlsCondition implements Condition {

    private final FilterPermanent filter;

    public DefendingPlayerControlsCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayer = game.getCombat().getDefenderId(source.getSourceId());
        return defendingPlayer != null && game.getBattlefield().countAll(filter, defendingPlayer, game) > 0;
    }
}
