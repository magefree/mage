
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;

import java.util.Objects;

/**
 * @author North
 */
public class AnyPlayerControlsCondition implements Condition {

    private final FilterPermanent filter;

    public AnyPlayerControlsCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AnyPlayerControlsCondition that = (AnyPlayerControlsCondition) obj;

        return Objects.equals(this.filter, that.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter);
    }
}
