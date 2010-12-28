package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;

public class ControlsPermanent implements Condition {
    private FilterPermanent filter;

    public ControlsPermanent(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().countAll(filter, source.getControllerId()) > 0;
    }
}
