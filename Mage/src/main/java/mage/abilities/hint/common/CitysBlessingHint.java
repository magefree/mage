package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
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
        boolean hasCitysBlessing = CitysBlessingCondition.instance.apply(game, ability);
        if (hasCitysBlessing) {
            return HintUtils.prepareText("You have the city's blessing", null, HintUtils.HINT_ICON_GOOD);
        }

        int count = controller == null ? 0 : game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT, controller.getId(), game);
        return HintUtils.prepareText(
                "You don't have the city's blessing (controlled permanents: " + count + " of 10)",
                null, HintUtils.HINT_ICON_BAD
        );
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
