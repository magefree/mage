package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

public class CountersSourcePlayerCount implements DynamicValue {

    private final CounterType counterType;

    /**
     * Number of counters of any type on the source permanent
     */
    public CountersSourcePlayerCount() {
        this((CounterType) null);
    }

    /**
     * Number of counters of the specified type on the source permanent
     */
    public CountersSourcePlayerCount(CounterType counterType) {
        this.counterType = counterType;
    }

    protected CountersSourcePlayerCount(final CountersSourcePlayerCount countersCount) {
        this.counterType = countersCount.counterType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID controllerId = sourceAbility.getControllerId();
        if (controllerId == null) {
            return 0;
        }
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return 0;
        }
        return counterType != null
                ? player.getCountersCount(counterType)
                : 0;
    }

    @Override
    public CountersSourcePlayerCount copy() {
        return new CountersSourcePlayerCount(this);
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
