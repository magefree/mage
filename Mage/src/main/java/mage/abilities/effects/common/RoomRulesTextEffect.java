package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.RoomAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;

/**
 * Continuous effect that removes abilities from locked halves of a Room.
 * Implements rule 709.5: abilities from locked halves are not present.
 */
public class RoomRulesTextEffect extends ContinuousEffectImpl {
    
    // Store which abilities belong to which half using ability references
    private final Set<UUID> leftHalfAbilityIds = new HashSet<>();
    private final Set<UUID> rightHalfAbilityIds = new HashSet<>();
    private boolean initialized = false;

    public RoomRulesTextEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Neutral);
        staticText = "";
    }

    private RoomRulesTextEffect(final RoomRulesTextEffect effect) {
        super(effect);
        this.leftHalfAbilityIds.addAll(effect.leftHalfAbilityIds);
        this.rightHalfAbilityIds.addAll(effect.rightHalfAbilityIds);
        this.initialized = effect.initialized;
    }

    @Override
    public RoomRulesTextEffect copy() {
        return new RoomRulesTextEffect(this);
    }

    // Method to register which abilities belong to which half
    public void registerLeftHalfAbility(UUID abilityId) {
        leftHalfAbilityIds.add(abilityId);
    }

    public void registerRightHalfAbility(UUID abilityId) {
        rightHalfAbilityIds.add(abilityId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        boolean isLeftUnlocked = permanent.isLeftHalfUnlocked();
        boolean isRightUnlocked = permanent.isRightHalfUnlocked();

        // Find abilities to remove from locked halves
        List<Ability> abilitiesToRemove = new ArrayList<>();
        
        for (Ability ability : permanent.getAbilities()) {
            // Skip room system abilities
            if (isRoomSystemAbility(ability)) {
                continue;
            }
            
            UUID abilityId = ability.getId();
            
            // Remove if from a locked half
            if (leftHalfAbilityIds.contains(abilityId) && !isLeftUnlocked) {
                abilitiesToRemove.add(ability);
            } else if (rightHalfAbilityIds.contains(abilityId) && !isRightUnlocked) {
                abilitiesToRemove.add(ability);
            }
        }
        
        // Remove abilities from locked halves
        permanent.removeAbilities(abilitiesToRemove, source.getSourceId(), game);

        return true;
    }

    private boolean isRoomSystemAbility(Ability ability) {
        return ability instanceof RoomAbility || 
               ability instanceof RoomUnlockAbility ||
               ability.getClass().getSimpleName().equals("SpellAbility");
    }
}
