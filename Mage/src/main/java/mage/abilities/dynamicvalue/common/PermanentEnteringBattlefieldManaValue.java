package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;

public enum PermanentEnteringBattlefieldManaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("permanentEnteringBattlefield");
        return permanent == null ? 0 : permanent.getManaValue();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "that creature's mana value";
    }
}
