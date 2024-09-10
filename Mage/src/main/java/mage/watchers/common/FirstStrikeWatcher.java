package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public class FirstStrikeWatcher extends Watcher {

    // creatures that had first strike or double strike for the first strike combat damage step of this combat phase
    // (note, due to 0 power or prevention, they may not necessarily have dealt damage)
    private final Set<MageObjectReference> firstStrikingCreatures;

    /**
     * Game default watcher, required for combat code
     */
    public FirstStrikeWatcher() {
        super(WatcherScope.GAME);
        this.firstStrikingCreatures = new HashSet<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_POST) {
            firstStrikingCreatures.clear();
        }
    }

    @Override
    public void reset() {
        super.reset();
        firstStrikingCreatures.clear();
    }

    public static void recordFirstStrikingCreature(UUID creatureId, Game game)  {
        game.getState()
                .getWatcher(FirstStrikeWatcher.class)
                .firstStrikingCreatures
                .add(new MageObjectReference(creatureId, game));
    }

    public static boolean wasFirstStrikingCreature(UUID creatureId, Game game) {
        return game.getState()
                .getWatcher(FirstStrikeWatcher.class)
                .firstStrikingCreatures
                .contains(new MageObjectReference(creatureId, game));
    }

}
