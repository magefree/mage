package mage.abilities.costs;

import mage.abilities.Ability;
import mage.constants.CostModificationType;
import mage.game.Game;

import java.io.Serializable;

/**
 * Dynamic costs implementation to control {X} or other costs, can be used in spells and abilities
 * <p>
 * Possible use cases:
 * - define {X} costs like X cards to discard (mana and non-mana values);
 * - define {X} limits before announce (to help in UX and AI logic);
 * - define any dynamic costs;
 * - use as simple cost increase/reduce effect;
 * <p>
 * Calls order by game engine:
 * - ... early cost target selection for EarlyTargetCost ...
 * - prepareX
 * - ... x announce ...
 * - prepareCost
 * - increaseCost
 * - reduceCost
 * - ... normal target selection and payment ...
 *
 * @author TheElk801, JayDi85
 */
public interface CostAdjuster extends Serializable {

    /**
     * Prepare {X} costs settings or define auto-announced mana values
     * <p>
     * Usage example:
     * - define auto-announced mana value {X} by ability.setVariableCostsValue
     * - define possible {X} settings by ability.setVariableCostsMinMax
     */
    default void prepareX(Ability ability, Game game) {
        // do nothing
    }

    /**
     * Prepare any dynamic costs
     * <p>
     * Usage example:
     * - add real cost after {X} mana value announce by CardUtil.getSourceCostsTagX
     * - add dynamic cost from game data
     */
    default void prepareCost(Ability ability, Game game) {
        // do nothing
    }

    /**
     * Simple cost reduction effect
     */
    default void reduceCost(Ability ability, Game game) {
        // do nothing
    }

    /**
     * Simple cost increase effect
     */
    default void increaseCost(Ability ability, Game game) {
        // do nothing
    }

    /**
     * Default implementation. Override reduceCost or increaseCost instead
     * TODO: make it private after java 9+ migrate
     */
    default void modifyCost(Ability ability, Game game, CostModificationType costModificationType) {
        switch (costModificationType) {
            case REDUCE_COST:
                reduceCost(ability, game);
                break;
            case INCREASE_COST:
                increaseCost(ability, game);
                break;
            case SET_COST:
                // do nothing
                break;
            default:
                throw new IllegalArgumentException("Unknown mod type: " + costModificationType);
        }
    }
}
