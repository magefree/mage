/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class MorphManacostVariableValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Integer xValue = (Integer) game.getState().getValue(sourceAbility.getSourceId().toString() + "TurnFaceUpX");
        if (xValue instanceof Integer) {
            return xValue;
        }
        return 0;
    }

    @Override
    public MorphManacostVariableValue copy() {
        return new MorphManacostVariableValue();
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
