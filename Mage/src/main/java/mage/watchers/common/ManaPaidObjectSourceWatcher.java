package mage.watchers.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaPaidEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Susucr
 */
public class ManaPaidObjectSourceWatcher extends Watcher {

    // what is paid -> set of all MageObject sources used to pay for the mana.
    private final Map<MageObjectReference, Set<MageObjectReference>> payMap = new HashMap<>();

    public ManaPaidObjectSourceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.MANA_PAID) {
            return;
        }

        ManaPaidEvent manaEvent = (ManaPaidEvent) event;
        UUID paid = manaEvent.getSourcePaidId();
        MageObject sourceObject = manaEvent.getSourceObject();
        if (paid == null || sourceObject == null) {
            return;
        }

        payMap
                .computeIfAbsent(new MageObjectReference(paid, game), x -> new HashSet<>())
                .add(new MageObjectReference(sourceObject, game));
    }

    public boolean checkManaFromSourceWasUsedToPay(MageObjectReference sourceOfMana, MageObjectReference paidObject) {
        return payMap
                .getOrDefault(paidObject, Collections.emptySet())
                .contains(sourceOfMana);
    }

    @Override
    public void reset() {
        payMap.clear();
        super.reset();
    }
}
