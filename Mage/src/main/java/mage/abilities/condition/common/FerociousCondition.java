

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum FerociousCondition implements Condition {
    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }


    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0;
    }

    @Override
    public String toString() {
        return "you control a creature with power 4 or greater";
    }


}
