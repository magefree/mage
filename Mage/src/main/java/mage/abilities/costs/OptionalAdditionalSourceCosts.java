package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.Collections;
import java.util.List;

/**
 * Interface for abilities that add additional costs to the source.
 * <p>
 * Example of such additional source costs:
 * {@link mage.abilities.keyword.KickerAbility}
 *
 * @author LevelX2
 */
public interface OptionalAdditionalSourceCosts {

    /**
     * Warning, don't forget to set up cost type for costs, it can help with X announce
     *
     * @param ability
     * @param game
     */
    // TODO: add AI support to use buyback, replicate and other additional costs (current version can't calc available mana before buyback use)
    void addOptionalAdditionalCosts(Ability ability, Game game);

    /**
     * Strategic AI hook for exposing optional additional costs as explicit cast
     * variants. Implementations should return copied abilities tagged with a
     * {@link CastCostPlan}; the normal prompt path remains unchanged.
     */
    default List<Ability> getOptionalAdditionalCostVariants(Ability ability, Game game) {
        return Collections.emptyList();
    }

    String getCastMessageSuffix();
}
