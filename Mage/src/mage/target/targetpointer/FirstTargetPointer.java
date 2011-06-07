package mage.target.targetpointer;

import mage.abilities.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirstTargetPointer implements TargetPointer {
    private static FirstTargetPointer instance = new FirstTargetPointer();

    public static FirstTargetPointer getInstance() {
        return instance;
    }

    @Override
    public List<UUID> getTargets(Ability source) {
        ArrayList<UUID> target = new ArrayList<UUID>();
        target.add(source.getFirstTarget());
        return target;
    }

    @Override
    public UUID getFirst(Ability source) {
        return source.getFirstTarget();
    }

    @Override
    public TargetPointer copy() {
        return instance;
    }
}
