package mage.target.targetpointer;

import mage.abilities.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FixedTarget implements TargetPointer {
    private UUID target;

    public FixedTarget(UUID target) {
        this.target = target;
    }

    public FixedTarget(final FixedTarget fixedTarget) {
        this.target = fixedTarget.target;
    }

    @Override
    public List<UUID> getTargets(Ability source) {
        ArrayList<UUID> list = new ArrayList<UUID>(1);
        list.add(target);
        return list;
    }

    @Override
    public UUID getFirst(Ability source) {
        return target;
    }

    @Override
    public TargetPointer copy() {
        return new FixedTarget(this);
    }
}
