package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

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

    String getCastMessageSuffix();
}
