package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

public enum OpponentsPoisonCountersCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Set<UUID> playerList = game.getOpponents(sourceAbility.getControllerId(), true);
        for (UUID playerUUID : playerList) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getCounters().getCount(CounterType.POISON);
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return OpponentsPoisonCountersCount.instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "poison counter your opponents have";
    }
}
