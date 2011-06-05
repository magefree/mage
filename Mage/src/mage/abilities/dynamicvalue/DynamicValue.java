package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

public interface DynamicValue extends Serializable {
    int calculate(Game game, Ability sourceAbility);
    DynamicValue clone();
    String getMessage();
}
