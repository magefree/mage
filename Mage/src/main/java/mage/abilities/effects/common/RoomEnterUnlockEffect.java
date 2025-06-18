package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import java.util.UUID; // Needed for Permanent ID

public class RoomEnterUnlockEffect extends OneShotEffect {

    public RoomEnterUnlockEffect() {
        super(Outcome.Neutral);
        staticText = ""; // No visible text needed
    }

    private RoomEnterUnlockEffect(final RoomEnterUnlockEffect effect) {
        super(effect);
    }

    @Override
    public RoomEnterUnlockEffect copy() {
        return new RoomEnterUnlockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Rule 709.5d: A permanent with a shared type line is given the appropriate
        // unlocked designation as it enters the battlefield if that half was cast as a
        // spell

        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.wasUnlockedOnCast()) {
            return false;
        }

        // Mark that we've processed the unlock-on-cast to prevent multiple triggers
        System.out.println("tryunlock");
        permanent.unlockOnCast(game);

        // Get the UUID of the permanent. This ID should correspond to the CardId
        // of the RoomCard when it was on the stack during casting.
        UUID permanentId = permanent.getId();
        
        System.out.println("RoomEnterUnlockEffect.apply() for: " + permanent.getName() + " (Permanent ID: " + permanentId + ")");
        SpellAbilityType lastCastHalf = (SpellAbilityType) game.getState().getValue(permanentId + "_ROOM_LAST_CAST_HALF");
        System.out.println("  Retrieved lastCastHalf from state: " + lastCastHalf + " for key: " + permanentId + "_ROOM_LAST_CAST_HALF");
        
        if (lastCastHalf == SpellAbilityType.SPLIT_LEFT) {
            // Left half was cast - unlock left door
            return permanent.unlockLeftHalf(game, source);
        } else if (lastCastHalf == SpellAbilityType.SPLIT_RIGHT) {
            // Right half was cast - unlock right door
            return permanent.unlockRightHalf(game, source);
        }

        // If neither half was cast (e.g., put into play by another effect),
        // both doors remain locked per rule 709.5d
        return true;
    }
}