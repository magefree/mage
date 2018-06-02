
package mage.abilities.dynamicvalue.common;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 * Amount of life the controller got this turn.
 *
 * @author LevelX2
 */
public class ControllerGotLifeCount implements DynamicValue, MageSingleton {

    private static final ControllerGotLifeCount fINSTANCE = new ControllerGotLifeCount();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static ControllerGotLifeCount getInstance() {
        return fINSTANCE;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        PlayerGainedLifeWatcher watcher = (PlayerGainedLifeWatcher) game.getState().getWatchers().get(PlayerGainedLifeWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.getLiveGained(controllerId);
        }
        return 0;
    }

    @Override
    public ControllerGotLifeCount copy() {
        return new ControllerGotLifeCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the amount of life you've gained this turn";
    }
}
