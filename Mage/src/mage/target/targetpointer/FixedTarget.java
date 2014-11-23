package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FixedTarget implements TargetPointer {
    private final UUID target;
    private int zoneChangeCounter;

    public FixedTarget(UUID target) {
        this.target = target;
    }

    public FixedTarget(final FixedTarget fixedTarget) {
        this.target = fixedTarget.target;
        this.zoneChangeCounter = fixedTarget.zoneChangeCounter;
    }

    @Override
    public void init(Game game, Ability source) {
        this.zoneChangeCounter = game.getZoneChangeCounter(target);
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        // check target not changed zone
        if (this.zoneChangeCounter > 0) { // will be zero if not defined in init
            if (game.getZoneChangeCounter(target) != this.zoneChangeCounter) {
                return new ArrayList<>(); // return empty
            }
        }

        ArrayList<UUID> list = new ArrayList<>(1);
        list.add(target);
        return list;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        // check target not changed zone
        if (this.zoneChangeCounter > 0) { // will be zero if not defined in init
            if (game.getZoneChangeCounter(target) != this.zoneChangeCounter) {
                return null;
            }
        }

        return target;
    }

    @Override
    public TargetPointer copy() {
        return new FixedTarget(this);
    }
    
    public UUID getTarget() {
        return target;
    }
}
