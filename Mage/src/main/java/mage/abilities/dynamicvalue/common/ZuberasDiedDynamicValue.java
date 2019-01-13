package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.ZuberasDiedWatcher;

/**
 * Created by Eric on 9/24/2016.
 */
public class ZuberasDiedDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ZuberasDiedWatcher watcher = game.getState().getWatcher(ZuberasDiedWatcher.class);
        if(watcher == null){
            return 0;
        }
        return  watcher.getZuberasDiedThisTurn();
    }

    @Override
    public ZuberasDiedDynamicValue copy() {
        return new ZuberasDiedDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Zubera that died this turn";
    }
}
