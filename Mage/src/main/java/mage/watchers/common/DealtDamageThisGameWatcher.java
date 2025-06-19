package mage.watchers.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 * @author TheElk801
 */
public class DealtDamageThisGameWatcher extends Watcher {

    private final Set<MageObjectReference> damagers = new HashSet<>();

    public DealtDamageThisGameWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGINNING_PHASE_PRE:
                // keep the stored values from getting too big, especially since it doesn't reset between games
                damagers.removeIf(mor -> !mor.zoneCounterIsCurrent(game));
                return;
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER:
                break;
            default:
                return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            damagers.add(new MageObjectReference(permanent, game));
        }
    }

    public static boolean checkCreature(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(DealtDamageThisGameWatcher.class)
                .damagers
                .stream()
                .noneMatch(mor -> mor.refersTo(source.getSourcePermanentOrLKI(game), game));
    }
}
