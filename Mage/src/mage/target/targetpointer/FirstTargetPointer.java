package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

import java.util.*;

public class FirstTargetPointer implements TargetPointer {

    private Map<UUID, Integer> zoneChangeCounter = new HashMap<UUID, Integer>();

    public static FirstTargetPointer getInstance() {
        return new FirstTargetPointer();
    }

    public FirstTargetPointer() {
    }

    public FirstTargetPointer(FirstTargetPointer firstTargetPointer) {
        this.zoneChangeCounter = new HashMap<UUID, Integer>();
        for (Map.Entry<UUID, Integer> entry : firstTargetPointer.zoneChangeCounter.entrySet()) {
            this.zoneChangeCounter.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void init(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            for (UUID target : source.getTargets().get(0).getTargets()) {
                Card card = game.getCard(target);
                if (card != null) {
                    this.zoneChangeCounter.put(target, card.getZoneChangeCounter());
                }    
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        ArrayList<UUID> target = new ArrayList<UUID>();
        if (source.getTargets().size() > 0) {
            for (UUID targetId : source.getTargets().get(0).getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null && zoneChangeCounter.containsKey(targetId)
                        && card.getZoneChangeCounter() != zoneChangeCounter.get(targetId)) {
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
            Card card = game.getCard(targetId);
            if (card != null && zoneChangeCounter.containsKey(targetId)
                        && card.getZoneChangeCounter() != zoneChangeCounter.get(targetId)) {
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
