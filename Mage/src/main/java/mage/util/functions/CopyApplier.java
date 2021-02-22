package mage.util.functions;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;
import java.util.UUID;

/**
 * Apply additional modifications for blueprint of the copy permanent
 *
 * @author LevelX2
 */
public abstract class CopyApplier implements Serializable {

    // WARNING:
    // 1. Applier uses for copy effects only;
    // 2. It applies to the blueprint, not the real object (the real object is targetObjectId and can be card or token, even from outside the game like EmptyToken);
    // 3. "source" is the current copy ability and can be different from the original copy ability (copy of copy);
    // 4. Don't use "source" param at all;
    // 5. For exception/non-copyable effects use isCopyOfCopy() to detect that situation (example: 706.9e, Spark Double, Altered Ego).
    public abstract boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId);

    public boolean isCopyOfCopy(Ability source, MageObject blueprint, UUID targetObjectId) {
        return blueprint.isCopy();
    }

    public String getText() {
        return "";
    }
}
