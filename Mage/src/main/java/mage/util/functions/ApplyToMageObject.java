package mage.util.functions;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class ApplyToMageObject {

    // WARNING:
    // 1. Applier uses for copy effects only;
    // 2. It's applies to blueprint, not real object (real object is targetObjectId and can be card or token, even outside from game like EmptyToken);
    // 3. "source" is current copy ability and can be different from original copy ability (copy of copy);
    // 4. Don't use "source" param at all;
    // 5. Use isCopyOfCopy() to detect it (some effects can applies to copy of copy, but other can't -- ses Spark Double as example).
    // TODO: check all aplliers implementations - remove source uses, add isCopyOfCopy processing
    public abstract boolean apply(Game game, MageObject mageObject, Ability source, UUID targetObjectId);

    public boolean isCopyOfCopy(Ability source, UUID targetObjectId) {
        return !Objects.equals(targetObjectId, source.getSourceId());
    }
}
