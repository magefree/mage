package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.RoomCard;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Continuous effect implementing rule 709.5:
 * "As long as this permanent doesn't have the 'left/right half unlocked' designation, 
 * it doesn't have the name, mana cost, or rules text of this object's left/right half"
 */
public class RoomLockedHalfEffect extends ContinuousEffectImpl {

    public RoomLockedHalfEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "As long as this permanent doesn't have the appropriate unlocked designation, " +
                    "it doesn't have the name, mana cost, or rules text of locked halves";
    }

    private RoomLockedHalfEffect(final RoomLockedHalfEffect effect) {
        super(effect);
    }

    @Override
    public RoomLockedHalfEffect copy() {
        return new RoomLockedHalfEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (!(permanent instanceof RoomCard)) {
            return false;
        }
        
        RoomCard room = (RoomCard) permanent;

        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                // Remove abilities from locked halves per rule 709.5
                if (room.isLeftHalfLocked()) {
                    // Remove abilities from left half
                    for (Ability ability : room.getLeftHalfCard().getAbilities()) {
                        permanent.removeAbility(ability, source.getSourceId(), game);
                    }
                }
                if (room.isRightHalfLocked()) {
                    // Remove abilities from right half
                    for (Ability ability : room.getRightHalfCard().getAbilities()) {
                        permanent.removeAbility(ability, source.getSourceId(), game);
                    }
                }
                return true;
                
            case TypeChangingEffects_4:
                // Handle name and mana cost changes if needed
                // For now, Room cards keep their combined characteristics
                // This layer is available for future name/mana cost hiding implementation
                return true;
                
            default:
                return false;
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // This method is not used for continuous effects that work with layers
        return false;
    }
}