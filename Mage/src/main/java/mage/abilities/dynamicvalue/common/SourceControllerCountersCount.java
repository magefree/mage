package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Susucr
 */
public enum SourceControllerCountersCount implements DynamicValue {
    EXPERIENCE(CounterType.EXPERIENCE),
    RAD(CounterType.RAD);

    private final CounterType counterType;


    SourceControllerCountersCount(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public SourceControllerCountersCount copy() {
        return this;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            amount = player.getCounters().getCount(counterType);
        }
        return amount;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return counterType.getName() + " you have";
    }
}
