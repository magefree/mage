package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author oscscull
 * Checks if a Permanent's left half is LOCKED (i.e., NOT unlocked).
 */
public enum RoomLeftHalfLockedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            permanent = game.getPermanent(source.getSourceId());
        }

        // Return true if the permanent exists and its left half is NOT unlocked
        return permanent != null && !permanent.isLeftHalfUnlocked();
    }
}