package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ControllerLifeDividedValue implements DynamicValue {

    private final Integer divider;

    public ControllerLifeDividedValue(Integer divider) {
        this.divider = divider;
    }

    public ControllerLifeDividedValue(final ControllerLifeDividedValue dynamicValue) {
        this.divider = dynamicValue.divider;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player p = game.getPlayer(sourceAbility.getControllerId());
        if (p != null) {
            return p.getLife() / divider;
        }
        return 0;
    }

    @Override
    public ControllerLifeDividedValue copy() {
        return new ControllerLifeDividedValue(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
