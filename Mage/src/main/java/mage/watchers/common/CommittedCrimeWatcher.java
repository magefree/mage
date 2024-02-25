package mage.watchers.common;

import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class CommittedCrimeWatcher extends Watcher {

    private final Set<UUID> criminals = new HashSet<>();

    public CommittedCrimeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TARGETED) {
            Optional.ofNullable(CommittedCrimeTriggeredAbility.getCriminal(event, game))
                    .filter(Objects::nonNull)
                    .ifPresent(criminals::add);
        }
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
                .contains(source.getSourceId());
    }
}
