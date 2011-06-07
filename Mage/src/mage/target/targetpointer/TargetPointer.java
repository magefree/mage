package mage.target.targetpointer;

import mage.abilities.Ability;

import java.util.List;
import java.util.UUID;

public interface TargetPointer {
    List<UUID> getTargets(Ability source);
    UUID getFirst(Ability source);
    TargetPointer copy();
}
