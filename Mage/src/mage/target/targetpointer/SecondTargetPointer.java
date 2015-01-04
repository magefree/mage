package mage.target.targetpointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

public class SecondTargetPointer implements TargetPointer {

    private Map<UUID, Integer> zoneChangeCounter = new HashMap<>();

    public static SecondTargetPointer getInstance() {
        return new SecondTargetPointer();
    }

    public SecondTargetPointer() {
    }

    public SecondTargetPointer(SecondTargetPointer firstTargetPointer) {
        this.zoneChangeCounter.putAll(firstTargetPointer.zoneChangeCounter);
    }

    @Override
    public void init(Game game, Ability source) {
        if (source.getTargets().size() > 1) {
            for (UUID target : source.getTargets().get(1).getTargets()) {
                this.zoneChangeCounter.put(target, game.getZoneChangeCounter(target));
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        ArrayList<UUID> target = new ArrayList<>();
        if (source.getTargets().size() > 1) {
            for (UUID targetId : source.getTargets().get(1).getTargets()) {
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
        if (source.getTargets().size() > 1) {
            UUID targetId = source.getTargets().get(1).getFirstTarget();
            if (zoneChangeCounter.containsKey(targetId)) {
                if (zoneChangeCounter.containsKey(targetId) && game.getZoneChangeCounter(targetId) != zoneChangeCounter.get(targetId)) {
                    return null;
                }
            }
            return targetId;
        }
        return null;
    }

    @Override
    public TargetPointer copy() {
        return new SecondTargetPointer(this);
    }
}
