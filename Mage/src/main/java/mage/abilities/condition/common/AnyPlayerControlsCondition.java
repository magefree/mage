
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;

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
}
