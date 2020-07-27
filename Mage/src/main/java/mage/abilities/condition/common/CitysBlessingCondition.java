
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.designations.DesignationType;
import mage.game.Game;

/**
 *
 * @author LvelX2
 */
public enum CitysBlessingCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).hasDesignation(DesignationType.CITYS_BLESSING);
    }

    @Override
    public String toString() {
        return "you have the city's blessing";
    }
}
