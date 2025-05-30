package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum ControllerSpeedCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(game.getPlayer(sourceAbility.getControllerId()))
                .map(Player::getSpeed)
                .orElse(0);
    }

    @Override
    public ControllerSpeedCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "your speed";
    }
}
