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

import java.util.ArrayList;
import java.util.List;

//todo delete me not doing anything rn
/**
 * Continuous effect implementing rule 709.5:
 * "As long as this permanent doesn't have the 'left/right half unlocked' designation, 
 * it doesn't have the name, mana cost, or rules text of this object's left/right half"
 */
public class RoomLockedHalfEffect extends ContinuousEffectImpl {

    private final boolean isLeftHalf;

    public RoomLockedHalfEffect(boolean isLeftHalf) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.isLeftHalf = isLeftHalf;
    }

    private RoomLockedHalfEffect(final RoomLockedHalfEffect effect) {
        super(effect);
        this.isLeftHalf = effect.isLeftHalf;
    }

    @Override
    public RoomLockedHalfEffect copy() {
        return new RoomLockedHalfEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }

        switch (layer) {
            // case AbilityAddingRemovingEffects_6:
            //     // Remove abilities from locked halves per rule 709.5
            //     return removeAbilitiesFromLockedHalf(permanent, source, game);
                
            case TypeChangingEffects_4:
                // Handle name and mana cost changes if needed
                // For now, Room cards keep their combined characteristics
                // This layer is available for future name/mana cost hiding implementation
                return handleNameAndManaCost(permanent, source, game);
                
            default:
                return false;
        }
    }

    private boolean removeAbilitiesFromLockedHalf(Permanent permanent, Ability source, Game game) {
        // Get the underlying room card to access the halves
        RoomCard roomCard = (RoomCard) permanent.getMainCard();
        if (roomCard == null) {
            return false;
        }

        List<Ability> abilitiesToRemove = new ArrayList<>();

        if (isLeftHalf && !permanent.isLeftHalfUnlocked()) {
            // Left half is locked - remove abilities from left half
            for (Ability ability : roomCard.getLeftHalfCard().getAbilities()) {
                // Find matching abilities on the permanent to remove
                for (Ability permAbility : permanent.getAbilities()) {
                    if (abilitiesMatch(ability, permAbility)) {
                        abilitiesToRemove.add(permAbility);
                    }
                }
            }
        } else if (!isLeftHalf && !permanent.isRightHalfUnlocked()) {
            // Right half is locked - remove abilities from right half
            for (Ability ability : roomCard.getRightHalfCard().getAbilities()) {
                // Find matching abilities on the permanent to remove
                for (Ability permAbility : permanent.getAbilities()) {
                    if (abilitiesMatch(ability, permAbility)) {
                        abilitiesToRemove.add(permAbility);
                    }
                }
            }
        }

        // Remove the identified abilities
        for (Ability abilityToRemove : abilitiesToRemove) {
            permanent.removeAbility(abilityToRemove, source.getSourceId(), game);
        }

        return !abilitiesToRemove.isEmpty();
    }

    private boolean handleNameAndManaCost(Permanent permanent, Ability source, Game game) {
        // Rule 709.5 also mentions hiding name and mana cost of locked halves
        // For now, rooms keep their combined characteristics
        // This could be implemented later if needed for specific interactions
        return false;
    }

    /**
     * Check if two abilities are the same for removal purposes
     * This is tricky because we need to match abilities from the card halves
     * to the abilities that were added to the permanent
     */
    private boolean abilitiesMatch(Ability cardAbility, Ability permanentAbility) {
        // Simple check - compare the class and rule text
        // This might need to be more sophisticated depending on how abilities are added
        return cardAbility.getClass().equals(permanentAbility.getClass()) &&
               cardAbility.getRule().equals(permanentAbility.getRule());
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