package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.io.Serializable;

public interface DynamicValue extends Serializable {
    int calculate(Game game, Ability sourceAbility, Effect effect);
    DynamicValue copy();
    String getMessage();
}
