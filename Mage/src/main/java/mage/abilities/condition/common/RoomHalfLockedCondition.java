package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author oscscull
 * Checks if a Permanent's specified half is LOCKED (i.e., NOT unlocked).
 */
public class RoomHalfLockedCondition implements Condition {
    
    public static final RoomHalfLockedCondition LEFT = new RoomHalfLockedCondition(true);
    public static final RoomHalfLockedCondition RIGHT = new RoomHalfLockedCondition(false);
    
    private final boolean checkLeft;

    private RoomHalfLockedCondition(boolean checkLeft) {
        this.checkLeft = checkLeft;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            permanent = game.getPermanent(source.getSourceId());
        }

        if (permanent == null) {
            return false;
        }

        // Return true if the specified half is NOT unlocked
        return checkLeft ? !permanent.roomLeftDoorUnlocked() : !permanent.roomRightDoorUnlocked();
    }
}