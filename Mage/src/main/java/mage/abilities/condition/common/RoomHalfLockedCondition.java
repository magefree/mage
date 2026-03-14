package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author oscscull
 * Checks if a Permanent's specified half is LOCKED (i.e., NOT unlocked).
 */
public enum RoomHalfLockedCondition implements Condition {
    
    LEFT(true),
    RIGHT(false);
    
    private final boolean checkLeft;

    RoomHalfLockedCondition(boolean checkLeft) {
        this.checkLeft = checkLeft;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            return false;
        }

        // Return true if the specified half is NOT unlocked
        return checkLeft ? !permanent.isLeftDoorUnlocked() : !permanent.isRightDoorUnlocked();
    }
}