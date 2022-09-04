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

    /**
     * Describes the appropriate subset of targets for ability text.
     *
     * @param targets
     * @param defaultText
     * @return
     */
    default String describeTargets(Targets targets, String defaultDescription) {
        return defaultDescription;
    }

    default boolean isPlural(Targets targets) {
        return false;
    }

    /**
     * Store text to target pointer (usefull to keep data for specific trigger, e.g. selected target name for rules)
     *
     * @param key
     * @param value
     */
    TargetPointer withData(String key, String value);

    String getData(String key);
}
