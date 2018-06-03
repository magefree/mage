
package mage.abilities.costs.mana;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that allow the player to pay mana costs of a spell in alternate ways.
 * For the payment SpecialActions are used.
 *
 * Example of such an alternate payment ability: {@link mage.abilities.keyword.DelveAbility}
 *
 * @author LevelX2
 */
@FunctionalInterface
public interface AlternateManaPaymentAbility {
    /**
     * Adds the special action to the state, that allows the user to do the alternate mana payment
     *
     * @param source
     * @param game
     * @param unpaid unapaid mana costs of the spell
     */
    void addSpecialAction(Ability source, Game game, ManaCost unpaid);
}