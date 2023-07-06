package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

public class TriggerDamageDone implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (!(sourceAbility instanceof TriggeredAbility)) {
            return 0;
        }
        return ((TriggeredAbility) sourceAbility).getTriggerEvent().getAmount();
    }

    @Override
    public DynamicValue copy() {
        return new TriggerDamageDone();
    }

    @Override
    public String getMessage() {
        return "that damage";
    }
}