package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Checks if a Permanents right half is unlocked
 *
 * @author oscscull
 */
public enum RoomRightHalfUnlockedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // This is needed on etb trigger
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            // This is needed on etb resolution
            permanent = game.getPermanent(source.getSourceId());
        }

        return permanent != null && permanent.isRightHalfUnlocked();
    }
}
