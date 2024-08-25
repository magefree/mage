package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class CountersControllerCount implements DynamicValue {

    private final CounterType counterType;

    /**
     * Number of counters of the specified type on the Controller of the source
     */
    public CountersControllerCount(CounterType counterType) {
        this.counterType = counterType;
    }

    protected CountersControllerCount(final CountersControllerCount countersCount) {
        this.counterType = countersCount.counterType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID controllerId = sourceAbility.getControllerId();
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return 0;
        }
        return counterType != null
                ? player.getCountersCount(counterType)
                : 0;
    }

    @Override
    public CountersControllerCount copy() {
        return new CountersControllerCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return (counterType != null ? counterType.toString() + ' ' : "") + "counter on {this}'s controller";
    }
}
