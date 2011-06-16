package mage.target.targetpointer;

import java.io.Serializable;
import mage.abilities.Ability;

import java.util.List;
import java.util.UUID;

public interface TargetPointer extends Serializable {
    List<UUID> getTargets(Ability source);
    UUID getFirst(Ability source);
    TargetPointer copy();
}
