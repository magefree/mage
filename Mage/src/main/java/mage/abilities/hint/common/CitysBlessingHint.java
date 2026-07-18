package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.hint.Hint;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author JayDi85
 */
public enum CitysBlessingHint implements Hint {

    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (CitysBlessingCondition.instance.apply(game, ability)) {
            return "You have the city's blessing";
        }

        int count = controller == null ? 0 : game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT, controller.getId(), game);
        return "You don't have the city's blessing (controlled permanents: " + count + " of 10)";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
