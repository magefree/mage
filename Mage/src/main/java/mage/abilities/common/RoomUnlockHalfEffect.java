package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * An effect that unlocks either the left or right half of a Room permanent.
 * This is used by the activated ability to pay mana to unlock a door.
 */
public class RoomUnlockHalfEffect extends OneShotEffect {

    private final boolean isLeftHalf;

    public RoomUnlockHalfEffect(boolean isLeftHalf) {
        super(Outcome.Neutral);
        this.isLeftHalf = isLeftHalf;
        staticText = "unlock the " + (isLeftHalf ? "left" : "right") + " half";
    }

    private RoomUnlockHalfEffect(final RoomUnlockHalfEffect effect) {
        super(effect);
        this.isLeftHalf = effect.isLeftHalf;
    }

    @Override
    public RoomUnlockHalfEffect copy() {
        return new RoomUnlockHalfEffect(this);
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

        // --- Check if the half is already unlocked, similar to Monstrosity's behavior ---
        if (isLeftHalf && permanent.isLeftHalfUnlocked()) {
            // Left half is already unlocked, so the effect does nothing
            return false;
        }
        if (!isLeftHalf && permanent.isRightHalfUnlocked()) {
            // Right half is already unlocked, so the effect does nothing
            return false;
        }
        // --- End of new check ---

        if (isLeftHalf) {
            return permanent.unlockLeftHalf(game, source);
        } else {
            return permanent.unlockRightHalf(game, source);
        }
    }
}