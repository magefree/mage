package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author LevelX2
 */
public class EachCreatureThatConvokedSourceWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> convokingCreatures = new HashMap<>();

    public EachCreatureThatConvokedSourceWatcher() {
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

        MageObjectReference convokedSpell = new MageObjectReference(spell.getSourceId(), game);
        Set<MageObjectReference> creatures;
        if (convokingCreatures.containsKey(convokedSpell)) {
            creatures = convokingCreatures.get(convokedSpell);
        } else {
            creatures = new HashSet<>();
            convokingCreatures.put(convokedSpell, creatures);
        }
        creatures.add(new MageObjectReference(tappedCreature, game));
    }

    public Set<MageObjectReference> getConvokingCreatures(MageObjectReference mor) {
        return convokingCreatures.get(mor);
    }

    @Override
    public void reset() {
        super.reset();
        convokingCreatures.clear();
    }
}