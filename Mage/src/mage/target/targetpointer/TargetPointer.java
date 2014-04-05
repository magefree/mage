package mage.target.targetpointer;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.game.Game;

public interface TargetPointer extends Serializable {
    void init(Game game, Ability source);
    List<UUID> getTargets(Game game, Ability source);
    UUID getFirst(Game game, Ability source);
    TargetPointer copy();
}
