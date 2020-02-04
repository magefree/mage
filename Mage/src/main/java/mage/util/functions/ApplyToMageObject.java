package mage.util.functions;

import java.util.Objects;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * @author LevelX2
 */
public abstract class ApplyToMageObject {

    // WARNING:
    // 1. Applier uses for copy effects only;
    // 2. It applies to the blueprint, not the real object (the real object is targetObjectId and can be card or token, even from outside the game like EmptyToken);
    // 3. "source" is the current copy ability and can be different from the original copy ability (copy of copy);
    // 4. Don't use "source" param at all;
    // 5. Use isCopyOfCopy() to detect it (some effects can apply to copy of copy, but others can't -- see Spark Double as an example).
    // TODO: check all applier implementations - remove source uses, add isCopyOfCopy processing
    public abstract boolean apply(Game game, MageObject mageObject, Ability source, UUID targetObjectId);

    public boolean isCopyOfCopy(Ability source, UUID targetObjectId) {
        return !Objects.equals(targetObjectId, source.getSourceId());
    }
}
