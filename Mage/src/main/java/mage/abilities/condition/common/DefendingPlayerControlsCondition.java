
package mage.abilities.condition.common;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        return Objects.hash(filter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DefendingPlayerControlsCondition that = (DefendingPlayerControlsCondition) obj;
        return Objects.equals(this.filter, that.filter);
    }
}
