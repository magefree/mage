
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public enum FormidableCondition implements Condition {

   instance;

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();


    @Override
    public boolean apply(Game game, Ability source) {
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            sumPower += permanent.getPower().getValue();
        }
        return sumPower >= 8;
    }

    @Override
    public String toString() {
        return "creatures you control have total power 8 or greater";
    }

}
