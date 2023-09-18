package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author LevelX2
 */
public class ConvokeWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> convokingCreatures = new HashMap<>();

    public ConvokeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CONVOKED) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        Permanent tappedCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (spell == null || tappedCreature == null) {
            return;
        }
        convokingCreatures
                .computeIfAbsent(new MageObjectReference(spell.getSourceId(), game), x -> new HashSet<>())
                .add(new MageObjectReference(tappedCreature, game));
    }

    public static Set<MageObjectReference> getConvokingCreatures(MageObjectReference mor, Game game) {
        return game
                .getState()
                .getWatcher(ConvokeWatcher.class)
                .convokingCreatures
                .getOrDefault(mor, Collections.emptySet());
    }

    public static boolean checkConvoke(MageObjectReference mor, Permanent permanent, Game game) {
        return getConvokingCreatures(mor, game)
                .stream()
                .anyMatch(m -> m.refersTo(permanent, game));
    }

    @Override
    public void reset() {
        super.reset();
        convokingCreatures.clear();
    }
}