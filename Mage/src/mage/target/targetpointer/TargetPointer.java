package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface TargetPointer extends Serializable {
    void init(Game game, Ability source);
    List<UUID> getTargets(Game game, Ability source);
    UUID getFirst(Game game, Ability source);
    TargetPointer copy();
}
