package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author emerald000
 */
public class MeleeAbility extends AttacksTriggeredAbility {

    public MeleeAbility() {
        super(new BoostSourceEffect(MeleeDynamicValue.instance, MeleeDynamicValue.instance, Duration.EndOfTurn), false);
        this.addWatcher(new MeleeWatcher());
    }

    public MeleeAbility(final MeleeAbility ability) {
        super(ability);
    }

    @Override
    public MeleeAbility copy() {
        return new MeleeAbility(this);
    }

    @Override
    public String getRule() {
        return "melee <i>(Whenever this creature attacks, it gets +1/+1 until end of turn for each opponent you attacked this combat.)</i>";
    }
}

class MeleeWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

    public MeleeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGIN_COMBAT_STEP_PRE:
                this.playersAttacked.clear();
                return;
            case ATTACKER_DECLARED:
                if (game.getPlayer(event.getTargetId()) == null) {
                    return;
                }
                this.playersAttacked
                        .computeIfAbsent(event.getPlayerId(), x -> new HashSet<>())
                        .add(event.getTargetId());
        }
    }

    public static int getNumberOfAttackedPlayers(UUID attackerId, Game game) {
        return game
                .getState()
                .getWatcher(MeleeWatcher.class)
                .playersAttacked
                .getOrDefault(attackerId, Collections.emptySet())
                .size();
    }
}

enum MeleeDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return MeleeWatcher.getNumberOfAttackedPlayers(sourceAbility.getSourceId(), game);
    }

    @Override
    public MeleeDynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "number of opponents you attacked this combat";
    }

    @Override
    public String toString() {
        return "X";
    }
}
