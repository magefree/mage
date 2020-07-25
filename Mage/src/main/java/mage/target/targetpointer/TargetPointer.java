package mage.target.targetpointer;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

public interface TargetPointer extends Serializable {

    void init(Game game, Ability source);

    List<UUID> getTargets(Game game, Ability source);

    UUID getFirst(Game game, Ability source);

    TargetPointer copy();

    FixedTarget getFixedTarget(Game game, Ability source);

    /**
     * Retrieves the permanent according the first targetId and
     * zoneChangeCounter if set.<br>
     * Retrieves also the LKI if the permanent is no longer onto the
     * battlefield.<br>
     * This should not be used for true targeted objects, because they are not
     * retrieved using LKI (608.2b).<br>
     * This is only used if the the target pointer is used to transfer
     * information about a related permanent (often from triggered abilities).
     *
     * @param game
     * @param source
     * @return permanent
     */
    Permanent getFirstTargetPermanentOrLKI(Game game, Ability source);
}
