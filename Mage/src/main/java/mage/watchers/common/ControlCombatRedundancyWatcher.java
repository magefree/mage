package mage.watchers.common;

import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public class ControlCombatRedundancyWatcher extends Watcher { // workaround for solving timestamp issues regarding "you choose which creatures block and how those creatures block" effects

    private static final class PlayerDuration implements java.io.Serializable {  // class must be serilizable Bug #8497

        private final Duration duration;
        private final UUID playerId;

        private PlayerDuration(Duration duration, UUID playerId) {
            this.duration = duration;
            this.playerId = playerId;
        }

        private boolean isCombat() {
            return duration == Duration.EndOfCombat;
        }

        private boolean isPlayer(UUID playerId) {
            return playerId.equals(this.playerId);
        }
    }

    private final List<PlayerDuration> attackingControllers = new ArrayList<>();
    private final List<PlayerDuration> blockingControllers = new ArrayList<>();

    public ControlCombatRedundancyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void reset() {
        super.reset();
        attackingControllers.clear();
        blockingControllers.clear();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST) {
            attackingControllers.removeIf(PlayerDuration::isCombat);
            blockingControllers.removeIf(PlayerDuration::isCombat);
        }
    }

    public static void addAttackingController(UUID playerId, Duration duration, Game game) {
        ControlCombatRedundancyWatcher watcher = game.getState().getWatcher(ControlCombatRedundancyWatcher.class);
        watcher.attackingControllers.add(0, new PlayerDuration(duration, playerId));
    }

    public static void addBlockingController(UUID playerId, Duration duration, Game game) {
        ControlCombatRedundancyWatcher watcher = game.getState().getWatcher(ControlCombatRedundancyWatcher.class);
        watcher.blockingControllers.add(0, new PlayerDuration(duration, playerId));
    }

    public static boolean checkAttackingController(UUID playerId, Game game) {
        ControlCombatRedundancyWatcher watcher = game.getState().getWatcher(ControlCombatRedundancyWatcher.class);
        return !watcher.attackingControllers.isEmpty() && watcher.attackingControllers.get(0).isPlayer(playerId);
    }

    public static boolean checkBlockingController(UUID playerId, Game game) {
        ControlCombatRedundancyWatcher watcher = game.getState().getWatcher(ControlCombatRedundancyWatcher.class);
        return !watcher.blockingControllers.isEmpty() && watcher.blockingControllers.get(0).isPlayer(playerId);
    }
}
