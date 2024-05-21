package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Targets;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface TargetPointer extends Serializable, Copyable<TargetPointer> {

    /**
     * Init dynamic targets (must save current targets zcc to fizzle it later on outdated targets)
     * - one shot effects: no needs to init
     * - continues effects: must use init logic (effect init on resolve or game add)
     * <p>
     * Targets list can be accessible before effect's init.
     */
    void init(Game game, Ability source);

    boolean isInitialized();

    void setInitialized();

    List<UUID> getTargets(Game game, Ability source);

    /**
     * Return first actual target id (null on outdated targets)
     */
    UUID getFirst(Game game, Ability source);

    /**
     * Return first actual target data (null on outdated targets)
     */
    FixedTarget getFirstAsFixedTarget(Game game, Ability source);

    TargetPointer copy();

    /**
     * Retrieves the permanent according the first targetId and
     * zoneChangeCounter if set.<br>
     * Retrieves also the LKI if the permanent is no longer onto the
     * battlefield.<br>
     * This should not be used for true targeted objects, because they are not
     * retrieved using LKI (608.2b).<br>
     * This is only used if the the target pointer is used to transfer
     * information about a related permanent (often from triggered abilities).
     */
    Permanent getFirstTargetPermanentOrLKI(Game game, Ability source);

    /**
     * Describes the appropriate subset of targets for ability text.
     */
    default String describeTargets(Targets targets, String defaultDescription) {
        return defaultDescription;
    }

    default boolean isPlural(Targets targets) {
        return false;
    }

    /**
     * Store text to target pointer (useful to keep data for specific trigger, e.g. selected target name for rules)
     */
    TargetPointer withData(String key, String value);

    String getData(String key);
}
