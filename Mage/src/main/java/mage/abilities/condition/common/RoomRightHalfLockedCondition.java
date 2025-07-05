package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author oscscull
 * Checks if a Permanent's right half is LOCKED (i.e., NOT unlocked).
 */
public enum RoomRightHalfLockedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            permanent = game.getPermanent(source.getSourceId());
        }

        // Return true if the permanent exists and its right half is NOT unlocked
        return permanent != null && !permanent.roomRightDoorUnlocked();
    }

    @Override
    public String toString() {
        // Return an empty string so that the "if (!conditionText.isEmpty())"
        // check in TriggeredAbilityImpl.getRule() evaluates to false.
        return "";
    }
}