package mage.watchers.common;

import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CommittedCrimeWatcher extends Watcher {

    // players who committed a crime this turn
    private final Set<UUID> criminals = new HashSet<>();

    public CommittedCrimeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case ACTIVATED_ABILITY:
            case TRIGGERED_ABILITY:
                break;
            default:
                return;
        }
        Optional.ofNullable(CommittedCrimeTriggeredAbility.getCriminal(event, game)).ifPresent(criminals::add);
    }

    @Override
    public void reset() {
        super.reset();
        criminals.clear();
    }

    public static boolean checkCriminality(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CommittedCrimeWatcher.class)
                .criminals
                .contains(source.getControllerId());
    }
}
