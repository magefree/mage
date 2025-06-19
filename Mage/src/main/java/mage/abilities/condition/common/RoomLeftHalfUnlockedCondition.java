package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Checks if a Permanent's left half is unlocked
 *
 * @author oscscull
 */
public enum RoomLeftHalfUnlockedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // This is needed on etb trigger
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            // This is needed on etb resolution
            permanent = game.getPermanent(source.getSourceId());
        }

        return permanent != null && permanent.isLeftHalfUnlocked();
    }

    @Override
    public String toString() {
        // Return an empty string so that the "if (!conditionText.isEmpty())"
        // check in TriggeredAbilityImpl.getRule() evaluates to false.
        return "";
    }
}