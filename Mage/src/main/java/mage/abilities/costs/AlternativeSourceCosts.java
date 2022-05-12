package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that add alternative costs to the source.
 * <p>
 * Example of such additional source costs: {@link mage.abilities.keyword.KickerAbility}
 *
 * @author LevelX2
 */
public interface AlternativeSourceCosts {

    /**
     * Ask the player if they want to use the alternative costs
     *
     * @param ability ability the alternative cost is activated for
     * @param game
     * @return
     */
    boolean askToActivateAlternativeCosts(Ability ability, Game game);

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