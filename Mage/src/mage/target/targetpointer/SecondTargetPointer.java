package mage.target.targetpointer;

import java.util.*;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;


public class SecondTargetPointer implements TargetPointer {

    private Map<UUID, Integer> zoneChangeCounter = new HashMap<UUID, Integer>();

    public static SecondTargetPointer getInstance() {
        return new SecondTargetPointer();
    }

    public SecondTargetPointer() {
    }

    public SecondTargetPointer(SecondTargetPointer firstTargetPointer) {
        this.zoneChangeCounter = new HashMap<UUID, Integer>();
        for (Map.Entry<UUID, Integer> entry : firstTargetPointer.zoneChangeCounter.entrySet()) {
            this.zoneChangeCounter.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void init(Game game, Ability source) {
        if (source.getTargets().size() > 1) {
            for (UUID target : source.getTargets().get(1).getTargets()) {
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
        if (source.getTargets().size() > 1) {
            for (UUID targetId : source.getTargets().get(1).getTargets()) {
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
        if (source.getTargets().size() > 1) {
            UUID targetId = source.getTargets().get(1).getFirstTarget();
            if (zoneChangeCounter.containsKey(targetId)) {
                Card card = game.getCard(targetId);
                if (card != null && zoneChangeCounter.containsKey(targetId)
                            && card.getZoneChangeCounter() != zoneChangeCounter.get(targetId)) {
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
