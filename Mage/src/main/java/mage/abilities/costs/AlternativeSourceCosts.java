package mage.abilities.costs;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that add alternative costs to the source.
 * <p>
 * Example of such additional source costs: {@link mage.abilities.keyword.KickerAbility}
 *
 * @author LevelX2
 */
public interface AlternativeSourceCosts extends Ability {

    /**
     * If non-Default, allow to link this cost to permission abilities
     */
    MageIdentifier getIdentifier();

    /**
     * Check that the alternative ability can be used for the ability.
     *
     * @param ability ability the alternative cost is activated for
     * @param game
     * @return
     */
    boolean canActivateAlternativeCostsNow(Ability ability, Game game);

    String getAlternativeCostText(Ability ability, Game game);

    /**
     * activate a specific alternative cost.
     * A check to canActivateAlternativeCostsNow should be made before-end
     * to check if it is valid.
     *
     * @param ability ability the alternative cost is activated for
     * @param game
     * @return
     */
    boolean activateAlternativeCosts(Ability ability, Game game);

    /**
     * Is the alternative spell cost currently available
     *
     * @param source spell ability the alternative costs can be paid for
     * @param game
     * @return
     */
    boolean isAvailable(Ability source, Game game);

    /**
     * Was the alternative cost activated
     *
     * @param game
     * @param source
     * @return
     */
    boolean isActivated(Ability source, Game game);

    /**
     * Suffix string to use for game log
     *
     * @param game
     * @return
     */
    String getCastMessageSuffix(Game game);

    void resetCost();
}