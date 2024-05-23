package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.PhaseStep;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public class FirstStrikeDamageWatcher extends Watcher {

    // creatures that dealt damage during the first strike combat damage step of this combat phase
    private final Set<MageObjectReference> firstStrikingCreatures;

    /**
     * Game default watcher, required for combat code
     */
    public FirstStrikeDamageWatcher() {
        super(WatcherScope.GAME);
        this.firstStrikingCreatures = new HashSet<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER:
                if (((DamagedEvent) event).isCombatDamage() && game.getTurnStepType() == PhaseStep.FIRST_COMBAT_DAMAGE) {
                    firstStrikingCreatures.add(new MageObjectReference(event.getSourceId(), game));
                }
                return;
            case COMBAT_PHASE_POST:
                firstStrikingCreatures.clear();
        }
    }

    @Override
    public void reset() {
        super.reset();
        firstStrikingCreatures.clear();
    }

    public static boolean dealtFirstStrikeDamage(UUID creatureId, Game game) {
        return game.getState()
                .getWatcher(FirstStrikeDamageWatcher.class)
                .firstStrikingCreatures
                .contains(new MageObjectReference(creatureId, game));
    }

}
