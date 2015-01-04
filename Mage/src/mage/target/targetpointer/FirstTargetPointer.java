package mage.target.targetpointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

public class FirstTargetPointer implements TargetPointer {

    private final Map<UUID, Integer> zoneChangeCounter = new HashMap<>();

    public static FirstTargetPointer getInstance() {
        return new FirstTargetPointer();
    }

    public FirstTargetPointer() {
    }

    public FirstTargetPointer(FirstTargetPointer firstTargetPointer) {
        zoneChangeCounter.putAll(firstTargetPointer.zoneChangeCounter);
    }

    @Override
    public void init(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            for (UUID target : source.getTargets().get(0).getTargets()) {
                this.zoneChangeCounter.put(target, game.getZoneChangeCounter(target));
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        ArrayList<UUID> target = new ArrayList<>();
        if (source.getTargets().size() > 0) {
            for (UUID targetId : source.getTargets().get(0).getTargets()) {
                if (zoneChangeCounter.containsKey(targetId) && game.getZoneChangeCounter(targetId) != zoneChangeCounter.get(targetId)) {
                    continue;
                }
                target.add(targetId);
            }
        }
        return target;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        if (zoneChangeCounter.containsKey(targetId)) {
            if (zoneChangeCounter.containsKey(targetId) && game.getZoneChangeCounter(targetId) != zoneChangeCounter.get(targetId)) {
                return null;
            }
        }
        return targetId;
    }

    @Override
    public TargetPointer copy() {
        return new FirstTargetPointer(this);
    }
}
