package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

public enum ControllerLifeCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player p = game.getPlayer(sourceAbility.getControllerId());
        if (p != null) {
            return p.getLife();
        }
        return 0;
    }

    @Override
    public ControllerLifeCount copy() {
        return ControllerLifeCount.instance;
    }

    @Override
    public String getMessage() {
        return "your life total";
    }

    @Override
    public String toString() {
        return "X";
    }
}
